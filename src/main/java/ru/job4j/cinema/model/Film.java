package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @EqualsAndHashCode.Exclude
    private int id;
    private String name;
    private String description;
    private int year;
    private int genreId;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;

}
