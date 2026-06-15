package com.cerebro.demo.model;

public class StimulusData {

    private String stimulus;
    private long timestamp;

    public StimulusData() {}

    public StimulusData(String stimulus, long timestamp) {
        this.stimulus = stimulus;
        this.timestamp = timestamp;
    }

    public String getStimulus() {
        return stimulus;
    }

    public void setStimulus(String stimulus) {
        this.stimulus = stimulus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}