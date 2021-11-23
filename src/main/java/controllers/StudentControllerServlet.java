/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import models.Student;
import services.StudentDbUtil;

/**
 *
 * @author huynq
 */
public class StudentControllerServlet extends HttpServlet {

    private StudentDbUtil studentDbUtil;

    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {

        super.init();

        try {

            studentDbUtil = new StudentDbUtil(dataSource);

        } catch (Exception e) {

            throw new ServletException(e);
        }

    }

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

        try {
            // read "command" parameter
            String theCommand = request.getParameter("command");

            //
            if (theCommand == null) theCommand = "LIST"; // get all

            switch (theCommand) {

                case "LIST":
                   listStudents(request, response);
                   break;
                case "ADD":
                   addStudent(request, response);
                   break;
            }




        } catch (Exception ex) {

            throw new ServletException(ex);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Student> students = studentDbUtil.getStudents();

        request.setAttribute("STUDENT_LIST", students);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/list-students.jsp");

        dispatcher.forward(request, response);

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

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read student info from form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Student theStudent = new Student(firstName, lastName, email);

        // add student to database
        studentDbUtil.addStudent(theStudent);

        // send back to main page
        //listStudents(request, response);
        response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");

    }

}
