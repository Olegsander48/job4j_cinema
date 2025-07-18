package ru.job4j.cinema.repository.user;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    /**
     * Test case: Save a user to the database and verify that the same user can be retrieved.
     */
    @Test
    void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        var savedUser = sql2oUserRepository.findById(user.get().getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    /**
     * Test case: Save three users to the database and verify that the same users can be retrieved.
     */
    @Test
    void whenSaveSeveralThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(0, "Aleks Prig1", "google1@gmail.com",  "1234")).get();
        var user2 = sql2oUserRepository.save(new User(0, "Aleks Prig2", "google2@gmail.com", "1234")).get();
        var user3 = sql2oUserRepository.save(new User(0, "Aleks Prig3", "google3@gmail.com", "1234")).get();
        var result = sql2oUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2, user3));
    }

    /**
     * Test case: Save 0 users to the database and verify that there is no users.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oUserRepository.findById(0)).isEmpty();
    }

    /**
     * Test case: Save and delete a user to the database and verify that the database is empty
     */
    @Test
    void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        var isDeleted = sql2oUserRepository.deleteById(user.get().getId());
        var savedUser = sql2oUserRepository.findById(user.get().getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEmpty();
    }

    /**
     * Test case: Delete a user from empty database and verify that it is impossible
     */
    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }

    /**
     * Test case: Attempt to save a user with the same email, name, and password as an existing user,
     * and verify that an exception is thrown.
     */
    @Test
    void whenPutExistingUserThenGetException() {
        sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThatThrownBy(() -> sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234")))
                .isInstanceOf(Sql2oException.class);
    }

    /**
     * Test case: Attempt to save a user with the same email as an existing user,
     * and verify that an exception is thrown.
     */
    @Test
    void whenPutExistingEmailThenGetException() {
        sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThatThrownBy(() -> sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234")))
                .isInstanceOf(Sql2oException.class);
    }

    /**
     * Test case: Try to find saved user by email and password and verify that found user the same as saved
     */
    @Test
    void whenTryToFindByEmailAndPasswordThenGetUser() {
        var user = sql2oUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThat(sql2oUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword())).isEqualTo(user);
    }

    /**
     * Test case: Try to find not saved user by email and password and verify that there is no such user
     */
    @Test
    void whenTryToFindNonExistingUserByEmailAndPasswordThenGetUser() {
        var user = new User(0, "Aleks Prig", "google@gmail.com", "1234");
        assertThat(sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).isEmpty();
    }
}