package com.cerebro.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stimulus")
@CrossOrigin("*")
public class StimulusController {

    private static String currentStimulus = "NONE";
    private static long timestamp = 0;

    @PostMapping
    public ResponseEntity<?> postStimulus(
            @RequestBody Map<String,Object> body){

        currentStimulus = body.get("stimulus").toString();
        timestamp = System.currentTimeMillis();

        System.out.println(
                "Stimulus Received : "
                        + currentStimulus
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Map<String,Object> getStimulus(){

        Map<String,Object> response = new HashMap<>();

        response.put("stimulus",currentStimulus);
        response.put("timestamp",timestamp);

        return response;
    }
}