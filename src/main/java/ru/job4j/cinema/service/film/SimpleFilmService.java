package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.Collection;
import java.util.List;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        var films = filmRepository.findAll();
        List<Genre> genres = (List<Genre>) genreRepository.findAll();
        return films.stream()
                .map(film -> new FilmDto(film, genres.get(film.getGenreId() - 1).getName()))
                .toList();
    }

    @Override
    public FilmDto findById(int id) {
        var film = filmRepository.findById(id);
        var genre = genreRepository.findById(film.getGenreId()).getName();
        return new FilmDto(film, genre);
    }
}
