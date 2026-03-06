package com.projectmass.model;


public class Patient extends User {
    private String medicalHistory;
    private String bloodGroup;

    // patient contractor to initialize new patient data
    public Patient(String bloodGroup, String medicalHistory, String firstName, String lastName) {
        super();
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setRole("PATIENT");
    }
    // updatePatient method to edit current patient data
    public void updatePatient(String bloodGroup, String medicalHistory, String firstName, String lastName) {
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setRole("PATIENT");
    }

    // Getters and Setters to retrieve patient private data
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

    /**
     * Using Polymorphism to Override a method from User Object
     * This way we can create a custom dashboard view for patients only.
     */
    @Override
    public void displayDashboard() {
        System.out.println("Displaying Patient Portal: View your appointments and history.");

    }
}