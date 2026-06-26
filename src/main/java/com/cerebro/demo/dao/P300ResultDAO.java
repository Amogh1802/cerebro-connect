package com.cerebro.demo.dao;

import com.cerebro.demo.model.P300Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface P300ResultDAO extends JpaRepository<P300Result, Long> {
    List<P300Result> findByPatientId(Long patientId);
    List<P300Result> findBySessionId(Long sessionId);
}