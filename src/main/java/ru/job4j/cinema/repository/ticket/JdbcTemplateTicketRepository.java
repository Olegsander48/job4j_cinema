package ru.job4j.cinema.repository.ticket;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcTemplateTicketRepository implements TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        var sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRowNumber());
            ps.setInt(3, ticket.getPlaceNumber());
            ps.setInt(4, ticket.getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected == 0 || keyHolder.getKey() == null) {
            return Optional.empty();
        }
        ticket.setId(keyHolder.getKey().intValue());
        return Optional.of(ticket);
    }

    @Override
    public Optional<Ticket> findById(int id) {
        var sql = """
                SELECT id,
                        session_id AS sessionId,
                        row_number AS rowNumber,
                        place_number AS placeNumber,
                        user_id AS userId
                FROM tickets
                WHERE id = ?;
                """;
        List<Ticket> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Ticket.class),
                id
        );
        return results.stream().findFirst();
    }

    @Override
    public Optional<Ticket> findByPlaceNumberRowNumberAndSessionId(int placeNumber, int rowNumber, int sessionId) {
        var sql = """
                SELECT id,
                        session_id AS sessionId,
                        row_number AS rowNumber,
                        place_number AS placeNumber,
                        user_id AS userId
                FROM tickets
                WHERE place_number = ? AND session_id = ? AND row_number = ?;
                """;
        List<Ticket> results = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Ticket.class),
                placeNumber, sessionId, rowNumber
        );
        return results.stream().findFirst();
    }

    @Override
    public boolean deleteById(int id) {
        var sql = """
                    DELETE FROM tickets
                    WHERE id = ?
                """;
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        }) > 0;
    }

    @Override
    public Collection<Ticket> findAll() {
        var sql = """
                SELECT id,
                        session_id AS sessionId,
                        row_number AS rowNumber,
                        place_number AS placeNumber,
                        user_id AS userId
                FROM tickets;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class));
    }
}
