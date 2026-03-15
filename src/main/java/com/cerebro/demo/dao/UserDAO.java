package com.cerebro.demo.dao;

import com.cerebro.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void update(User user);

    void delete(Long id);

    // Optional: role-based queries
    List<User> findByRole(String role);  // e.g. "DOCTOR" or "PATIENT"
}