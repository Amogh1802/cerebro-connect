package com.cerebro.demo.controller;

import com.cerebro.demo.dao.EEGSessionDAO;
import com.cerebro.demo.model.EEGSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eeg")
public class EEGSessionController {

    @Autowired
    private EEGSessionDAO eegSessionDAO;

    @PostMapping("/session")
    public ResponseEntity<Map<String, Object>> startSession(@RequestBody EEGSession session) {
        try {
            session.setStartTime(LocalDateTime.now());
            eegSessionDAO.save(session);

            // Return session with ID
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Session started");
            response.put("id", session.getId());
            response.put("patientId", session.getPatientId());

            System.out.println("✅ Session saved to DB: ID=" + session.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Failed to save session: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}