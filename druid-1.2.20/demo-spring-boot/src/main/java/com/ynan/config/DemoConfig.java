package com.ynan.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class DemoConfig {

    @Value("${spring.datasource.druid.url}")
    private String url;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Bean
    // @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        /**
         * ------------------ 01:基础配置 ------------------
         */
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // driver和driverClassName可以不配置，会从url中解析
        //dataSource.setDriver(null);
        //dataSource.setDriverClassName("");

        // 连接池允许最大连接数
        dataSource.setMaxActive(2);


        /**
         * ------------------ 02:创建物理连接相关配置 ------------------
         */
        // 创建物理连接的超时时间，默认10S，生效原理是由Driver直接使用
        dataSource.setConnectTimeout(10001);
        // 连接的响应时间，默认10S，不是连接空闲超时时间，具体见文档
        dataSource.setSocketTimeout(10001);

        // 验证连接的语句
        dataSource.setValidationQuery("select 1");
        // 和validationQuery配合使用
        dataSource.setValidationQueryTimeout(10000);
        // 是否使用ping的方式验证connect，默认false
        dataSource.setUsePingMethod(true);

        // 是否记录【show variables】和【show global variables】语句返回的变量
        // 经过测试分别返回了600多项
        dataSource.setInitVariants(true);
        dataSource.setInitGlobalVariants(true);

        // 用于初始化connection的sql语句
        String initSql1 = "set sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';";
        String initSql2 = "set names utf8mb4;";
        dataSource.setConnectionInitSqls(Arrays.asList(initSql1, initSql2));

        /**
         * ------------------ 03:获取连接相关配置 ------------------
         */
        // 获取连接时是否带超时时间，该值默认-1表示不带超时，目前发现当带超时时间且超时的情况下会抛出GetConnectionTimeoutException异常
         dataSource.setMaxWait(1000);

        // 获取连接时如果发生了超时失败的重试次数
        dataSource.setNotFullTimeoutRetryCount(3);

        /**
         * ------------------ 04:检测相关配置 ------------------
         */
        // 检查空闲连接的频率，默认值为1分钟，超过该值需要验证连接是否有效
        dataSource.setTimeBetweenEvictionRunsMillis(60000);

        // 泄露检测机制
        dataSource.setRemoveAbandoned(true);
        //dataSource.setRemoveAbandonedTimeout();
        //dataSource.setRemoveAbandonedTimeoutMillis();


        // 默认false ？？？
        // dataSource.setKeepAlive();

        return dataSource;
    }

}
