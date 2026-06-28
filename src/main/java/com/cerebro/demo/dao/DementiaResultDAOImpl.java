package com.cerebro.demo.dao;

import com.cerebro.demo.model.DementiaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class DementiaResultDAOImpl implements DementiaResultDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<DementiaResult> DEMENTIA_ROW_MAPPER = (rs, rowNum) -> {
        DementiaResult result = new DementiaResult();
        result.setId(rs.getLong("id"));
        result.setPatientId(rs.getLong("patient_id"));
        result.setSessionId(rs.getLong("session_id"));
        result.setPrediction(rs.getString("prediction"));
        result.setProbability(rs.getDouble("probability"));
        result.setRecordedAt(rs.getTimestamp("recorded_at") != null
                ? rs.getTimestamp("recorded_at").toLocalDateTime() : null);
        return result;
    };

    @Override
    public void save(DementiaResult result) {
        String sql = """
            INSERT INTO dementia_results
                (patient_id, session_id, prediction, probability, recorded_at)
            VALUES (?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, result.getPatientId());
            ps.setLong(2, result.getSessionId());
            ps.setString(3, result.getPrediction());
            ps.setDouble(4, result.getProbability());
            ps.setObject(5, result.getRecordedAt());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            result.setId(key.longValue());
        }
    }

    @Override
    public Optional<DementiaResult> findById(Long id) {
        String sql = "SELECT * FROM dementia_results WHERE id = ?";
        List<DementiaResult> results = jdbcTemplate.query(sql, DEMENTIA_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<DementiaResult> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM dementia_results WHERE patient_id = ? ORDER BY recorded_at DESC";
        return jdbcTemplate.query(sql, DEMENTIA_ROW_MAPPER, patientId);
    }

    @Override
    public List<DementiaResult> findBySessionId(Long sessionId) {
        String sql = "SELECT * FROM dementia_results WHERE session_id = ? ORDER BY recorded_at DESC";
        return jdbcTemplate.query(sql, DEMENTIA_ROW_MAPPER, sessionId);
    }
}