package com.example.Artrental.novi.payload;

import com.example.Artrental.novi.model.Role;

import java.util.HashSet;
import java.util.Set;

public class UserSummary {
    private Long id;
    private String username;
    private String fullname;
    private Set<Role> roles = new HashSet<>();

    public UserSummary(Long id, String username, String fullname) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
    }

    public UserSummary(Long id, String username, String fullname, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.roles = roles;
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

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}