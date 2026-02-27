package com.projectmass.model;

public class Doctor extends User {
    // Encapsulation: Private fields specific to Doctors [cite: 57, 103]
    private String specialization;
    private String licenseID;
    private double consultationFee;

    // Default Constructor
    public Doctor() {
        super(); // Calls the constructor of the User class
    }

    // Overloaded Constructor
    public Doctor(int id, String firstName, String lastName, String email, String specialization, String licenseID) {
        this.setUserID(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setRole("DOCTOR");
        this.specialization = specialization;
        this.licenseID = licenseID;
    }

    // Getters and Setters (Information Hiding)
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseID() {
        return licenseID;
    }
    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    /**
     * Polymorphism: You could override a method from User here[cite: 7, 59].
     * For example, a custom dashboard view for doctors.
     */
    @Override
    public void displayDashboard() {
        System.out.println("Displaying Doctor Portal for: " + getFullName());
    }
}