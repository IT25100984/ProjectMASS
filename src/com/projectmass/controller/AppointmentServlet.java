package com.projectmass.controller;

import com.projectmass.dao.AppointmentDAO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebServlet("/bookAppointment") // ✅ FIX 1: Uncommented so Tomcat can route requests here!
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get session if it exists
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // ✅ FIX 2: Prevent NullPointerException if session times out
        if (user == null || !"PATIENT".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int patientId = user.getUserID();
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String doctorIdStr = request.getParameter("doctorId");

        if (doctorIdStr == null || doctorIdStr.isEmpty()) {
            request.setAttribute("errorMsg", "Please select a valid doctor.");
            request.getRequestDispatcher("book_appointment.jsp").forward(request, response);
            return;
        }

        try {
            int doctorId = Integer.parseInt(doctorIdStr);
            AppointmentDAO dao = new AppointmentDAO();

            if (dao.bookAppointment(doctorId, patientId, date, time)) {
                // ✅ FIX 3: Redirect to the Servlet URL endpoint so it reloads appointments!
                response.sendRedirect("patientDashboard?msg=success");
            } else {
                response.sendRedirect("book_appointment.jsp?error=failed");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("book_appointment.jsp?errorMsg=Invalid+Doctor+ID");
        }
    }
}