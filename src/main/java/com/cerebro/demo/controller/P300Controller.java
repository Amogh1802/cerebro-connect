package com.cerebro.demo.controller;

import com.cerebro.demo.dao.P300ResultDAO;
import com.cerebro.demo.model.P300Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eeg/p300")
@CrossOrigin(origins = "*")
public class P300Controller {

    @Autowired
    private P300ResultDAO p300ResultDAO;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // LabVIEW posts the completed P300 result here
    @PostMapping("/result")
    public ResponseEntity<Map<String, Object>> receiveP300Result(
            @RequestBody P300Result result) {

        System.out.println("====================================");
        System.out.println("ENTERED P300 API");
        System.out.println("Received object:");
        System.out.println(result);
        System.out.println("====================================");

        try {

            System.out.println("Setting timestamp...");
            result.setRecordedAt(LocalDateTime.now());

            System.out.println("Saving to database...");
            p300ResultDAO.save(result);

            System.out.println("DATABASE SAVE SUCCESSFUL");
            System.out.println(
                    "patient=" + result.getPatientId()
                            + " amplitude=" + result.getAmplitude()
                            + " latency=" + result.getLatencyMs()
                            + " detected=" + result.getDetected()
            );

            System.out.println("Sending websocket update...");
            messagingTemplate.convertAndSend("/topic/p300", result);
            System.out.println("Websocket update sent.");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", result.getId());

            System.out.println("Returning HTTP response...");
            System.out.println("====================================");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            System.err.println("====================================");
            System.err.println("P300 API FAILED");
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println("====================================");

            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "success", false,
                            "error", e.getMessage()
                    ));
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<P300Result>> getResultsByPatient(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok(p300ResultDAO.findByPatientId(patientId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<P300Result>> getResultsBySession(@PathVariable Long sessionId) {
        try {
            return ResponseEntity.ok(p300ResultDAO.findBySessionId(sessionId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}