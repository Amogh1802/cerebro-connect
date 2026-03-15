package com.cerebro.demo.controller;

import com.cerebro.demo.dao.PatientDAO;
import com.cerebro.demo.dao.UserDAO;
import com.cerebro.demo.model.Patient;
import com.cerebro.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private UserDAO userDAO;

    // GET all patients for a specific doctor (with name from users table)
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Map<String, Object>>> getPatientsByDoctor(@PathVariable Long doctorId) {
        List<Patient> patients = patientDAO.findByDoctorId(doctorId);

        List<Map<String, Object>> result = patients.stream().map(patient -> {
            User user = userDAO.findById(patient.getId()).orElse(null);
            return Map.of(
                    "id", patient.getId(),
                    "name", user != null ? user.getName() : "Unknown",
                    "email", user != null ? user.getEmail() : "",
                    "doctorIds", patient.getDoctorIds() != null ? patient.getDoctorIds() : List.of()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // GET all patients (admin/general use)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPatients() {
        List<Patient> patients = patientDAO.findAll();

        List<Map<String, Object>> result = patients.stream().map(patient -> {
            User user = userDAO.findById(patient.getId()).orElse(null);
            return Map.of(
                    "id", patient.getId(),
                    "name", user != null ? user.getName() : "Unknown",
                    "email", user != null ? user.getEmail() : "",
                    "doctorIds", patient.getDoctorIds() != null ? patient.getDoctorIds() : List.of()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Assign a patient to a doctor
    @PostMapping("/assign")
    public ResponseEntity<String> assignDoctor(@RequestBody Map<String, Long> body) {
        Long patientId = body.get("patientId");
        Long doctorId = body.get("doctorId");
        patientDAO.assignDoctor(patientId, doctorId);
        return ResponseEntity.ok("Patient assigned to doctor");
    }
}