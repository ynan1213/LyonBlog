package com.ynan;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestMain {
   public static void main(String[] args) throws SQLException {

      HikariDataSource hikariDataSource = new HikariDataSource();
      hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDataTimeBehavior=convertToNull&useSSL=false");
      hikariDataSource.setUsername("root");
      hikariDataSource.setPassword("12345678");

      hikariDataSource.setConnectionTestQuery("select 1");
      hikariDataSource.setConnectionInitSql("set names utf8mb4");

      hikariDataSource.setMinimumIdle(0);

      Connection connection = hikariDataSource.getConnection();
      connection.close();
   }
}
