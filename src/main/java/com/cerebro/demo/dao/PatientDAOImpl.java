package com.cerebro.demo.dao;

import com.cerebro.demo.model.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientDAOImpl implements PatientDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;  // FIXED: removed static

    // FIXED: method instead of static field so it can use objectMapper
    private RowMapper<Patient> patientRowMapper() {
        return (rs, rowNum) -> {
            Patient patient = new Patient();
            patient.setId(rs.getLong("id"));
            try {
                String doctorIdsJson = rs.getString("doctor_ids");
                if (doctorIdsJson != null && !doctorIdsJson.isBlank()) {
                    patient.setDoctorIds(
                            objectMapper.readValue(doctorIdsJson, new TypeReference<List<Long>>() {})
                    );
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse patient JSON fields", e);
            }
            return patient;
        };
    }

    @Override
    public void save(Patient patient) {
        String sql = """
            INSERT INTO patients (id, doctor_ids)
            VALUES (?, ?)
            """;
        String doctorIdsJson = toJson(patient.getDoctorIds());
        jdbcTemplate.update(sql, patient.getId(), doctorIdsJson);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        List<Patient> results = jdbcTemplate.query(sql, patientRowMapper(), id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients";
        return jdbcTemplate.query(sql, patientRowMapper());
    }

    @Override
    public void update(Patient patient) {
        String sql = """
            UPDATE patients
            SET doctor_ids = ?
            WHERE id = ?
            """;
        String doctorIdsJson = toJson(patient.getDoctorIds());
        jdbcTemplate.update(sql, doctorIdsJson, patient.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Patient> findByDoctorId(Long doctorId) {
        String sql = """
            SELECT * FROM patients
            WHERE JSON_CONTAINS(doctor_ids, CAST(? AS JSON))
            """;
        return jdbcTemplate.query(sql, patientRowMapper(), doctorId);
    }

    @Override
    public void assignDoctor(Long patientId, Long doctorId) {
        String sql = """
            UPDATE patients
            SET doctor_ids = JSON_ARRAY_APPEND(
                COALESCE(doctor_ids, '[]'),
                '$',
                ?
            )
            WHERE id = ?
            """;
        jdbcTemplate.update(sql, doctorId, patientId);
    }

    private String toJson(Object obj) {
        if (obj == null) return "[]";
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize to JSON", e);
        }
    }
}