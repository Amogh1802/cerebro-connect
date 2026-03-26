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
    public void receiveRealtime(@RequestBody Map<String, Object> data) {
        // FIXED: explicit cast to String to resolve ambiguity
        messagingTemplate.convertAndSend((String) "/topic/eeg", (Object) data);
    }
}