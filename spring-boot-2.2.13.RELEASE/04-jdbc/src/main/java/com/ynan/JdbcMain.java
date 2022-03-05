package com.ynan;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author yuannan
 * @Date 2022/3/5 11:17
 */
@SpringBootApplication
public class JdbcMain {

	public static void main(String[] args) throws SQLException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(JdbcMain.class, args);

		DataSource dataSource = applicationContext.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		String name = connection.getClass().getName();
		boolean readOnly = connection.isReadOnly();
		boolean autoCommit = connection.getAutoCommit();
		System.out.println("==========");
	}

	@Bean
	public DataSource getDataSource(){
		HikariDataSource dataSource = new HikariDataSource();
		return dataSource;
	}
}
