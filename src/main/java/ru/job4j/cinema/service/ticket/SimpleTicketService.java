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
        var ticketOptional = ticketRepository.findByPlaceNumberRowNumberAndSessionId(
                ticket.getPlaceNumber(),
                ticket.getRowNumber(),
                ticket.getSessionId());
        if (ticketOptional.isPresent()) {
            throw new IllegalArgumentException("Ticket already exists");
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(int id) {
        var ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new NoSuchElementException("Ticket with id " + id + " already exists");
        }
        return ticketOptional.get();
    }
}
