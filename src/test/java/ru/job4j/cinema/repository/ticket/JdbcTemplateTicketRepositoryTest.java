package ru.job4j.cinema.repository.ticket;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.SpringJdbcConfig;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

class JdbcTemplateTicketRepositoryTest {
    private static JdbcTemplateTicketRepository jdbcTemplateTicketRepository;

    @BeforeAll
    static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new SpringJdbcConfig();
        var datasource = configuration.postgresDataSource(url, username, password);
        var jdbcTemplate = configuration.databaseClient(datasource);

        jdbcTemplateTicketRepository = new JdbcTemplateTicketRepository(jdbcTemplate);
    }

    @AfterEach
    void clearTickets() {
        var tickets = jdbcTemplateTicketRepository.findAll();
        for (var ticket : tickets) {
            jdbcTemplateTicketRepository.deleteById(ticket.getId());
        }
    }

    /**
     * Test case: Save a ticket to the database and verify that the same ticket can be retrieved.
     */
    @Test
    void whenSaveThenGetSame() {
        var ticket = jdbcTemplateTicketRepository.save(new Ticket(0, 1, 2, 2, 1));
        var savedTicket = jdbcTemplateTicketRepository.findById(ticket.get().getId());
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    /**
     * Test case: Save three tickets to the database and verify that the same tickets can be retrieved.
     */
    @Test
    void whenSaveSeveralThenGetAll() {
        var ticket1 = jdbcTemplateTicketRepository.save(new Ticket(1, 1, 2, 2, 1)).get();
        var ticket2 = jdbcTemplateTicketRepository.save(new Ticket(2, 1, 3, 2, 1)).get();
        var ticket3 = jdbcTemplateTicketRepository.save(new Ticket(3, 1, 4, 2, 1)).get();
        var result = jdbcTemplateTicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    /**
     * Test case: Save two same tickets to the database and verify that we get constraint exception.
     */
    @Test
    void whenSaveSameTicketThenGetIndexViolationException() {
        jdbcTemplateTicketRepository.save(new Ticket(1, 1, 2, 2, 1));
        assertThatThrownBy(() -> jdbcTemplateTicketRepository.save(new Ticket(2, 1, 2, 2, 1)).get())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    /**
     * Test case: Save 0 tickets to the database and verify that there is no tickets.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(jdbcTemplateTicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(jdbcTemplateTicketRepository.findById(0)).isEmpty();
    }

    /**
     * Test case: Save and delete a ticket to the database and verify that the database is empty
     */
    @Test
    void whenDeleteThenGetEmptyOptional() {
        var ticket = jdbcTemplateTicketRepository.save(new Ticket(0, 1, 2, 2, 1));
        var isDeleted = jdbcTemplateTicketRepository.deleteById(ticket.get().getId());
        var savedTicket = jdbcTemplateTicketRepository.findById(ticket.get().getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedTicket).isEmpty();
    }

    /**
     * Test case: Delete a ticket from empty database and verify that it is impossible
     */
    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(jdbcTemplateTicketRepository.deleteById(0)).isFalse();
    }

    /**
     * Test case: Attempt to get a ticket by place number, row number and session id
     * and verify that expected ticket can be retrieved.
     */
    @Test
    void whenFindByPlaceNumberRowNumberAndSessionIdThenGetTicket() {
        var ticket = new Ticket(0, 1, 2, 2, 1);
        jdbcTemplateTicketRepository.save(ticket);
        var savedTicket = jdbcTemplateTicketRepository.findByPlaceNumberRowNumberAndSessionId(
                ticket.getPlaceNumber(),
                ticket.getRowNumber(),
                ticket.getSessionId());
        assertThat(savedTicket).contains(ticket);
    }

}