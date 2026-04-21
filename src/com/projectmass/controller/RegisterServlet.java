package com.projectmass.controller;

import com.projectmass.dao.UserDAO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get data from the JSP form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. Create a User object (Ensure you fixed the 'abstract' error in the User model!)
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(pass);
        newUser.setRole(role);

        // 3. Use UserDAO to save to MySQL
        UserDAO dao = new UserDAO();
        boolean success = dao.saveUser(newUser);

        // This "?status=registered" is what triggers the <% if %> block in your login page
        if (success) {
            response.sendRedirect("login.jsp?status=registered");
        } else {
            response.sendRedirect("register.jsp?msg=error");
        }
    }
}