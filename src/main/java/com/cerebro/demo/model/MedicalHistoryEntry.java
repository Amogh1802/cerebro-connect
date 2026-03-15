package com.cerebro.demo.model;

import java.time.LocalDateTime;

public class MedicalHistoryEntry {
    private LocalDateTime date;
    private String notes;
    private String prescriptions;   // Could be JSON or comma-separated

    public MedicalHistoryEntry() {
    }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPrescriptions() { return prescriptions; }
    public void setPrescriptions(String prescriptions) { this.prescriptions = prescriptions; }
}