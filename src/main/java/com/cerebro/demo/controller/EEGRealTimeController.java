package com.cerebro.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/eeg")
public class EEGRealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/realtime", consumes = "*/*", produces = "application/json")
    public Map<String, Object> receiveRealtimeEEG(@RequestBody String rawBody) {

        System.out.println("========== LABVIEW RAW BODY RECEIVED ==========");
        System.out.println(rawBody);

        Map<String, Object> data;
        try {
            data = objectMapper.readValue(rawBody, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            System.out.println("========== JSON PARSE ERROR ==========");
            e.printStackTrace();

            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid JSON received");
            error.put("rawBody", rawBody);
            error.put("error", e.getMessage());
            return error;
        }

        if (!data.containsKey("timestamp")) {
            data.put("timestamp", LocalDateTime.now().toString());
        }

        if (data.containsKey("alpha") && !data.containsKey("rmsAlpha")) {
            data.put("rmsAlpha", data.get("alpha"));
        }
        if (data.containsKey("beta") && !data.containsKey("rmsBeta")) {
            data.put("rmsBeta", data.get("beta"));
        }
        if (data.containsKey("theta") && !data.containsKey("rmsTheta")) {
            data.put("rmsTheta", data.get("theta"));
        }
        if (data.containsKey("delta") && !data.containsKey("rmsDelta")) {
            data.put("rmsDelta", data.get("delta"));
        }
        if (!data.containsKey("state")) {
            data.put("state", "UNKNOWN");
        }

        System.out.println("========== PARSED EEG DATA ==========");
        System.out.println(data);

        // Broadcast to frontend
        messagingTemplate.convertAndSend("/topic/eeg", (Object) data);

        System.out.println("========== SENT TO /topic/eeg ==========");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "EEG data received");
        response.put("data", data);
        return response;
    }
}