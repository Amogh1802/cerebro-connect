package com.cerebro.demo.model;

import java.time.LocalDateTime;

public class EEGSession {
    private Long id;
    private Long patientId;
    private String mode;            // "DEMENTIA", "COMA", "GENERAL"
    private String notes;           // ADDED: session notes
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String rawData;         // JSON or serialized raw signals
    private String processedData;   // JSON (delta/theta/alpha/beta/gamma power, P300/N400 values)

    public EEGSession() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    // ADDED: notes getter & setter
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }

    public String getProcessedData() { return processedData; }
    public void setProcessedData(String processedData) { this.processedData = processedData; }

    @Override
    public String toString() {
        return "EEGSession{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", mode='" + mode + '\'' +
                ", notes='" + notes + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}