package com.cerebro.demo.dao;

import com.cerebro.demo.model.EEGSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EEGSessionDAO {

    void save(EEGSession session);

    Optional<EEGSession> findById(Long id);

    List<EEGSession> findByPatientId(Long patientId);

    List<EEGSession> findByPatientAndMode(Long patientId, String mode);

    List<EEGSession> findRecentByPatient(Long patientId, int limit);


    void updateEndTime(Long sessionId, LocalDateTime endTime);

    void delete(Long id);
}