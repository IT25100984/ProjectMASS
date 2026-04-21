package com.projectmass.dao;

import com.projectmass.model.Doctor;
import com.projectmass.model.Patient;
import com.projectmass.model.User;
import com.projectmass.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {

    public List<User> getAllDoctors() {
        List<User> doctors = new ArrayList<>();
        String query = "SELECT user_id, first_name, last_name, specialization FROM users WHERE role = 'DOCTOR'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User doc = new User();
                doc.setUserID(rs.getInt("user_id"));
                doc.setFirstName(rs.getString("first_name"));
                doc.setLastName(rs.getString("last_name"));
                doc.setSpecialization(rs.getString("specialization"));
                doctors.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public List<User> getAllUsersForAdmin() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String role = rs.getString("role");
                User user;

                if ("DOCTOR".equalsIgnoreCase(role)) {
                    user = new Doctor(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("specialization")
                    );
                } else if ("PATIENT".equalsIgnoreCase(role)) {
                    user = new Patient(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("blood_group")
                    );
                } else {
                    user = new User(); // For admin or generic fallback
                    user.setUserID(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                }

                user.setEmail(rs.getString("email"));
                user.setRole(role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword()); // In a real app, hash this!
            ps.setString(5, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ✅ Merged Login method that handles Sub-Models & Column Names correctly
     */
    public User login(String email, String password) {
        System.out.println("--- UserDAO: Attempting login for email: " + email + " ---");
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                User user;

                if ("DOCTOR".equalsIgnoreCase(role)) {
                    user = new Doctor();
                    user.setSpecialization(rs.getString("specialization"));
                } else if ("PATIENT".equalsIgnoreCase(role)) {
                    user = new Patient();
                    user.setBloodGroup(rs.getString("blood_group"));
                    user.setMedicalHistory(rs.getString("medical_history"));
                } else {
                    user = new User(); // Admin fallback
                }

                user.setUserID(rs.getInt("user_id")); // Fixed index naming
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(role);

                System.out.println("--- UserDAO: Login Successful for " + email + " ---");
                return user;
            } else {
                System.out.println("--- UserDAO: No user found with those credentials ---");
            }
        } catch (SQLException e) {
            System.err.println("--- UserDAO ERROR: " + e.getMessage() + " ---");
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(int userId, String blood, String history) {
        String query = "UPDATE users SET blood_group=?, medical_history=? WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, blood);
            ps.setString(2, history);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getDoctorsBySpecialization(String specialization) {
        List<User> doctors = new ArrayList<>();
        String query = "SELECT user_id, first_name, last_name, specialization FROM users WHERE role = 'DOCTOR' AND specialization = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User doctor = new User();
                doctor.setUserID(rs.getInt("user_id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public boolean updateProfile(int userId, String spec) {
        String query = "UPDATE users SET specialization=? WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, spec);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}