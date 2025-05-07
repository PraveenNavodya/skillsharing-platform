package com.skillsharing.platform.skillsharing.model;

import java.util.List;

public class SkillPost {
    private String id;
    private String userId; // ID of the user who created the post
    private String description;
    private List<String> mediaUrls; // URLs of uploaded photos/videos
    private long timestamp;

    public SkillPost() {}

    public SkillPost(String id, String userId, String description, List<String> mediaUrls, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.mediaUrls = mediaUrls;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}