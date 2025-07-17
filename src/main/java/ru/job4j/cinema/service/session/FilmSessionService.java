package ru.job4j.cinema.service.session;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.Collection;

public interface FilmSessionService {
    Collection<FilmSessionDto> findAll();

    FilmSessionDto findById(int id);
}
