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

    public Student getStudent(String theStudentId) throws Exception {

        Student theStudent = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        int studentId;

        try {

            // convert id
            studentId = Integer.parseInt(theStudentId);

            // get connection
            myConn = dataSource.getConnection();

            // sql
            String sql = "SELECT * FROM student WHERE id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, studentId);

            // execute query
            myRs = myStmt.executeQuery();

            // retrieve data
            if (myRs.next()) {

                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                theStudent = new Student(studentId, firstName, lastName, email);

            } else {

                throw new Exception("Could not find student id: " + studentId);
            }

        } finally {

            close(myConn, myStmt, myRs);
        }

        return theStudent;
    }

    public void updateStudent(Student theStudent) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            // sql
            String sql = "UPDATE student SET first_name=?, last_name=?, email=? WHERE id=?";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());
            myStmt.setInt(4, theStudent.getId());

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);
        }

    }

    public void deleteStudent(String theStudentId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            // convert id to int
            int studentId = Integer.parseInt(theStudentId);

            myConn = dataSource.getConnection();

            // sql
            String sql = "DELETE FROM student WHERE id=?";

            // statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, studentId);

            myStmt.execute();

        } finally {

            close(myConn, myStmt, null);
        }
    }

    public void searchStudents() throws Exception {

        List<Student> allStudents = getStudents();
        allStudents.stream()
                .filter(s -> (s.getId() - 100) * (s.getId() - 200) <= 0 );//> 100 and < 200
    }
}
