package ru.job4j.cinema.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.job4j.cinema.model.FilmSession;

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

    public FilmSessionDto(String filmName, String hallName, FilmSession filmSession) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
        this.id = filmSession.getId();
    }

    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yy"));
    }

    public String getFormattedEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yy"));
    }
}
