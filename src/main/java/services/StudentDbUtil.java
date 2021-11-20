/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import models.Student;

/**
 *
 * @author huynq
 */
public class StudentDbUtil {

    private DataSource dataSource;

    public StudentDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Student> getStudents() throws Exception {

        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            // get connection
            myConn = dataSource.getConnection();

            // sql
            String sql = "SELECT * FROM student";

            // query
            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process resultset
            while (myRs.next()) {

                // retrieve data from rows
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                Student tempStudent = new Student(id, firstName, lastName, email);

                students.add(tempStudent);
            }

            return students;
            
        } finally {

                        // close all
            if (myRs != null) try {
                myRs.close();
            } catch (SQLException e) {
                // ignore
            }

            if (myStmt != null) try {
                myStmt.close();
            } catch (SQLException e) {
                // ignore
            }

            if (myConn != null) try {
                myConn.close();
            } catch (SQLException e) {
                // ignore
            }
        }

    }
}
