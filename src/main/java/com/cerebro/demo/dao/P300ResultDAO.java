package com.cerebro.demo.dao;

import com.cerebro.demo.model.P300Result;
import java.util.List;
import java.util.Optional;

public interface P300ResultDAO {

    void save(P300Result result);

    Optional<P300Result> findById(Long id);

    List<P300Result> findByPatientId(Long patientId);

    List<P300Result> findBySessionId(Long sessionId);
}