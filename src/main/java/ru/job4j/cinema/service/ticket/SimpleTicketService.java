package ru.job4j.cinema.service.ticket;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.ticket.TicketRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        var ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new NoSuchElementException("Ticket with id " + id + " not found");
        }
        return ticketOptional.get();
    }
}
