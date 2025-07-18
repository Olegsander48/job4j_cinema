package ru.job4j.cinema.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class FilmSessionDto {
    private int id;
    private String filmName;
    private String hallName;
    @EqualsAndHashCode.Exclude
    private LocalDateTime startTime;
    @EqualsAndHashCode.Exclude
    private LocalDateTime endTime;
    private int price;
    @EqualsAndHashCode.Exclude
    private String mapLink;
    private int rowCount;
    private int placeCount;

    public FilmSessionDto(String filmName, Hall hall, FilmSession filmSession) {
        this.filmName = filmName;
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
        this.id = filmSession.getId();

        this.hallName = hall.getName();
        this.mapLink = hall.getMapLink();
        this.rowCount = hall.getRowCount();
        this.placeCount = hall.getPlaceCount();
    }

    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yy"));
    }

    public String getFormattedEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yy"));
    }
}
