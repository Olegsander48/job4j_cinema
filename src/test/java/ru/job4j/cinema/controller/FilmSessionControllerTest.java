package ru.job4j.cinema.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.session.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

class FilmSessionControllerTest {
    private FilmSessionController filmSessionController;
    private FilmSessionService filmSessionService;

    @BeforeEach
    void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    /**
     * Test case: Request all film sessions and verify correct view and list of session DTOs.
     */
    @Test
    void whenRequestAllFilmSessionsThenGetListOfFilmSessions() {
        Hall hall = new Hall(1, "Red Hall", 10, 20, "VIP hall with premium seats", "https://maps.com/red");
        FilmSession filmSession = new FilmSession(5, 105, 2, LocalDateTime.of(2023, 12, 17, 17, 30), LocalDateTime.of(2023, 12, 17, 20, 15), 700);
        var filmSessionDtoListList = List.of(new FilmSessionDto("film1", hall, filmSession),
                new FilmSessionDto("test2", hall, filmSession),
                new FilmSessionDto("test3", hall, filmSession),
                new FilmSessionDto("test4", hall, filmSession),
                new FilmSessionDto("test5", hall, filmSession));
        when(filmSessionService.findAll()).thenReturn(filmSessionDtoListList);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var expectedList = model.getAttribute("sessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(expectedList).usingRecursiveAssertion().isEqualTo(filmSessionDtoListList);
    }

}