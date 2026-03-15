package com.cerebro.demo.dao;

import com.cerebro.demo.model.EEGSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class EEGSessionDAOTest {

    @Autowired
    private EEGSessionDAO eegSessionDAO;

    @org.testng.annotations.Test
    void testSaveAndFindByPatientId() {
        // Create a test session
        EEGSession session = new EEGSession();
        session.setPatientId(1L);  // Use a test/fake patient ID (make sure patient exists or ignore FK for test)
        session.setMode("GENERAL");
        session.setStartTime(LocalDateTime.now());
        session.setRawData("{\"samples\":[1.2, 1.5, 1.3]}");
        session.setProcessedData("{\"alpha\":12.4, \"beta\":8.1}");

        // Save it
        eegSessionDAO.save(session);

        // Retrieve sessions for the same patient
        List<EEGSession> sessions = eegSessionDAO.findByPatientId(1L);

        // Basic assertions
        assertNotNull(sessions, "Sessions list should not be null");
        assertFalse(sessions.isEmpty(), "Should have at least one saved session");

        // Print for visual confirmation (remove in real tests)
        System.out.println("Saved session example:");
        System.out.println(sessions.get(0));
    }
}