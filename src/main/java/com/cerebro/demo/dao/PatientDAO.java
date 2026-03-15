package com.cerebro.demo.dao;

import com.cerebro.demo.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientDAO {

    void save(Patient patient);

    Optional<Patient> findById(Long id);

    List<Patient> findAll();

    void update(Patient patient);

    void delete(Long id);

    // Important relations
    List<Patient> findByDoctorId(Long doctorId);

    void assignDoctor(Long patientId, Long doctorId);
}