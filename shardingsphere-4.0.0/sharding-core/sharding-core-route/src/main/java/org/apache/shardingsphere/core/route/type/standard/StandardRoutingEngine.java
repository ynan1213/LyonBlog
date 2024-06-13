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

package org.apache.shardingsphere.core.route.type.standard;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.exception.ShardingException;
import org.apache.shardingsphere.sql.parser.relation.statement.SQLStatementContext;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.DeleteStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.InsertStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dml.UpdateStatement;
import org.apache.shardingsphere.core.route.router.sharding.condition.ShardingCondition;
import org.apache.shardingsphere.core.route.router.sharding.condition.ShardingConditions;
import org.apache.shardingsphere.core.route.type.RoutingEngine;
import org.apache.shardingsphere.core.route.type.RoutingResult;
import org.apache.shardingsphere.core.route.type.RoutingUnit;
import org.apache.shardingsphere.core.route.type.TableUnit;
import org.apache.shardingsphere.core.rule.BindingTableRule;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.core.strategy.route.ShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.hint.HintShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;
import org.apache.shardingsphere.core.strategy.route.value.RouteValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Standard routing engine.
 * 
 * @author zhangliang
 * @author maxiaoguang
 * @author panjuan
 */
@RequiredArgsConstructor
public final class StandardRoutingEngine implements RoutingEngine {
    
    private final ShardingRule shardingRule;

    /**
     * StandardRoutingEngine的适用场景：只支持分片操作只涉及一张表，或者涉及多张表但这些表是互为绑定表的关系
     * 也就是StandardRoutingEngine和一个logicTableName绑定
     */
    private final String logicTableName;
    
    private final SQLStatementContext sqlStatementContext;
    
    private final ShardingConditions shardingConditions;
    
    @Override
    public RoutingResult route() {
        // 不支持多表DML操作
        if (isDMLForModify(sqlStatementContext.getSqlStatement()) && !sqlStatementContext.getTablesContext().isSingleTable()) {
            throw new ShardingException("Cannot support Multiple-Table for '%s'.", sqlStatementContext.getSqlStatement());
        }
        TableRule tableRule = shardingRule.getTableRule(logicTableName);
        Collection<DataNode> dataNodes = getDataNodes(tableRule);
        return generateRoutingResult(dataNodes);
    }
    
    private boolean isDMLForModify(final SQLStatement sqlStatement) {
        return sqlStatement instanceof InsertStatement || sqlStatement instanceof UpdateStatement || sqlStatement instanceof DeleteStatement;
    }
    
    private RoutingResult generateRoutingResult(final Collection<DataNode> routedDataNodes) {
        RoutingResult result = new RoutingResult();
        for (DataNode each : routedDataNodes) {
            RoutingUnit routingUnit = new RoutingUnit(each.getDataSourceName());
            routingUnit.getTableUnits().add(new TableUnit(logicTableName, each.getTableName()));
            result.getRoutingUnits().add(routingUnit);
        }
        return result;
    }

    /**
     * 以下三条代码分支虽然处理方式有所不同，但本质上都是获取 RouteValue 的集合（RouteValue 保存的就是用于路由的表名和列名）。
     * 在获取了所需的 RouteValue 之后，在 StandardRoutingEngine 中，以上三种场景最终都会调用 route0 基础方法进行路由，
     * 该方法的作用就是根据这些 RouteValue 得出目标 DataNode（DataNode 中保存的就是具体的目标节点，包括 dataSourceName和tableName）的集合。
     */
    private Collection<DataNode> getDataNodes(final TableRule tableRule) {
        // 当 DatabaseShardingStrategy 和 TableShardingStrategy 都是 HintShardingStrategy时走该分支
        // Hint是一种强制路由的方法
        if (isRoutingByHint(tableRule)) {
            return routeByHint(tableRule);
        }

        // 当 DatabaseShardingStrategy 和 TableShardingStrategy 都不是 HintShardingStrategy 时走该分支
        if (isRoutingByShardingConditions(tableRule)) {
            return routeByShardingConditions(tableRule);
        }

        // 当 DatabaseShardingStrategy 或 TableShardingStrategy 中任意一个是 HintShardingStrategy 时走该分支
        return routeByMixedConditions(tableRule);
    }
    
    private boolean isRoutingByHint(final TableRule tableRule) {
        return shardingRule.getDatabaseShardingStrategy(tableRule) instanceof HintShardingStrategy
            && shardingRule.getTableShardingStrategy(tableRule) instanceof HintShardingStrategy;
    }
    
    private Collection<DataNode> routeByHint(final TableRule tableRule) {
        return route0(tableRule, getDatabaseShardingValuesFromHint(), getTableShardingValuesFromHint());
    }
    
    private boolean isRoutingByShardingConditions(final TableRule tableRule) {
        return !(shardingRule.getDatabaseShardingStrategy(tableRule) instanceof HintShardingStrategy || shardingRule.getTableShardingStrategy(tableRule) instanceof HintShardingStrategy);
    }
    
    private Collection<DataNode> routeByShardingConditions(final TableRule tableRule) {
        return shardingConditions.getConditions().isEmpty()
                ? route0(tableRule, Collections.<RouteValue>emptyList(), Collections.<RouteValue>emptyList())
                : routeByShardingConditionsWithCondition(tableRule);
    }
    
    private Collection<DataNode> routeByShardingConditionsWithCondition(final TableRule tableRule) {
        Collection<DataNode> result = new LinkedList<>();
        for (ShardingCondition each : shardingConditions.getConditions()) {
            Collection<DataNode> dataNodes = route0(
                tableRule,
                getShardingValuesFromShardingConditions(shardingRule.getDatabaseShardingStrategy(tableRule).getShardingColumns(), each),
                getShardingValuesFromShardingConditions(shardingRule.getTableShardingStrategy(tableRule).getShardingColumns(), each));
            each.getDataNodes().addAll(dataNodes);
            result.addAll(dataNodes);
        }
        return result;
    }
    
    private Collection<DataNode> routeByMixedConditions(final TableRule tableRule) {
        return shardingConditions.getConditions().isEmpty()
            ? routeByMixedConditionsWithHint(tableRule)
            : routeByMixedConditionsWithCondition(tableRule);
    }
    
    private Collection<DataNode> routeByMixedConditionsWithCondition(final TableRule tableRule) {
        Collection<DataNode> result = new LinkedList<>();
        for (ShardingCondition each : shardingConditions.getConditions()) {
            Collection<DataNode> dataNodes = route0(tableRule, getDatabaseShardingValues(tableRule, each), getTableShardingValues(tableRule, each));
            each.getDataNodes().addAll(dataNodes);
            result.addAll(dataNodes);
        }
        return result;
    }
    
    private Collection<DataNode> routeByMixedConditionsWithHint(final TableRule tableRule) {
        if (shardingRule.getDatabaseShardingStrategy(tableRule) instanceof HintShardingStrategy) {
            return route0(tableRule, getDatabaseShardingValuesFromHint(), Collections.<RouteValue>emptyList());
        }
        return route0(tableRule, Collections.<RouteValue>emptyList(), getTableShardingValuesFromHint());
    }
    
    private List<RouteValue> getDatabaseShardingValues(final TableRule tableRule, final ShardingCondition shardingCondition) {
        ShardingStrategy dataBaseShardingStrategy = shardingRule.getDatabaseShardingStrategy(tableRule);
        return isGettingShardingValuesFromHint(dataBaseShardingStrategy)
                ? getDatabaseShardingValuesFromHint() : getShardingValuesFromShardingConditions(dataBaseShardingStrategy.getShardingColumns(), shardingCondition);
    }
    
    private List<RouteValue> getTableShardingValues(final TableRule tableRule, final ShardingCondition shardingCondition) {
        ShardingStrategy tableShardingStrategy = shardingRule.getTableShardingStrategy(tableRule);
        return isGettingShardingValuesFromHint(tableShardingStrategy)
                ? getTableShardingValuesFromHint() : getShardingValuesFromShardingConditions(tableShardingStrategy.getShardingColumns(), shardingCondition);
    }
    
    private boolean isGettingShardingValuesFromHint(final ShardingStrategy shardingStrategy) {
        return shardingStrategy instanceof HintShardingStrategy;
    }
    
    private List<RouteValue> getDatabaseShardingValuesFromHint() {
        return getRouteValues(HintManager.getDatabaseShardingValues(logicTableName));
    }
    
    private List<RouteValue> getTableShardingValuesFromHint() {
        return getRouteValues(HintManager.getTableShardingValues(logicTableName));
    }
    
    private List<RouteValue> getRouteValues(final Collection<Comparable<?>> shardingValue) {
        return shardingValue.isEmpty() ? Collections.<RouteValue>emptyList() : Collections.<RouteValue>singletonList(new ListRouteValue<>("", logicTableName, shardingValue));
    }
    
    private List<RouteValue> getShardingValuesFromShardingConditions(final Collection<String> shardingColumns, final ShardingCondition shardingCondition) {
        List<RouteValue> result = new ArrayList<>(shardingColumns.size());
        for (RouteValue each : shardingCondition.getRouteValues()) {
            Optional<BindingTableRule> bindingTableRule = shardingRule.findBindingTableRule(logicTableName);
            if ((logicTableName.equals(each.getTableName()) || bindingTableRule.isPresent() && bindingTableRule.get().hasLogicTable(logicTableName)) 
                    && shardingColumns.contains(each.getColumnName())) {
                result.add(each);
            }
        }
        return result;
    }
    
    private Collection<DataNode> route0(
        final TableRule tableRule,
        final List<RouteValue> databaseShardingValues,
        final List<RouteValue> tableShardingValues) {
        // 路由DataSource
        Collection<String> routedDataSources = routeDataSources(tableRule, databaseShardingValues);
        Collection<DataNode> result = new LinkedList<>();
        for (String each : routedDataSources) {
            result.addAll(routeTables(tableRule, each, tableShardingValues));
        }
        return result;
    }
    
    private Collection<String> routeDataSources(final TableRule tableRule, final List<RouteValue> databaseShardingValues) {
        // 如果databaseShardingValues为空，直接返回所有的数据源
        if (databaseShardingValues.isEmpty()) {
            return tableRule.getActualDatasourceNames();
        }
        // 否则调用ShardingStrategy进行处理
        ShardingStrategy databaseShardingStrategy = shardingRule.getDatabaseShardingStrategy(tableRule);
        Collection<String> shardingResult = databaseShardingStrategy.doSharding(tableRule.getActualDatasourceNames(), databaseShardingValues);
        Collection<String> result = new LinkedHashSet<>(shardingResult);
        Preconditions.checkState(!result.isEmpty(), "no database route info");
        Preconditions.checkState(tableRule.getActualDatasourceNames().containsAll(result), 
                "Some routed data sources do not belong to configured data sources. routed data sources: `%s`, configured data sources: `%s`", result, tableRule.getActualDatasourceNames());
        return result;
    }
    
    private Collection<DataNode> routeTables(
        final TableRule tableRule,
        final String routedDataSource,
        final List<RouteValue> tableShardingValues) {

        // 获取routedDataSource数据源下的表
        Collection<String> availableTargetTables = tableRule.getActualTableNames(routedDataSource);

        // 如果tableShardingValues为空，直接返回数据源下的所有表，否则按照ShardingStrategy进行处理
        Collection<String> routedTables = new LinkedHashSet<>(tableShardingValues.isEmpty() ? availableTargetTables
                : shardingRule.getTableShardingStrategy(tableRule).doSharding(availableTargetTables, tableShardingValues));
        Preconditions.checkState(!routedTables.isEmpty(), "no table route info");
        Collection<DataNode> result = new LinkedList<>();
        for (String each : routedTables) {
            result.add(new DataNode(routedDataSource, each));
        }
        return result;
    }
}
