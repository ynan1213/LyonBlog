package com.ynan;


import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 广播表插入
 */
public class UserMain03 {

    public static void main(String[] args) throws SQLException {

        Properties prop = new Properties();
        prop.put("sql.show", "true");

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("xxx_ds", DataSourceUtil.createDataSource("xxx_ds"));
        dataSourceMap.put("xxx_ds0", DataSourceUtil.createDataSource("xxx_ds0"));
        dataSourceMap.put("xxx_ds1", DataSourceUtil.createDataSource("xxx_ds1"));


        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("xxx_ds");
        shardingRuleConfig.setBroadcastTables(Collections.singletonList("t_address"));
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, prop);


        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_address (addr_code, addr_name) VALUES (?, ?)");
        preparedStatement.setInt(1, 200);
        preparedStatement.setString(2, "武汉");
        preparedStatement.executeUpdate();

    }
}
