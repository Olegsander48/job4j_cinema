package ru.job4j.cinema.service.session;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.hall.HallRepository;
import ru.job4j.cinema.repository.session.FilmSessionRepository;

import java.util.Collection;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, HallRepository hallRepository, FilmRepository filmRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll()
                .stream()
                .map(filmSession -> new FilmSessionDto(
                        filmRepository.findById(filmSession.getFilmId()).getName(),
                        hallRepository.findById(filmSession.getHallId()).getName(),
                        filmSession))
                .toList();
    }

    @Override
    public FilmSessionDto findById(int id) {
        var filmSession = filmSessionRepository.findById(id);
        return new FilmSessionDto(
                filmRepository.findById(filmSession.getFilmId()).getName(),
                hallRepository.findById(filmSession.getHallId()).getName(),
                filmSession);
    }
}
