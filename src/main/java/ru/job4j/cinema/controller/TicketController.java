package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.session.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/buy/{id}")
    public String getBuyPage(Model model, @PathVariable int id, HttpSession session) {
        var filmSessionDto = filmSessionService.findById(id);
        if (filmSessionDto.isEmpty()) {
            model.addAttribute("message", "Такой страницы не существует");
            return "fragments/errors/404";
        }
        model.addAttribute("filmSessionDto", filmSessionDto.get());
        model.addAttribute("ticket", new Ticket(id,
                ((User) session.getAttribute("user")).getId()));
        return "tickets/buy";
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model) {
        try {
            ticketService.save(ticket);
            model.addAttribute("ticket", ticket);
            return "fragments/confirmation";
        } catch (IllegalArgumentException exception) {
            model.addAttribute("message", "Билет на данный ряд и место уже куплены. Поробуйте выбрать другое место");
            return "fragments/errors/seat-unavailable";
        }
    }
}
