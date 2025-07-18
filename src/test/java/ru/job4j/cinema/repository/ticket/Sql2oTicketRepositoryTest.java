package ru.job4j.cinema.repository.ticket;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

class Sql2oTicketRepositoryTest {
    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    /**
     * Test case: Save a ticket to the database and verify that the same ticket can be retrieved.
     */
    @Test
    void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, 1, 2, 2, 1));
        var savedTicket = sql2oTicketRepository.findById(ticket.get().getId());
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    /**
     * Test case: Save three tickets to the database and verify that the same tickets can be retrieved.
     */
    @Test
    void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 2, 2, 1)).get();
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, 1, 3, 2, 1)).get();
        var ticket3 = sql2oTicketRepository.save(new Ticket(3, 1, 4, 2, 1)).get();
        var result = sql2oTicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    /**
     * Test case: Save 0 tickets to the database and verify that there is no tickets.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oTicketRepository.findById(0)).isEmpty();
    }

    /**
     * Test case: Save and delete a ticket to the database and verify that the database is empty
     */
    @Test
    void whenDeleteThenGetEmptyOptional() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, 1, 2, 2, 1));
        var isDeleted = sql2oTicketRepository.deleteById(ticket.get().getId());
        var savedTicket = sql2oTicketRepository.findById(ticket.get().getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedTicket).isEmpty();
    }

    /**
     * Test case: Delete a ticket from empty database and verify that it is impossible
     */
    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oTicketRepository.deleteById(0)).isFalse();
    }

    /**
     * Test case: Attempt to get a ticket by place number, row number and session id
     * and verify that expected ticket can be retrieved.
     */
    @Test
    void whenFindByPlaceNumberRowNumberAndSessionIdThenGetTicket() {
        var ticket = new Ticket(0, 1, 2, 2, 1);
        sql2oTicketRepository.save(ticket);
        var savedTicket = sql2oTicketRepository.findByPlaceNumberRowNumberAndSessionId(
                ticket.getPlaceNumber(),
                ticket.getRowNumber(),
                ticket.getSessionId());
        assertThat(savedTicket).contains(ticket);
    }
}