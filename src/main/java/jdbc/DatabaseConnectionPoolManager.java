/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author huynq
 */
public class DatabaseConnectionPoolManager {

    private final static BasicDataSource dataSource = new BasicDataSource();

    static {

        dataSource.setUrl("jdbc:mysql://localhost:3306/web_student_tracker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("admin");
        dataSource.setPassword("123456");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // 5 <> 8

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }

}
