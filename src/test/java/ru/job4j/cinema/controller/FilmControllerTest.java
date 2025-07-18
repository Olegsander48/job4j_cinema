package ru.job4j.cinema.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.film.FilmService;
import java.util.List;

class FilmControllerTest {
    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    /**
     * Test case: Request all films and verify correct view and list of film DTOs.
     */
    @Test
    void whenRequestAllFilmsThenGetListOfFilms() {
        Film film1 = new Film(1, "The Shawshank Redemption", "Two imprisoned men bond", 1994, 1, 16, 142, 201);
        Film film2 = new Film(2, "The Godfather", "Mafia dynasty story", 1972, 1, 18, 175, 202);
        Film film3 = new Film(3, "Pulp Fiction", "Interconnected crime stories", 1994, 2, 18, 154, 203);
        Film film4 = new Film(4, "The Dark Knight", "Batman vs Joker", 2008, 3, 16, 152, 204);
        Film film5 = new Film(5, "Fight Club", "Underground fight club", 1999, 4, 18, 139, 205);
        var filmsList = List.of(new FilmDto(film1, "test1"),
                                new FilmDto(film2, "test2"),
                                new FilmDto(film3, "test3"),
                                new FilmDto(film4, "test4"),
                                new FilmDto(film5, "test5"));
        when(filmService.findAll()).thenReturn(filmsList);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var expectedList = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(expectedList).usingRecursiveAssertion().isEqualTo(filmsList);
    }
}