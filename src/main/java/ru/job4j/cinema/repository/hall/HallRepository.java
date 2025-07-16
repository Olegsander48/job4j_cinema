package ru.job4j.cinema.repository.hall;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

public interface HallRepository {
    Collection<Hall> findAll();

    Hall findById(int id);
}
