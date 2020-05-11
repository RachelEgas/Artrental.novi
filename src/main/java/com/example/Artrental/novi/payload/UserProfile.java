package com.example.Artrental.novi.payload;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String username;
    private String fullname;
    private Instant joinedAt;
    private long artCount;
    private long rentCount;

    public UserProfile(Long id, String username, String fullname, Instant joinedAt, long artCount, long rentCount) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.joinedAt = joinedAt;
        this.artCount = artCount;
        this.rentCount = rentCount;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String name) {
        this.fullname = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public long getArtCount() {
        return artCount;
    }

    public void setArtCount(long artCount) {
        this.artCount = artCount;
    }

    public long getRentCount() {
        return rentCount;
    }

    public void setRentCount(long rentCount) {
        this.rentCount = rentCount;
    }
}