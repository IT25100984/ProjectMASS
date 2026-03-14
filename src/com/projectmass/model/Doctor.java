package com.projectmass.model;

public class Doctor extends User {
    // Encapsulation of private attributes specific to Doctors
    private String specialization;
    private String licenseID;
    private double consultationFee;

    // Default Constructor for Doctor object
    public Doctor() {
        super(); // this super() calls the empty constructor of the User class
    }

    // Overloaded constructor to initialize Doctor attributes
    public Doctor(int id, String firstName, String lastName, String email, String password, String specialization, String licenseID) {
        super(id, firstName, lastName, email, password, "DOCTOR");
        this.specialization = specialization;
        this.licenseID = licenseID;
    }
    // updateDoctor to edit current Doctor attributes
    public void updateDoctor(int id, String firstName, String lastName, String email, String password, String specialization, String licenseID) {
        this.setUserID(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole("DOCTOR");
        this.specialization = specialization;
        this.licenseID = licenseID;
    }

    // Getters and Setters implemented to access private attributes
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseID() {
        return licenseID;
    }
    public void setLicenseID(String licenseID) { this.licenseID = licenseID;}

    /**
     * Using Polymorphism to Override a method from User Object
     * This way we can create a custom dashboard view for doctors.
     */
    @Override
    public void displayDashboard() {
        System.out.println("Displaying Doctor Portal for: " + getFirstName() + " " + getLastName());
    }
}