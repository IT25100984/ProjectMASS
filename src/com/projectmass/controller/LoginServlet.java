package com.projectmass.controller;

import com.projectmass.dao.UserDAO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the data from login.jsp
        String email = request.getParameter("username"); // Matches 'name="username"' in your JSP
        String pass = request.getParameter("password");

        // DEBUG: Print this to see if the Servlet is even triggered
        System.out.println("LoginServlet: Received request for " + email);

        // 2. THIS IS THE MISSING LINK: Call the DAO
        UserDAO dao = new UserDAO();
        User user = dao.login(email, pass);

        if (user != null) {
            // SUCCESS: Store user in session and redirect to dashboard
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Logic to send to correct dashboard based on role
            if ("DOCTOR".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("doctorDashboard");
            } else {
                response.sendRedirect("patientDashboard");
            }
        } else {
            // FAILURE: Send back to log in with error
            response.sendRedirect("login.jsp?error=failed");
        }
    }
}