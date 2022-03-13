package com.ynan.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2022/3/5 21:44
 */
@Configuration(proxyBeanMethods = false)
public class MybatisConfig {

    /**
     * 因为mybatis自动化配置类 MybatisAutoConfiguration 上面有 @ConditionalOnSingleCandidate(DataSource.class)
     * 所以当容器中有多个 DataSource的时候，mybatis自动化配置会失效
     *
     * 因为自定义了 DataSource，spring-boot-starter-jdbc的DataSource是不会生效的，但是shardingJdbc的DataSource会生效（debug的时候未明白）
     */
//    @Bean
//    public DataSource getDataSource() {
//        return new HikariDataSource();
//    }

}
