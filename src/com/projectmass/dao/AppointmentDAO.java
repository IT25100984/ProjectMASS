package com.projectmass.dao;

import com.projectmass.dto.AppointmentDTO;
import com.projectmass.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentDAO {

    // ==========================================
    // SECTION 1: APPOINTMENT FETCHING
    // ==========================================

    public List<AppointmentDTO> getAppointmentsByPatient(int patientID) {
        List<AppointmentDTO> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.appt_date, a.appt_time, u.first_name, u.last_name, a.status " +
                "FROM appointments a " +
                "JOIN users u ON a.doctor_id = u.user_id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appt_date DESC, a.appt_time ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = Objects.requireNonNull(conn).prepareStatement(sql)) {
            ps.setInt(1, patientID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String rawTime = rs.getString("appt_time");
                    if (rawTime != null && rawTime.length() >= 5) rawTime = rawTime.substring(0, 5);
                    AppointmentDTO dto = new AppointmentDTO(
                            rs.getString("appt_date") + " at " + rawTime,
                            rs.getString("first_name") + " " + rs.getString("last_name"),
                            rs.getString("status")
                    );
                    dto.setAppointmentID(rs.getInt("appointment_id"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<AppointmentDTO> getAppointmentsByDoctor(int doctorID) {
        List<AppointmentDTO> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.appt_date, a.appt_time, u.first_name, u.last_name, a.status " +
                "FROM appointments a " +
                "JOIN users u ON a.patient_id = u.user_id " +
                "WHERE a.doctor_id = ? " +
                "ORDER BY a.appt_date DESC, a.appt_time ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = Objects.requireNonNull(conn).prepareStatement(sql)) {
            ps.setInt(1, doctorID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String rawTime = rs.getString("appt_time");
                    if (rawTime != null && rawTime.length() >= 5) rawTime = rawTime.substring(0, 5);
                    AppointmentDTO dto = new AppointmentDTO(
                            rs.getString("appt_date") + " at " + rawTime,
                            rs.getString("first_name") + " " + rs.getString("last_name"),
                            rs.getString("status")
                    );
                    dto.setAppointmentID(rs.getInt("appointment_id"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentDTO> list = new ArrayList<>();
        String sql = "SELECT a.appointment_id, a.appt_date, a.appt_time, " +
                "d.last_name AS docName, p.last_name AS patName, a.status " +
                "FROM appointments a " +
                "JOIN users d ON a.doctor_id = d.user_id " +
                "JOIN users p ON a.patient_id = p.user_id " +
                "ORDER BY a.appt_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = Objects.requireNonNull(conn).prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String rawTime = rs.getString("appt_time");
                if (rawTime != null && rawTime.length() >= 5) rawTime = rawTime.substring(0, 5);
                AppointmentDTO dto = new AppointmentDTO(
                        rs.getString("appt_date") + " " + rawTime,
                        rs.getString("docName") + " | Patient: " + rs.getString("patName"),
                        rs.getString("status")
                );
                dto.setAppointmentID(rs.getInt("appointment_id"));
                list.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ==========================================
    // SECTION 2: BOOKING & STATUS UPDATES
    // ==========================================

    public boolean bookAppointment(int doctorID, int patientID, String date, String time) {
        String checkSql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appt_date = ? AND appt_time = ? AND status != 'CANCELLED'";
        String insertSql = "INSERT INTO appointments (doctor_id, patient_id, appt_date, appt_time, status) VALUES (?, ?, ?, ?, 'PENDING')";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, doctorID);
                checkStmt.setString(2, date);
                checkStmt.setString(3, time);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) return false;
            }
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, doctorID);
                insertStmt.setInt(2, patientID);
                insertStmt.setString(3, date);
                insertStmt.setString(4, time);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateAppointmentStatus(int appointmentID, String status, String newDate, String newTime) {
        String sqlWithTime = "UPDATE appointments SET status = ?, appt_date = ?, appt_time = ? WHERE appointment_id = ?";
        String sqlStatusOnly = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

        boolean isReschedule = (newDate != null && !newDate.isEmpty() && newTime != null && !newTime.isEmpty());
        String query = isReschedule ? sqlWithTime : sqlStatusOnly;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, status);
            if (isReschedule) {
                ps.setString(2, newDate);
                ps.setString(3, newTime);
                ps.setInt(4, appointmentID);
            } else {
                ps.setInt(2, appointmentID);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean cancelAppointment(int appointmentId) {
        String sql = "UPDATE appointments SET status = 'CANCELLED' WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ==========================================
    // SECTION 3: AVAILABILITY & SLOTS
    // ==========================================

    public boolean setDoctorAvailability(int doctorId, String unusedDate, Integer dayOfWeek, String startTime, String endTime) {
        String query = "INSERT INTO doctor_availability (doctor_id, available_date, day_of_week, start_time, end_time) VALUES (?, NULL, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, dayOfWeek);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean setDoctorAvailability(int doctorId, String availableDate, String startTime, String endTime) {
        String query = "INSERT INTO doctor_availability (doctor_id, available_date, day_of_week, start_time, end_time) VALUES (?, ?, NULL, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, doctorId);
            ps.setString(2, availableDate);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Logic:
     * 1. Finds the doctor's general shift (start to end hour).
     * 2. Finds already booked appointments for that doctor/day.
     * 3. Returns the "Remainder" (Shift Hours - Booked Hours).
     */
    public List<String> getAvailableSlots(int doctorId, String date) {
        List<String> availableSlots = new ArrayList<>();
        String dbDate;
        int dayOfWeekInt;

        try {
            // Normalize Date Format: UI sends DD-MM-YYYY, DB needs YYYY-MM-DD
            DateTimeFormatter uiFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, uiFormatter);
            dbDate = localDate.toString();
            dayOfWeekInt = localDate.getDayOfWeek().getValue();
        } catch (Exception e) {
            System.out.println("Invalid date format received in DAO: " + date);
            return availableSlots;
        }

        // Query Availability
        String availSql = "SELECT start_time, end_time FROM doctor_availability " +
                "WHERE doctor_id = ? AND (available_date = ? OR day_of_week = ?) " +
                "ORDER BY available_date DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psAvail = conn.prepareStatement(availSql)) {

            psAvail.setInt(1, doctorId);
            psAvail.setString(2, dbDate);
            psAvail.setInt(3, dayOfWeekInt);

            ResultSet rsAvail = psAvail.executeQuery();
            if (rsAvail.next()) {
                int startHour = rsAvail.getTime("start_time").toLocalTime().getHour();
                int endHour = rsAvail.getTime("end_time").toLocalTime().getHour();

                // Fetch Booked Hours for this specific day
                List<String> bookedHours = new ArrayList<>();
                String bookedSql = "SELECT appt_time FROM appointments WHERE doctor_id = ? AND appt_date = ? AND status != 'CANCELLED'";

                try (PreparedStatement psBooked = conn.prepareStatement(bookedSql)) {
                    psBooked.setInt(1, doctorId);
                    psBooked.setString(2, dbDate);
                    ResultSet rsBooked = psBooked.executeQuery();
                    while (rsBooked.next()) {
                        Time bTime = rsBooked.getTime("appt_time");
                        if (bTime != null) {
                            bookedHours.add(String.format("%02d:00", bTime.toLocalTime().getHour()));
                        }
                    }
                }

                // Subtraction Logic: Add to list if NOT booked
                for (int h = startHour; h < endHour; h++) {
                    String hourStr = String.format("%02d:00", h);
                    if (!bookedHours.contains(hourStr)) {
                        availableSlots.add(hourStr);
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return availableSlots;
    }
}