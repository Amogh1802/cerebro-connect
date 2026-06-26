package com.cerebro.demo.model;

import java.time.LocalDateTime;

public class P300Result {

    private Long id;
    private Long patientId;
    private Long sessionId;
    private String mode;              // e.g. "COMA"
    private Integer trialsAveraged;   // e.g. 10
    private Double amplitude;         // e.g. 1.28
    private Integer latencyMs;        // e.g. 460
    private Boolean detected;         // true if "P300 Response Detected"
    private LocalDateTime recordedAt;

    public P300Result() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Integer getTrialsAveraged() { return trialsAveraged; }
    public void setTrialsAveraged(Integer trialsAveraged) { this.trialsAveraged = trialsAveraged; }

    public Double getAmplitude() { return amplitude; }
    public void setAmplitude(Double amplitude) { this.amplitude = amplitude; }

    public Integer getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Integer latencyMs) { this.latencyMs = latencyMs; }

    public Boolean getDetected() { return detected; }
    public void setDetected(Boolean detected) { this.detected = detected; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    @Override
    public String toString() {
        return "P300Result{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", sessionId=" + sessionId +
                ", mode='" + mode + '\'' +
                ", trialsAveraged=" + trialsAveraged +
                ", amplitude=" + amplitude +
                ", latencyMs=" + latencyMs +
                ", detected=" + detected +
                ", recordedAt=" + recordedAt +
                '}';
    }
}