package com.cerebro.demo.dao;

import com.cerebro.demo.model.DementiaResult;
import java.util.List;
import java.util.Optional;

public interface DementiaResultDAO {

    void save(DementiaResult result);

    Optional<DementiaResult> findById(Long id);

    List<DementiaResult> findByPatientId(Long patientId);

    List<DementiaResult> findBySessionId(Long sessionId);
}