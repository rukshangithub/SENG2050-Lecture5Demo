package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogonServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Retrieve the 'name' parameter from the GET request
        String username = request.getParameter("studentNo");
        String password = request.getParameter("password");

        // Authenticate user
        StudentService stdServervice = new StudentService();
        Student student = stdServervice.authenticateStudent(username, password);
        if (student!= null) {
            // Store stduent in request scope
            request.setAttribute("student", student);

            // Forward to Welcome.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
            dispatcher.forward(request, response);
        }
        else {
            String errorMessage = new String("Your username and/or password was not valid");
            request.setAttribute("errorMessage", errorMessage);

            // Forward to error.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}


