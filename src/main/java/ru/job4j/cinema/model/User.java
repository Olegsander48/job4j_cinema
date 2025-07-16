package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    private String fullName;
    private String email;
    private String password;
}
