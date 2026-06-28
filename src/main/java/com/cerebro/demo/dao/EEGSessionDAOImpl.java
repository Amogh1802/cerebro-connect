package com.cerebro.demo.dao;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import com.cerebro.demo.model.EEGSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EEGSessionDAOImpl implements EEGSessionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<EEGSession> SESSION_ROW_MAPPER = (rs, rowNum) -> {
        EEGSession session = new EEGSession();
        session.setId(rs.getLong("id"));
        session.setPatientId(rs.getLong("patient_id"));
        session.setMode(rs.getString("mode"));
        session.setNotes(rs.getString("notes"));  // ADDED
        session.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        session.setEndTime(rs.getTimestamp("end_time") != null
                ? rs.getTimestamp("end_time").toLocalDateTime() : null);
        session.setRawData(rs.getString("raw_data"));
        session.setProcessedData(rs.getString("processed_data"));
        return session;
    };

    @Override
    public void save(EEGSession session) {

        String sql = """
        INSERT INTO eeg_sessions
        (patient_id, mode, start_time, notes, raw_data, processed_data)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, session.getPatientId());
            ps.setString(2, session.getMode());
            ps.setObject(3, session.getStartTime());
            ps.setString(4, session.getNotes());
            ps.setString(5, session.getRawData());
            ps.setString(6, session.getProcessedData());

            return ps;

        }, keyHolder);

        if (keyHolder.getKey() != null) {
            session.setId(keyHolder.getKey().longValue());
        }

        System.out.println(
                "Generated session id = "
                        + session.getId());
    }

    @Override
    public Optional<EEGSession> findById(Long id) {
        String sql = "SELECT * FROM eeg_sessions WHERE id = ?";
        List<EEGSession> results = jdbcTemplate.query(sql, SESSION_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<EEGSession> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM eeg_sessions WHERE patient_id = ? ORDER BY start_time DESC";
        return jdbcTemplate.query(sql, SESSION_ROW_MAPPER, patientId);
    }

    @Override
    public List<EEGSession> findByPatientAndMode(Long patientId, String mode) {
        String sql = "SELECT * FROM eeg_sessions WHERE patient_id = ? AND mode = ? ORDER BY start_time DESC";
        return jdbcTemplate.query(sql, SESSION_ROW_MAPPER, patientId, mode);
    }

    @Override
    public List<EEGSession> findRecentByPatient(Long patientId, int limit) {
        String sql = "SELECT * FROM eeg_sessions WHERE patient_id = ? ORDER BY start_time DESC LIMIT ?";
        return jdbcTemplate.query(sql, SESSION_ROW_MAPPER, patientId, limit);
    }

    @Override
    public void updateEndTime(Long sessionId, LocalDateTime endTime) {
        String sql = "UPDATE eeg_sessions SET end_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, endTime, sessionId);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM eeg_sessions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public void updateNotes(
            Long sessionId,
            String notes) {

        String sql =
                "UPDATE eeg_sessions " +
                        "SET notes=? " +
                        "WHERE id=?";

        jdbcTemplate.update(
                sql,
                notes,
                sessionId
        );
    }
}