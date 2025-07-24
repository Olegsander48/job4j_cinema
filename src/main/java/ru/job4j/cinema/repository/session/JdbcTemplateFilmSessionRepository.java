package ru.job4j.cinema.repository.session;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateFilmSessionRepository implements FilmSessionRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateFilmSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<FilmSession> findAll() {
        var sql = """
                SELECT id,
                        film_id AS filmId,
                        halls_id AS hallId,
                        start_time AS startTime,
                        end_time AS endTime,
                        price
                FROM film_sessions;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FilmSession.class));
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        var sql = """
                SELECT id,
                        film_id AS filmId,
                        halls_id AS hallId,
                        start_time AS startTime,
                        end_time AS endTime,
                        price
                FROM film_sessions
                WHERE id = ?;
                """;
        List<FilmSession> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(FilmSession.class),
                id
        );
        return results.stream().findFirst();
    }
}
