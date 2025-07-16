package ru.job4j.cinema.repository.session;

import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;

public interface FilmSessionRepository {
    Collection<FilmSession> findAll();

    FilmSession findById(int id);
}
