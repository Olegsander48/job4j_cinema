package ru.job4j.cinema.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @EqualsAndHashCode.Exclude
    private int id;
    private int sessionId;
    private int rowNumber;
    private int placeNumber;
    private int userId;
}
