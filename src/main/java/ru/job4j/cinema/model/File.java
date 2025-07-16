package ru.job4j.cinema.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @EqualsAndHashCode.Exclude
    private int id;
    private String name;
    private String path;
}
