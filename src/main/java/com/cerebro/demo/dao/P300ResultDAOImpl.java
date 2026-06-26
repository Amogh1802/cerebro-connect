package com.cerebro.demo.dao;

import com.cerebro.demo.model.P300Result;
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
public class P300ResultDAOImpl implements P300ResultDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<P300Result> P300_ROW_MAPPER = (rs, rowNum) -> {
        P300Result result = new P300Result();
        result.setId(rs.getLong("id"));
        result.setPatientId(rs.getLong("patient_id"));
        result.setSessionId(rs.getLong("session_id"));
        result.setMode(rs.getString("mode"));
        result.setTrialsAveraged(rs.getInt("trials_averaged"));
        result.setAmplitude(rs.getDouble("amplitude"));
        result.setLatencyMs(rs.getInt("latency_ms"));
        result.setDetected(rs.getBoolean("detected"));
        result.setRecordedAt(rs.getTimestamp("recorded_at") != null
                ? rs.getTimestamp("recorded_at").toLocalDateTime() : null);
        return result;
    };

    @Override
    public void save(P300Result result) {
        String sql = """
            INSERT INTO p300_results
                (patient_id, session_id, mode, trials_averaged, amplitude, latency_ms, detected, recorded_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, result.getPatientId());
            ps.setLong(2, result.getSessionId());
            ps.setString(3, result.getMode());
            ps.setInt(4, result.getTrialsAveraged());
            ps.setDouble(5, result.getAmplitude());
            ps.setInt(6, result.getLatencyMs());
            ps.setBoolean(7, result.getDetected());
            ps.setObject(8, result.getRecordedAt());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            result.setId(key.longValue());
        }
    }

    @Override
    public Optional<P300Result> findById(Long id) {
        String sql = "SELECT * FROM p300_results WHERE id = ?";
        List<P300Result> results = jdbcTemplate.query(sql, P300_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<P300Result> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM p300_results WHERE patient_id = ? ORDER BY recorded_at DESC";
        return jdbcTemplate.query(sql, P300_ROW_MAPPER, patientId);
    }

    @Override
    public List<P300Result> findBySessionId(Long sessionId) {
        String sql = "SELECT * FROM p300_results WHERE session_id = ? ORDER BY recorded_at DESC";
        return jdbcTemplate.query(sql, P300_ROW_MAPPER, sessionId);
    }
}