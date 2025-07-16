package ru.job4j.cinema.repository.ticket;

import ru.job4j.cinema.model.Ticket;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

    Optional<Ticket> findByPlaceNumberAndRowNumber(int placeNumber, int rowNumber);

    boolean deleteById(int id);
}
