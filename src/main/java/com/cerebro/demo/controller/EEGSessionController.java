package com.cerebro.demo.controller;

import com.cerebro.demo.dao.EEGSessionDAO;
import com.cerebro.demo.model.EEGSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/eeg")
public class EEGSessionController {

    @Autowired
    private EEGSessionDAO eegSessionDAO;

    @PostMapping("/session")
    public ResponseEntity<String> startSession(@RequestBody EEGSession session) {
        session.setStartTime(LocalDateTime.now());
        eegSessionDAO.save(session);
        return ResponseEntity.ok("Session started with ID: " + session.getId());
    }
    @GetMapping("/sessions/{patientId}")
    public ResponseEntity<List<EEGSession>> getSessions(@PathVariable Long patientId) {
        return ResponseEntity.ok(eegSessionDAO.findByPatientId(patientId));
    }
}