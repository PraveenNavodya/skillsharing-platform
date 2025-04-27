package com.skillsharing.platform.skillsharing.model;

public class UserProfile {
    private String id;
    private String fullName;
    private String email;
    private String bio;

    public UserProfile() {
    }

    public UserProfile(String id, String fullName, String email, String bio) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
