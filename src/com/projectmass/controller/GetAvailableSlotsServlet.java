package com.projectmass.controller;

import com.google.gson.Gson;
import com.projectmass.dao.AppointmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/getAvailableSlots") // ✅ Uncommented to match your JS fetch URL
public class GetAvailableSlotsServlet extends HttpServlet {

    private final AppointmentDAO apptDAO = new AppointmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get Parameters
        String doctorIdStr = request.getParameter("doctorId");
        String dateStr = request.getParameter("date");

        // 2. Setup Response Headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<String> availableSlots = new ArrayList<>();

        // 3. Logic with Safety Check
        // Added check for .isEmpty() to prevent NumberFormatException for input string: ""
        if (doctorIdStr != null && !doctorIdStr.isEmpty() && dateStr != null && !dateStr.isEmpty()) {
            try {
                int doctorId = Integer.parseInt(doctorIdStr);

                // Assuming your DAO method is implemented to accept (int, String)
                availableSlots = apptDAO.getAvailableSlots(doctorId, dateStr);

            } catch (NumberFormatException e) {
                // Log error but return empty list instead of crashing with 500 error
                System.err.println("Invalid Doctor ID format: " + doctorIdStr);
            }
        }

        // 4. Send JSON Response
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(availableSlots));
        out.flush();
    }
}