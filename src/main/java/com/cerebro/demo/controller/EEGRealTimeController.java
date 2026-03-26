package com.cerebro.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/eeg")
public class EEGRealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/realtime")
    public Map<String, Object> receiveRealtimeEEG(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("timestamp")) {
            data.put("timestamp", LocalDateTime.now().toString());
        }

        // Optional compatibility mapping for your frontend cards
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

        messagingTemplate.convertAndSend("/topic/eeg", (Object) data);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "EEG data received");
        response.put("data", data);
        return response;
    }
}