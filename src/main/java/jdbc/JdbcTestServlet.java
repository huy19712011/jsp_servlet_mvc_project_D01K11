/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;

/**
 *
 * @author huynq
 */
public class JdbcTestServlet extends HttpServlet {

    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {

            out.println("hello world");


            myConn = dataSource.getConnection();


            myStmt = myConn.createStatement();

            String sql = "SELECT * FROM student";

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                String email = myRs.getString("email");

                out.println(email);
                out.println("<br>");
            }



        } catch (Exception e) {
            System.out.println("error");
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
