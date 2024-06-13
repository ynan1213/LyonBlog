package com.ynan;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * 分库分表插入
 */
public class UserMain01 {
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

        String sql = "INSERT INTO t_user (age, sex, name) VALUES (?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        Random random = new Random();

        for (int i = 0; i < 1; i++) {
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, i);
                preparedStatement.setInt(2, random.nextInt(2));
                preparedStatement.setString(3, "name_" + i);
                preparedStatement.executeUpdate();

            } finally {
                preparedStatement.close();
                connection.close();
            }
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
