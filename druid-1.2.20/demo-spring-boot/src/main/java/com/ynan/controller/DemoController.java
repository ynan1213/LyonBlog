package com.ynan.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class DemoController {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select 1");
        connection.close();
        Connection connection1 = dataSource.getConnection();
        connection1.close();
        Connection connection2 = dataSource.getConnection();
        connection2.close();
        Connection connection3 = dataSource.getConnection();
        connection3.close();

        DruidDataSource druidDataSource = (DruidDataSource)dataSource;
        druidDataSource.init();
    }

    @RequestMapping("/hello")
    public String hello() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection.toString();
    }

}
