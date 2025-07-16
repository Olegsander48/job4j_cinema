package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    @EqualsAndHashCode.Exclude
    private int id;
    private String name;
    private int rowCount;
    private int placeCount;
    private String description;
}
