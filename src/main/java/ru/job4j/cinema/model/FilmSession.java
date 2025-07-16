package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSession {
    private int id;
    private int filmId;
    private int hallId;
    @EqualsAndHashCode.Exclude
    private LocalDateTime startTime;
    @EqualsAndHashCode.Exclude
    private LocalDateTime endTime;
    private int price;

}
