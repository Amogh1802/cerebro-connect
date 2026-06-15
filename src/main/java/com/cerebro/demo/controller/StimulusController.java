package com.cerebro.demo.controller;

import com.cerebro.demo.model.StimulusData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stimulus")
@CrossOrigin("*")
public class StimulusController {

    private static StimulusData latestStimulus =
            new StimulusData("NONE", 0);

    @PostMapping
    public ResponseEntity<?> receiveStimulus(
            @RequestBody StimulusData stimulusData) {

        latestStimulus = stimulusData;

        System.out.println(
                "Stimulus Received : "
                        + stimulusData.getStimulus()
                        + " Time : "
                        + stimulusData.getTimestamp()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<StimulusData> getLatestStimulus() {
        return ResponseEntity.ok(latestStimulus);
    }
}