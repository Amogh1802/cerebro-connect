package com.cerebro.demo.model;

import java.time.LocalDateTime;

public class EEGLog {

    private Long id;
    private LocalDateTime timestamp;
    private String rawSample;          // e.g. voltage value or small array snippet
    private String processedBands;     // JSON: {"delta": 12.5, "theta": 8.2, "alpha": 15.3, ...}
    private String erpComponents;      // JSON: {"P300": {"amplitude": 4.2, "latency": 320}, ...}
    private String mode;               // "DEMENTIA", "COMA", "GENERAL"

    // Default constructor
    public EEGLog() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRawSample() {
        return rawSample;
    }

    public void setRawSample(String rawSample) {
        this.rawSample = rawSample;
    }

    public String getProcessedBands() {
        return processedBands;
    }

    public void setProcessedBands(String processedBands) {
        this.processedBands = processedBands;
    }

    public String getErpComponents() {
        return erpComponents;
    }

    public void setErpComponents(String erpComponents) {
        this.erpComponents = erpComponents;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}