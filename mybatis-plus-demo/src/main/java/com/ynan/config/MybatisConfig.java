package com.ynan.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2021/12/4 09:42
 */
@Configuration
public class MybatisConfig {

    /**
     * 注意：这里未配置 Druid的情况默认会使用springboot的HikariDataSource数据源
     */
//    @Bean
//    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        return sqlSessionFactoryBean;
//    }


//    @Bean
//    public GlobalConfig globalConfig(){
//        GlobalCo
//    }

}
