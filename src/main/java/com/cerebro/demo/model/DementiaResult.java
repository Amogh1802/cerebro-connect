package com.cerebro.demo.model;

import java.time.LocalDateTime;

public class DementiaResult {

    private Long id;
    private Long patientId;
    private Long sessionId;
    private String prediction;     // e.g. "Healthy", "Mild Cognitive Impairment", "Dementia"
    private Double probability;    // e.g. 71.00
    private LocalDateTime recordedAt;

    public DementiaResult() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }

    public Double getProbability() { return probability; }
    public void setProbability(Double probability) { this.probability = probability; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    @Override
    public String toString() {
        return "DementiaResult{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", sessionId=" + sessionId +
                ", prediction='" + prediction + '\'' +
                ", probability=" + probability +
                ", recordedAt=" + recordedAt +
                '}';
    }
}