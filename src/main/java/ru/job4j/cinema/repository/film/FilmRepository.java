package ru.job4j.cinema.repository.film;

import ru.job4j.cinema.model.Film;
import java.util.Collection;

public interface FilmRepository {
    Collection<Film> findAll();

    Film findById(int id);
}
