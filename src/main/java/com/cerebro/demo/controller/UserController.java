package com.cerebro.demo.controller;

import com.cerebro.demo.dao.UserDAO;
import com.cerebro.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/patients")
    public ResponseEntity<List<Map<String, Object>>> getAllPatients() {
        List<User> patients = userDAO.findByRole("PATIENT");
        List<Map<String, Object>> result = patients.stream().map(u -> Map.of(
                "id", (Object) u.getId(),
                "name", u.getName(),
                "email", u.getEmail()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Map<String, Object>>> getAllDoctors() {
        List<User> doctors = userDAO.findByRole("DOCTOR");
        List<Map<String, Object>> result = doctors.stream().map(u -> Map.of(
                "id", (Object) u.getId(),
                "name", u.getName(),
                "email", u.getEmail()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}