package com.skillsharing.platform.skillsharing.model;

public class UserProfile {
    private String id;
    private String fullName;
    private String email;
    private String bio;
    private String country;
    private String gender;
    private String language;
    private String profilePictureUrl;

    public UserProfile() {
    }

    public UserProfile(String id, String fullName, String email, String bio, String country, String gender, String language, String profilePictureUrl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.country = country;
        this.gender = gender;
        this.language = language;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
}
