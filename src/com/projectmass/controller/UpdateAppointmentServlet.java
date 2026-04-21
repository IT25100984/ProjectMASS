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

//@WebServlet("/updateAppointment")
public class UpdateAppointmentServlet extends HttpServlet {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    // 🟢 Handles "Accept" and "Cancel" Clicks
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdStr = request.getParameter("id");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Fallback logic for redirection based on Role
        String redirectPage = "login.jsp";
        if (user != null) {
            redirectPage = "DOCTOR".equals(user.getRole()) ? "doctorDashboard" : "patientDashboard";
        }

        System.out.println("--- UpdateAppointmentServlet (GET): Role=" + (user != null ? user.getRole() : "NULL") +
                " | Action=" + action + " | ID=" + appointmentIdStr + " ---");

        if (appointmentIdStr != null && action != null) {
            try {
                int appointmentId = Integer.parseInt(appointmentIdStr);
                boolean success = false;

                if ("accept".equalsIgnoreCase(action)) {
                    // Doctor accepts patient request OR Patient accepts doctor's suggestion
                    success = appointmentDAO.updateAppointmentStatus(appointmentId, "CONFIRMED", null, null);
                } else if ("cancel".equalsIgnoreCase(action)) {
                    success = appointmentDAO.cancelAppointment(appointmentId);
                }

                if (success) {
                    System.out.println("--- UpdateAppointmentServlet: Action " + action + " succeeded! ---");
                    redirectPage += "?msg=success";
                } else {
                    System.out.println("--- UpdateAppointmentServlet: Action " + action + " failed! ---");
                    redirectPage += "?msg=error";
                }

            } catch (NumberFormatException e) {
                System.err.println("--- UpdateAppointmentServlet: Invalid ID format ---");
                redirectPage += "?msg=error";
            }
        }

        response.sendRedirect(redirectPage);
    }

    // 🟡 Handles "Suggest Time" Form Submissions (The Modal)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdStr = request.getParameter("id");
        String action = request.getParameter("action");
        String newDate = request.getParameter("newDate");
        String newTime = request.getParameter("newTime");

        System.out.println("--- UpdateAppointmentServlet (POST): Rescheduling ID=" + appointmentIdStr + " to " + newDate + " " + newTime + " ---");

        if (appointmentIdStr != null && "reschedule".equalsIgnoreCase(action)) {
            try {
                int appointmentId = Integer.parseInt(appointmentIdStr);

                // This flips the appointment to 'RESCHEDULED' status and sets the date/time
                boolean success = appointmentDAO.updateAppointmentStatus(appointmentId, "RESCHEDULED", newDate, newTime);

                if (success) {
                    System.out.println("--- UpdateAppointmentServlet: Successfully suggested new time! ---");
                    response.sendRedirect("doctorDashboard?msg=success");
                    return;
                }
            } catch (NumberFormatException e) {
                System.err.println("--- UpdateAppointmentServlet: Invalid ID format ---");
            }
        }

        response.sendRedirect("doctorDashboard?msg=error");
    }
}