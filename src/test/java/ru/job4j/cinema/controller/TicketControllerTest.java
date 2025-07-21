package ru.job4j.cinema.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.session.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.util.Optional;

class TicketControllerTest {
    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private TicketController ticketController;
    private MockHttpSession mockHttpSession;

    @BeforeEach
    void initService() {
        ticketService = Mockito.mock(TicketService.class);
        filmSessionService = Mockito.mock(FilmSessionService.class);
        ticketController = new TicketController(ticketService, filmSessionService);
        mockHttpSession = new MockHttpSession();

        User user = new User(1, "Test", "test@gmail.com", "qwery123");
        mockHttpSession.setAttribute("user", user);
    }

    /**
     * Test case: Request buy ticket page by valid session ID and verify page and model attributes.
     */
    @Test
    void whenRequestBuyPageById1ThenGetBuyTicketPage() {
        FilmSessionDto filmSessionDto = mock(FilmSessionDto.class);
        var optionalFilmSessionDto = Optional.of(filmSessionDto);
        var ticket = new Ticket(1, 1);
        when(filmSessionService.findById(1)).thenReturn(optionalFilmSessionDto);

        var model = new ConcurrentModel();
        var view = ticketController.getBuyPage(model, 1, mockHttpSession);
        var actualFilmSessionDto = model.getAttribute("filmSessionDto");
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualFilmSessionDto).isEqualTo(filmSessionDto);
        assertThat(actualTicket).isEqualTo(ticket);
    }

    /**
     * Test case: Request buy ticket page with non-existing session ID and verify error page is shown.
     */
    @Test
    void whenRequestBuyPageByNonExistingIdThenGetErrorPage() {
        when(filmSessionService.findById(100)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.getBuyPage(model, 1, mockHttpSession);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("fragments/errors/404");
        assertThat(actualMessage).isEqualTo("Такой страницы не существует");
    }

    /**
     * Test case: Buy a ticket for free seat and verify confirmation page and correct ticket data.
     */
    @Test
    void whenBuyTicketWithFreeSeatsThenGetConfirmationPage() {
        var ticket = new Ticket(1, 1, 5, 5, 1);
        var optionalTicket = Optional.of(ticket);
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(optionalTicket);

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket, model);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("fragments/confirmation");
        assertThat(actualMessage).isEqualTo("ряд: 5, место: 5");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    /**
     * Test case: Buy a ticket for already taken seat and verify seat unavailable error page.
     */
    @Test
    void whenTicketAlreadyBoughtThenGetErrorPage() {
        var ticketExistException = new IllegalArgumentException("Ticket already exists");
        when(ticketService.save(any())).thenThrow(ticketExistException);

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(new Ticket(), model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("fragments/errors/seat-unavailable");
        assertThat(actualMessage).isEqualTo("Билет на данный ряд и место уже куплены. Поробуйте выбрать другое место");
    }
}