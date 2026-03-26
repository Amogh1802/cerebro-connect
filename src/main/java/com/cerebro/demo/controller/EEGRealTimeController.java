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
public class EEGRealtimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Python bridge POSTs here → broadcasts to all React clients
    @PostMapping("/realtime")
    public void receiveRealtime(@RequestBody Map<String, Object> data) {
        messagingTemplate.convertAndSend("/topic/eeg", data);
    }
}