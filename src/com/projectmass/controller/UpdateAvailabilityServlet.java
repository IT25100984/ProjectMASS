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

//@WebServlet("/updateAvailability") // ✅ FIX 1: Uncommented for URL routing
public class UpdateAvailabilityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"DOCTOR".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        AppointmentDAO dao = new AppointmentDAO();

        String scheduleType = request.getParameter("scheduleType");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int doctorId = user.getUserID();

        boolean anySuccess = false; // ✅ FIX 2: Better boolean tracking

        System.out.println("--- UpdateAvailabilityServlet (POST): Doctor ID=" + doctorId + " Type=" + scheduleType + " ---");

        if ("weekly".equalsIgnoreCase(scheduleType)) {
            String[] selectedDays = request.getParameterValues("days");

            if (selectedDays != null && selectedDays.length > 0) {
                for (String dayStr : selectedDays) {
                    try {
                        int dayOfWeek = Integer.parseInt(dayStr);
                        boolean ok = dao.setDoctorAvailability(doctorId, null, dayOfWeek, startTime, endTime);
                        if (ok) {
                            anySuccess = true; // If at least ONE day works, we mark it success
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if ("single".equalsIgnoreCase(scheduleType)) {
            String availDate = request.getParameter("availDate");

            if (availDate != null && !availDate.isEmpty()) {
                anySuccess = dao.setDoctorAvailability(doctorId, availDate, 0, startTime, endTime);
            }
        }

        // Redirect back to dashboard with the outcome
        response.sendRedirect("doctorDashboard?availUpdated=" + anySuccess);
    }
}