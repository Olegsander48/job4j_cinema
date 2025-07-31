package ru.job4j.cinema.repository.hall;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.util.Collection;
import java.util.List;

@Repository
@Primary
public class JdbcTemplateHallRepository implements HallRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateHallRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Hall> findAll() {
        var sql = """
                SELECT id,
                        name,
                        row_count AS rowCount,
                        place_count AS placeCount,
                        description,
                        map_link AS mapLink
                FROM halls;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Hall.class));

    }

    @Override
    public Hall findById(int id) {
        var sql = """
                SELECT id,
                        name,
                        row_count AS rowCount,
                        place_count AS placeCount,
                        description,
                        map_link AS mapLink
                FROM halls
                WHERE id = ?;
                """;
        List<Hall> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Hall.class),
                id
        );
        return results.stream().findFirst().get();
    }
}
