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

            close(myConn, myStmt, myRs);

        }

    }

    public void addStudent(Student theStudent) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            // get connection
            myConn = dataSource.getConnection();

            // sql
            String sql = "INSERT INTO student "
                    + "(first_name, last_name, email) "
                    + "values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set params values
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);

        }

    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

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
