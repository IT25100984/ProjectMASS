package com.projectmass.model;

/**
 * Patient class inherits from User.
 */
public class Patient extends User {
    // Encapsulation: Private fields for data privacy
    private String medicalHistory;
    private String bloodGroup;

    public Patient() {
        super();
        this.setRole("PATIENT");
    }

    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    // Polymorphism: Unique dashboard for Patients
    @Override
    public void displayDashboard() {
        System.out.println("Displaying Patient Portal: View your appointments and history.");
    }
}