/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.route.router.sharding;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.core.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.sql.parser.relation.statement.SQLStatementContext;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dal.DALStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dal.dialect.mysql.ShowDatabasesStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dal.dialect.mysql.UseStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dal.dialect.postgresql.ResetParameterStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dal.dialect.postgresql.SetStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dcl.DCLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.DDLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.DMLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.SelectStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.tcl.TCLStatement;
import org.apache.shardingsphere.core.route.router.sharding.condition.ShardingConditions;
import org.apache.shardingsphere.core.route.type.RoutingEngine;
import org.apache.shardingsphere.core.route.type.broadcast.DataSourceGroupBroadcastRoutingEngine;
import org.apache.shardingsphere.core.route.type.broadcast.DatabaseBroadcastRoutingEngine;
import org.apache.shardingsphere.core.route.type.broadcast.MasterInstanceBroadcastRoutingEngine;
import org.apache.shardingsphere.core.route.type.broadcast.TableBroadcastRoutingEngine;
import org.apache.shardingsphere.core.route.type.complex.ComplexRoutingEngine;
import org.apache.shardingsphere.core.route.type.defaultdb.DefaultDatabaseRoutingEngine;
import org.apache.shardingsphere.core.route.type.ignore.IgnoreRoutingEngine;
import org.apache.shardingsphere.core.route.type.standard.StandardRoutingEngine;
import org.apache.shardingsphere.core.route.type.unicast.UnicastRoutingEngine;
import org.apache.shardingsphere.core.rule.ShardingRule;

import java.util.Collection;

/**
 * Routing engine factory.
 *
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoutingEngineFactory {
    
    /**
     * Create new instance of routing engine.
     * 
     * @param shardingRule sharding rule
     * @param metaData meta data of ShardingSphere
     * @param sqlStatementContext SQL statement context
     * @param shardingConditions shardingConditions
     * @return new instance of routing engine
     */
    public static RoutingEngine newInstance(final ShardingRule shardingRule,
                                            final ShardingSphereMetaData metaData,
                                            final SQLStatementContext sqlStatementContext,
                                            final ShardingConditions shardingConditions) {
        SQLStatement sqlStatement = sqlStatementContext.getSqlStatement();
        Collection<String> tableNames = sqlStatementContext.getTablesContext().getTableNames();

        // 全库路由: BeginTransaction、SetAutoCommit、Commit、Rollback等操作需要对所有库都操作
        if (sqlStatement instanceof TCLStatement) {
            return new DatabaseBroadcastRoutingEngine(shardingRule);
        }

        // 全库表路由: CreateTable、DropTable、AlterTable、CreateIndex、DropIndex等数据库定义语言需要对每个表都操作
        if (sqlStatement instanceof DDLStatement) {
            return new TableBroadcastRoutingEngine(shardingRule, metaData.getTables(), sqlStatementContext);
        }
        // 阻断路由: Describe、ResetParameter、Show等操作
        if (sqlStatement instanceof DALStatement) {
            return getDALRoutingEngine(shardingRule, sqlStatement, tableNames);
        }
        // 全实例路由：grant、createUser、alterUser、dropUser、createRole、setPassword等操作
        if (sqlStatement instanceof DCLStatement) {
            return getDCLRoutingEngine(shardingRule, sqlStatementContext, metaData);
        }
        // 默认库路由: 配置了默认数据源 + 表未分片且不是广播表
        // 1、配置了默认数据源；
        // 2、sql中所有的表没有配置分片规则；
        // 3、sql中所有的表均不是广播表；
        if (shardingRule.isAllInDefaultDataSource(tableNames)) {
            // 直接以defaultDataSource + logicTabelName创建RoutingUnit
            return new DefaultDatabaseRoutingEngine(shardingRule, tableNames);
        }
        // 全都是广播表 + 查询模式：随机选择一个数据源
        // 全都是广播表: 全库路由
        if (shardingRule.isAllBroadcastTables(tableNames)) {
            return sqlStatement instanceof SelectStatement ?
                new UnicastRoutingEngine(shardingRule, tableNames) :
                new DatabaseBroadcastRoutingEngine(shardingRule);
        }
        // 默认库路由
        if (sqlStatementContext.getSqlStatement() instanceof DMLStatement && tableNames.isEmpty() && shardingRule.hasDefaultDataSourceName()) {
            return new DefaultDatabaseRoutingEngine(shardingRule, tableNames);
        }
        // 单播路由
        if (sqlStatementContext.getSqlStatement() instanceof DMLStatement && shardingConditions.isAlwaysFalse()
            || tableNames.isEmpty()
            // 如果未配置默认数据源，且没有分片规则，且不是广播表，在这里就会被匹配，根本走不到后面默认的地方从而报错
            || !shardingRule.tableRuleExists(tableNames)) {
            return new UnicastRoutingEngine(shardingRule, tableNames);
        }
        // 分片路由
        return getShardingRoutingEngine(shardingRule, sqlStatementContext, shardingConditions, tableNames);
    }
    
    private static RoutingEngine getDALRoutingEngine(final ShardingRule shardingRule, final SQLStatement sqlStatement, final Collection<String> tableNames) {
        // 如果是Use语句，则什么也不做
        if (sqlStatement instanceof UseStatement) {
            return new IgnoreRoutingEngine();
        }
        // 如果是Set或ResetParameter语句，则进行全数据库广播
        if (sqlStatement instanceof SetStatement || sqlStatement instanceof ResetParameterStatement || sqlStatement instanceof ShowDatabasesStatement) {
            return new DatabaseBroadcastRoutingEngine(shardingRule);
        }
        // 如果存在默认数据库，则执行默认数据库路由
        if (!tableNames.isEmpty() && !shardingRule.tableRuleExists(tableNames) && shardingRule.hasDefaultDataSourceName()) {
            return new DefaultDatabaseRoutingEngine(shardingRule, tableNames);
        }
        // 如果表列表不为空，则执行单播路由
        // UnicastRoutingEngine 代表单播路由，用于获取某一真实表信息的场景，它只需要从任意库中的任意真实表中获取数据即可。
        // 例如 DESCRIBE 语句就适合使用 UnicastRoutingEngine，因为每个真实表中的数据描述结构都是相同的
        if (!tableNames.isEmpty()) {
            return new UnicastRoutingEngine(shardingRule, tableNames);
        }
        return new DataSourceGroupBroadcastRoutingEngine(shardingRule);
    }
    
    private static RoutingEngine getDCLRoutingEngine(final ShardingRule shardingRule, final SQLStatementContext sqlStatementContext, final ShardingSphereMetaData metaData) {
        return isGrantForSingleTable(sqlStatementContext) 
                ? new TableBroadcastRoutingEngine(shardingRule, metaData.getTables(), sqlStatementContext)
                // 在主从环境下，对于 DCLStatement 而言，有时候我们希望 SQL 语句只针对主数据库进行执行，所以就有了 MasterInstanceBroadcastRoutingEngine
                : new MasterInstanceBroadcastRoutingEngine(shardingRule, metaData.getDataSources());
    }
    
    private static boolean isGrantForSingleTable(final SQLStatementContext sqlStatementContext) {
        return !sqlStatementContext.getTablesContext().isEmpty() && !"*".equals(sqlStatementContext.getTablesContext().getSingleTableName());
    }
    
    private static RoutingEngine getShardingRoutingEngine(final ShardingRule shardingRule, final SQLStatementContext sqlStatementContext, 
                                                          final ShardingConditions shardingConditions, final Collection<String> tableNames) {
        /**
         * 入参 tableNames 代表sql中的表
         * shardingTableNames的个数可能小于tableNames，这里只返回配置了分片规则的表
         */
        Collection<String> shardingTableNames = shardingRule.getShardingLogicTableNames(tableNames);
        if (1 == shardingTableNames.size() || shardingRule.isAllBindingTables(shardingTableNames)) {
            // 如果分片操作只涉及一张表，或者涉及多张表但这些表是互为绑定表的关系时，则使用 StandardRoutingEngine 进行路由
            return new StandardRoutingEngine(shardingRule, shardingTableNames.iterator().next(), sqlStatementContext, shardingConditions);
        }
        // TODO config for cartesian set
        return new ComplexRoutingEngine(shardingRule, tableNames, sqlStatementContext, shardingConditions);
    }
}
