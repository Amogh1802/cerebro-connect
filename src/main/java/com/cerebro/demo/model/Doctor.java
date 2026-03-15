package com.cerebro.demo.model;

import java.util.List;

public class Doctor {
    private Long id;                // Same as corresponding User.id
    private List<Long> patientIds;  // List of patient IDs this doctor treats

    public Doctor() {
    }

    public Doctor(Long id, List<Long> patientIds) {
        this.id = id;
        this.patientIds = patientIds;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Long> getPatientIds() { return patientIds; }
    public void setPatientIds(List<Long> patientIds) { this.patientIds = patientIds; }
}