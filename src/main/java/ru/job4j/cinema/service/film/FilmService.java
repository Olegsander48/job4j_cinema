package ru.job4j.cinema.service.film;

import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;

public interface FilmService {
    Collection<FilmDto> findAll();

    FilmDto findById(int id);
}
