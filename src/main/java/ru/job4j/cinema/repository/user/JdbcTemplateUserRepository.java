package ru.job4j.cinema.repository.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> save(User user) {
        var sql = """
                    INSERT INTO users(full_name, email, password)
                    VALUES (?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected == 0 || keyHolder.getKey() == null) {
            return Optional.empty();
        }
        user.setId(keyHolder.getKey().intValue());
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        var sql = """
                SELECT id,
                        full_name AS fullName,
                        email,
                        password
                FROM users
                WHERE email = ? AND password = ?;
                """;
        List<User> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                email, password
        );
        return results.stream().findFirst();
    }

    @Override
    public Collection<User> findAll() {
        var sql = """
                SELECT id,
                        full_name AS fullName,
                        email,
                        password
                FROM users;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public boolean deleteById(int id) {
        var sql = """
                    DELETE FROM users
                    WHERE id = ?
                """;
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        }) > 0;
    }

    @Override
    public Optional<User> findById(int id) {
        var sql = """
                SELECT id,
                        full_name AS fullName,
                        email,
                        password
                FROM users
                WHERE id = ?;
                """;
        List<User> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                id
        );
        return results.stream().findFirst();
    }
}
