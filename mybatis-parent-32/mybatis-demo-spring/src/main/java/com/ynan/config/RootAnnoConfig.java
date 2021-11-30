package com.ynan.config;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @Author yuannan
 * @Date 2021/11/30 21:59
 *
 * 使用 @MapperScan 代替 MapperScannerConfigurer
 */
@Configuration
@ComponentScan("com.ynan")
@PropertySource("classpath:db.properties")
@MapperScan(basePackages = "com.ynan.dao")
public class RootAnnoConfig {

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
}
