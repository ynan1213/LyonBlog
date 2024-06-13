package com.ynan;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.example.core.api.DataSourceUtil;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserMain {
    public static void main(String[] args) throws SQLException {
        System.setProperty("sql.show", "true");
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_user", "xxx_ds_$->{0..1}.t_user_$->{[0, 1]}");
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "xxx_ds_${id % 2}"));
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_user_${id % 2}"));
        tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id", null));
        shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfiguration);

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("xxx_ds_0", DataSourceUtil.createDataSource("xxx_ds_0"));
        dataSourceMap.put("xxx_ds_1", DataSourceUtil.createDataSource("xxx_ds_1"));

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());

        String sql = "INSERT INTO t_user (age, name) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, "zhangsan");
            preparedStatement.setInt(1, 23);
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        }
    }
}
