package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmSession {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;
    private int filmId;
    private int hallId;
    @EqualsAndHashCode.Exclude
    private LocalDateTime startTime;
    @EqualsAndHashCode.Exclude
    private LocalDateTime endTime;
    private int price;

}
