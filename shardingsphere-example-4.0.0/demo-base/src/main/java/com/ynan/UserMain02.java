package com.ynan;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * INLINE模式查询
 */
public class UserMain02 {
    public static void main(String[] args) throws SQLException {

        Properties prop = new Properties();
        prop.put("sql.show", "true");

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("xxx_ds", DataSourceUtil.createDataSource("xxx_ds"));
        dataSourceMap.put("xxx_ds0", DataSourceUtil.createDataSource("xxx_ds0"));
        dataSourceMap.put("xxx_ds1", DataSourceUtil.createDataSource("xxx_ds1"));


        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(createTableRule());
        shardingRuleConfig.setDefaultDataSourceName("xxx_ds");
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, prop);


        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user where sex = ? and age = 13");
        // INLINE模式不支持范围查询
        // PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user where age < 15");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            int age = resultSet.getInt(2);
            int sex = resultSet.getInt(3);
            String name = resultSet.getString(4);
            String format = String.format("User[id=%s, age=%s, sex=%s, name=%s]", id, age, sex, name);
            System.out.println(format);
        }

    }

    public static TableRuleConfiguration createTableRule() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(
            "t_user",
            "xxx_ds$->{0..1}.t_user_$->{0..1}");
        // 按性别分到不同的库
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration(
            "sex", "xxx_ds$->{sex % 2}"));
        // 再按年龄分到不同的表
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(
            "age", "t_user_$->{age % 2}"));
        tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id", null));
        return tableRuleConfiguration;
    }
}
