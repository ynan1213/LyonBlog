package com.ynan.main1.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author yuannan
 * @Date 2021/9/12 5:22 下午
 */
public class Main1jdbc {

  private Connection connection;

  @Before
  public void befor() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager
      .getConnection("jdbc:mysql://101.132.104.47:3306/Test?serverTimezone=UTC&useSSL=false&characterEncoding=UTF-8",
        "root", "123456");
  }

  @Test
  public void select() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from type_test");
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      byte col2 = resultSet.getByte(2);
      short col3 = resultSet.getShort(3);
      int col4 = resultSet.getInt(4);
      int col5 = resultSet.getInt(5);
      //int col6 = resultSet.getInt(6);
      long col6 = resultSet.getLong(6);
      long col7 = resultSet.getLong(7);
      //long col8 = resultSet.getLong(8);
    }
  }

  @Test
  public void select1() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from type_test1");
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      Date date = resultSet.getDate(2);
      Time time = resultSet.getTime(3);
      Date datetime = resultSet.getDate(4);
      Timestamp timestamp = resultSet.getTimestamp(5);
    }
  }

  @Test
  public void insert() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(
      "insert into user(name, age) values('xxxx', 22)", Statement.RETURN_GENERATED_KEYS);
    int i = preparedStatement.executeUpdate();
    ResultSet keys = preparedStatement.getGeneratedKeys();
    while (keys.next()) {
      System.out.println(keys.getInt(1));
    }
  }

  public void callable() {
    // connection.prepareCall();
  }


}
