package com.projectmass.model;

/**
 * Admin class inherits from User.
 */
public class Admin extends User {

    public Admin() {
        super();
        this.setRole("ADMIN");
    }

    // Polymorphism: Unique dashboard for Admins
    @Override
    public void displayDashboard() {
        System.out.println("Displaying Admin Panel: System-wide oversight and user management.");
    }

    // Admin-specific method (Information Hiding/Abstraction)
    public void generateReport() {
        System.out.println("Generating system usage report...");
    }
}