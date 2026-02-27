package com.projectmass.model;

public abstract class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
    private String role;

    public User() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        setFullName();
        return fullName;
    }
    public void setFullName() {
        this.firstName = this.firstName + this.lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public abstract void displayDashboard();
}
