package com.cerebro.demo.dao;

import com.cerebro.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setRole(rs.getString("role"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setProfileInfo(rs.getString("profile_info"));
        return user;
    };

    @Override
    public void save(User user) {
        String sql = """
            INSERT INTO users (role, email, password, name, profile_info)
            VALUES (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                user.getRole(),
                user.getEmail(),
                user.getPassword(),      // must be hashed before calling this method
                user.getName(),
                user.getProfileInfo()
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> results = jdbcTemplate.query(sql, USER_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, USER_ROW_MAPPER, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    @Override
    public void update(User user) {
        String sql = """
            UPDATE users
            SET role = ?, email = ?, password = ?, name = ?, profile_info = ?
            WHERE id = ?
        """;
        jdbcTemplate.update(sql,
                user.getRole(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getProfileInfo(),
                user.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> findByRole(String role) {
        String sql = "SELECT * FROM users WHERE role = ?";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, role);
    }
}