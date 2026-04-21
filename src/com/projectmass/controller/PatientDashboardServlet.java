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

public class PatientDashboardServlet extends HttpServlet {

    private final AppointmentDAO apptDAO = new AppointmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Force browser to fetch fresh data every time (Fixes the "Hidden" issue)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser != null && "PATIENT".equals(currentUser.getRole())) {

            // 2. Fetch the latest appointments using the verified userID
            List<AppointmentDTO> myAppointments = apptDAO.getAppointmentsByPatient(currentUser.getUserID());

            // Debugging: Check IntelliJ console to see if data is actually being found
            System.out.println("Dashboard: Found " + (myAppointments != null ? myAppointments.size() : 0) + " appointments for " + currentUser.getFirstName());

            // 3. Set the attribute so the JSP can see it
            request.setAttribute("appointments", myAppointments);

            // 4. Forward to the JSP page
            request.getRequestDispatcher("patient_dashboard.jsp").forward(request, response);
        } else {
            // No valid session? Go to login.
            response.sendRedirect("login.jsp");
        }
    }
}