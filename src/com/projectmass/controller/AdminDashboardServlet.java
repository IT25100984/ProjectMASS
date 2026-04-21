package com.projectmass.controller;

import com.projectmass.dao.AppointmentDAO;
import com.projectmass.dto.AppointmentDTO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

//@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private AppointmentDAO apptDAO = new AppointmentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        // Security check: Only let Admins in
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {

            // Fetch all appointments for the whole hospital
            List<AppointmentDTO> allAppointments = apptDAO.getAllAppointments();

            request.setAttribute("adminApps", allAppointments);
            request.getRequestDispatcher("admin_dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}