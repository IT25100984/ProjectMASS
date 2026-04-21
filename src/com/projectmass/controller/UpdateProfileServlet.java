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

//@WebServlet("/updateProfile") // ✅ Step 1: Uncommented so Tomcat can route requests here!
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String bloodGroup = request.getParameter("bloodGroup");
        String medicalHistory = request.getParameter("medicalHistory");
        String specialization = request.getParameter("specialization"); // For Doctors

        UserDAO userDao = new UserDAO();
        boolean success = false;
        String redirectPage = "PATIENT".equals(user.getRole()) ? "patientDashboard" : "doctorDashboard";

        // 🩸 1. If it's a Patient updating Blood Group & History
        if ("PATIENT".equals(user.getRole())) {
            success = userDao.updateProfile(user.getUserID(), bloodGroup, medicalHistory);

            if (success) {
                user.setBloodGroup(bloodGroup);
                user.setMedicalHistory(medicalHistory);
                session.setAttribute("user", user); // Refresh Session Context
            }
        }
        // 🩺 2. If it's a Doctor updating Specialization
        else if ("DOCTOR".equals(user.getRole())) {
            success = userDao.updateProfile(user.getUserID(), specialization);

            if (success) {
                user.setSpecialization(specialization);
                session.setAttribute("user", user); // Refresh Session Context
            }
        }

        // ✅ Step 2: Unified redirection!
        if (success) {
            response.sendRedirect(redirectPage + "?updated=true");
        } else {
            response.sendRedirect(redirectPage + "?error=true");
        }
    }
}