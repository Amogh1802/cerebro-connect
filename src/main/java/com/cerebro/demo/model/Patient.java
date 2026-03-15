package com.cerebro.demo.model;

import java.util.List;

public class Patient {

    private Long id;                                // Same as corresponding User.id
    private List<Long> doctorIds;                   // Doctors treating this patient
    private List<MedicalHistoryEntry> medicalHistory;
    private List<EEGSession> reports;               // Past EEG sessions / reports
    private List<Prescription> prescriptions;
    private List<EEGLog> eegDataLogs;

    // Default constructor (important for frameworks like Spring/Jackson)
    public Patient() {
        // You can initialize lists here if you want to avoid null
        // this.doctorIds = new ArrayList<>();
        // this.medicalHistory = new ArrayList<>();
        // etc.
    }

    // Optional: constructor with most important fields
    public Patient(Long id, List<Long> doctorIds) {
        this.id = id;
        this.doctorIds = doctorIds;
    }

    // ────────────────────────────────────────────────
    //               Getters & Setters
    // ────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public List<MedicalHistoryEntry> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<MedicalHistoryEntry> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<EEGSession> getReports() {
        return reports;
    }

    public void setReports(List<EEGSession> reports) {
        this.reports = reports;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<EEGLog> getEegDataLogs() {
        return eegDataLogs;
    }

    public void setEegDataLogs(List<EEGLog> eegDataLogs) {
        this.eegDataLogs = eegDataLogs;
    }

    // Optional: toString() for easier debugging
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", doctorIds=" + doctorIds +
                ", reportsCount=" + (reports != null ? reports.size() : 0) +
                ", prescriptionsCount=" + (prescriptions != null ? prescriptions.size() : 0) +
                '}';
    }
}