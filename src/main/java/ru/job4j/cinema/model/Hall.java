package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "row_count", "rowCount",
            "place_count", "placeCount",
            "description", "description",
            "map_link", "mapLink"
    );

    @EqualsAndHashCode.Exclude
    private int id;
    private String name;
    private int rowCount;
    private int placeCount;
    private String description;
    @EqualsAndHashCode.Exclude
    private String mapLink;
}
