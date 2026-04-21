package com.projectmass.controller;

import com.projectmass.dao.AppointmentDAO;
import com.projectmass.dto.AppointmentDTO;
import com.projectmass.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class DoctorDashboardServlet extends HttpServlet {

    private final AppointmentDAO apptDAO = new AppointmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user != null && "DOCTOR".equals(user.getRole())) {
            // Fetch appointments where the user is the DOCTOR
            List<AppointmentDTO> myAppts = apptDAO.getAppointmentsByDoctor(user.getUserID());

            // This name "myAppts" must match the ${myAppts} in your JSP loop!
            request.setAttribute("myAppts", myAppts);
            request.getRequestDispatcher("doctor_dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}