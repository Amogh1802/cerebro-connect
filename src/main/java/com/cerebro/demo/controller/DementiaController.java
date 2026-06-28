package com.cerebro.demo.controller;

import com.cerebro.demo.dao.DementiaResultDAO;
import com.cerebro.demo.model.DementiaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.cerebro.demo.dao.EEGSessionDAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eeg/dementia")
@CrossOrigin(origins = "*")
public class DementiaController {

    @Autowired
    private DementiaResultDAO dementiaResultDAO;

    @Autowired
    private EEGSessionDAO eegSessionDAO;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // LabVIEW posts the completed dementia prediction here
    @PostMapping("/result")
    public ResponseEntity<Map<String, Object>> receiveDementiaResult(@RequestBody DementiaResult result) {
        try {
            result.setRecordedAt(LocalDateTime.now());
            dementiaResultDAO.save(result);
            String notes =
                    "Dementia Probability: "
                            + result.getProbability()
                            + "% ("
                            + result.getPrediction()
                            + ")";

            eegSessionDAO.updateNotes(
                    result.getSessionId(),
                    notes
            );

            System.out.println("Dementia result saved: patient=" + result.getPatientId()
                    + " prediction=" + result.getPrediction()
                    + " probability=" + result.getProbability());

            // Push live to the React dashboard
            messagingTemplate.convertAndSend("/topic/dementia", (Object) result);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", result.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Failed to save dementia result: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<DementiaResult>> getResultsByPatient(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok(dementiaResultDAO.findByPatientId(patientId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<DementiaResult>> getResultsBySession(@PathVariable Long sessionId) {
        try {
            return ResponseEntity.ok(dementiaResultDAO.findBySessionId(sessionId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}