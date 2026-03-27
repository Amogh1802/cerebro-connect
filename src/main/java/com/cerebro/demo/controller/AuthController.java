package com.cerebro.demo.controller;

import com.cerebro.demo.dao.PatientDAO;
import com.cerebro.demo.dao.UserDAO;
import com.cerebro.demo.model.Patient;
import com.cerebro.demo.model.User;
import com.cerebro.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public static class AuthRequest {
        private String email;
        private String password;
        private String role;
        private String name;

        public AuthRequest() {}
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null
                    || request.getRole() == null || request.getName() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Missing required fields"));
            }

            if (userDAO.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User already exists"));
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole().toUpperCase());
            user.setName(request.getName());
            user.setProfileInfo("{}");

            userDAO.save(user);

            // Reload to get generated ID
            User savedUser = userDAO.findByEmail(request.getEmail()).orElse(user);

            // Auto-create patient record on patient registration
            if ("PATIENT".equals(savedUser.getRole())) {
                Patient patient = new Patient();
                patient.setId(savedUser.getId());
                patient.setDoctorIds(new ArrayList<>());
                patientDAO.save(patient);
            }

            // ✅ CHANGED: Generate token with email only
            String token = jwtUtil.generateToken(savedUser.getEmail());

            Map<String, String> response = Map.of(
                    "message", "User registered successfully",
                    "token", token,
                    "role", savedUser.getRole(),
                    "name", savedUser.getName(),
                    "id", String.valueOf(savedUser.getId())
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Missing email or password"));
            }

            User user = userDAO.findByEmail(request.getEmail()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }

            if (user.getPassword() == null ||
                    !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }

            // ✅ CHANGED: Generate token with email only
            String token = jwtUtil.generateToken(user.getEmail());

            Map<String, String> response = Map.of(
                    "message", "Login successful",
                    "token", token,
                    "role", user.getRole(),
                    "name", user.getName(),
                    "id", String.valueOf(user.getId())
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }
}