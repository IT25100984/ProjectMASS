package com.projectmass.controller;

import com.google.gson.Gson;
import com.projectmass.dao.UserDAO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/getDoctorsBySpec")
public class GetDoctorsBySpecServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO(); // Ensure this is initialized

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String spec = request.getParameter("specialization");

        // 1. Fetch filtered list from DB
        List<User> doctors;

        if (spec == null || spec.trim().isEmpty()) {
            // Fetch all doctors from the database
            doctors = userDAO.getAllDoctors();
        } else {
            // Fetch only doctors matching the specialization
            doctors = userDAO.getDoctorsBySpecialization(spec);
        }

        // 2. Setup Response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 3. Convert to JSON and send
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(doctors));
        out.flush();
    }
}
