package com.projectmass.dto;

public class AppointmentDTO {
    private int appointmentID; // 👈 Add this field
    private String dateTime;
    private String oppositePartyName;
    private String status;

    public AppointmentDTO(String dateTime, String oppositePartyName, String status) {
        this.dateTime = dateTime;
        this.oppositePartyName = oppositePartyName;
        this.status = status;
    }

    // Getters and Setters
    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int id) { this.appointmentID = id; }

    public String getDateTime() { return dateTime; }
    public String getOppositePartyName() { return oppositePartyName; }
    public String getStatus() { return status; }
}