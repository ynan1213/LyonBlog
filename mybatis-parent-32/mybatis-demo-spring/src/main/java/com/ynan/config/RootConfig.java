package com.ynan.config;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @program: mybatis-parent
 * @description:
 * @author: yn
 * @create: 2021-06-25 18:42
 */

//@Configuration
//@ComponentScan("com.ynan")
//@PropertySource("classpath:db.properties")
public class RootConfig {

    /**
     * 不知道为什么 @Value读取不到@PropertySource注入的配置
     * 但是 Environment 可以
     */
    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource getDataSource(Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // dataSource 是必须设置的
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 配置文件其实是可以省略，因为下面很多属性可以使用
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("config.xml"));

        // 还可以设置扫描别名的包
        // sqlSessionFactoryBean.setTypeAliasesPackage("com.ynan.entity");

        // 还可以设置插件
        // sqlSessionFactoryBean.setPlugins();

        // 还可以设置类型转换器
        // sqlSessionFactoryBean.setTypeHandlers();

        // 还可以设置默认的枚举类型处理器
        // sqlSessionFactoryBean.setDefaultEnumTypeHandler();

        // 还可以设置 mapperLocations，注意：这里不能使用通配符
        //sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("mapper/*.xml"));
        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("mapper/UserMapper.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.ynan.dao");

        // 这里也可以不配置，默认会按类型来注入，但是看源码不是很明白
        //mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        // 如果配置成了true（默认false），basePackage等属性就可以使用 ${}占位符了
        //        mapperScannerConfigurer.setProcessPropertyPlaceHolders(true);
        return mapperScannerConfigurer;
    }

    //    @Bean
    //    public PropertySourcesPlaceholderConfigurer getXxx() {
    //        return new PropertySourcesPlaceholderConfigurer();
    //    }

}
