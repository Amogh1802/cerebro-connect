package com.cerebro.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class User implements UserDetails {
    private Long id;
    private String role;        // "DOCTOR" or "PATIENT"
    private String email;
    private String password;    // Hashed
    private String name;
    private String profileInfo; // JSON string

    // Default constructor
    public User() {
    }

    // All-args constructor
    public User(Long id, String role, String email, String password, String name, String profileInfo) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileInfo = profileInfo;
    }

    // Getters & Setters (keep all from before)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProfileInfo() { return profileInfo; }
    public void setProfileInfo(String profileInfo) { this.profileInfo = profileInfo; }

    // ────────────────────────────────────────────────
    // UserDetails Implementation (for Spring Security + JWT)
    // ────────────────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role to Spring authority (e.g., "ROLE_DOCTOR")
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return email;  // Use email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize if you add expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", role='" + role + "', email='" + email + "', name='" + name + "'}";
    }
}