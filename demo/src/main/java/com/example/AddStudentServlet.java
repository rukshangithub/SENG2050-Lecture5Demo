package com.example;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");

        // Retrieve the 'name' parameter from the GET request
        String stdNo = request.getParameter("studentNo");
        String givenNames = request.getParameter("givenNames");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        try {
            // Add student
            StudentService stdServervice = new StudentService();
            stdServervice.addStudent(stdNo, givenNames, lastName, password);
         
            // Forward to success page
            String message = new String("Student was  added!");
            request.setAttribute("Message", message);
            request.setAttribute("returnURL", "addStudent.html");

            // Forward to Welcome.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e)
        {
            String errorMessage = new String("Student was not added");
            request.setAttribute("errorMessage", errorMessage);

            // Forward to error.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);          
        }

    }
}
