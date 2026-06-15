package com.cerebro.demo.controller;

import com.cerebro.demo.dao.EEGSessionDAO;
import com.cerebro.demo.model.EEGSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/session")
    public ResponseEntity<Map<String, Object>> startSession(@RequestBody EEGSession session) {
        try {
            session.setStartTime(LocalDateTime.now());
            eegSessionDAO.save(session);

            // Broadcast mode to LabVIEW via WebSocket
            Map<String, Object> modePayload = new HashMap<>();
            modePayload.put("mode", session.getMode());
            modePayload.put("patientId", session.getPatientId());
            modePayload.put("sessionId", session.getId());
            messagingTemplate.convertAndSend("/topic/mode", (Object) modePayload);
            System.out.println(" Mode broadcast to /topic/mode: " + session.getMode());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Session started");
            response.put("id", session.getId());
            response.put("patientId", session.getPatientId());

            System.out.println(" Session saved to DB: ID=" + session.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println(" Failed to save session: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/sessions/{patientId}")
    public ResponseEntity<List<EEGSession>> getSessionsByPatient(@PathVariable Long patientId) {
        try {
            List<EEGSession> sessions = eegSessionDAO.findByPatientId(patientId);
            System.out.println(" Fetched " + sessions.size() + " sessions for patient " + patientId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println(" Failed to fetch sessions for patient " + patientId + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}