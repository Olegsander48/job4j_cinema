package ru.job4j.cinema.repository.user;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.SpringJdbcConfig;
import ru.job4j.cinema.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

class JdbcTemplateUserRepositoryTest {
    private static JdbcTemplateUserRepository jdbcTemplateUserRepository;

    @BeforeAll
    static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new SpringJdbcConfig();
        var datasource = configuration.postgresDataSource(url, username, password);
        var client = configuration.databaseClient(datasource);

        jdbcTemplateUserRepository = new JdbcTemplateUserRepository(client);
    }

    @AfterEach
    void clearUsers() {
        var users = jdbcTemplateUserRepository.findAll();
        for (var user : users) {
            jdbcTemplateUserRepository.deleteById(user.getId());
        }
    }

    /**
     * Test case: Save a user to the database and verify that the same user can be retrieved.
     */
    @Test
    void whenSaveThenGetSame() {
        var user = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        var savedUser = jdbcTemplateUserRepository.findById(user.get().getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    /**
     * Test case: Save three users to the database and verify that the same users can be retrieved.
     */
    @Test
    void whenSaveSeveralThenGetAll() {
        var user1 = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig1", "google1@gmail.com",  "1234")).get();
        var user2 = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig2", "google2@gmail.com", "1234")).get();
        var user3 = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig3", "google3@gmail.com", "1234")).get();
        var result = jdbcTemplateUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2, user3));
    }

    /**
     * Test case: Save 0 users to the database and verify that there is no users.
     */
    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(jdbcTemplateUserRepository.findAll()).isEqualTo(emptyList());
        assertThat(jdbcTemplateUserRepository.findById(0)).isEmpty();
    }

    /**
     * Test case: Save and delete a user to the database and verify that the database is empty
     */
    @Test
    void whenDeleteThenGetEmptyOptional() {
        var user = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        var isDeleted = jdbcTemplateUserRepository.deleteById(user.get().getId());
        var savedUser = jdbcTemplateUserRepository.findById(user.get().getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEmpty();
    }

    /**
     * Test case: Delete a user from empty database and verify that it is impossible
     */
    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(jdbcTemplateUserRepository.deleteById(0)).isFalse();
    }

    /**
     * Test case: Attempt to save a user with the same email, name, and password as an existing user,
     * and verify that an exception is thrown.
     */
    @Test
    void whenPutExistingUserThenGetException() {
        jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThatThrownBy(() -> jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234")))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    /**
     * Test case: Attempt to save a user with the same email as an existing user,
     * and verify that an exception is thrown.
     */
    @Test
    void whenPutExistingEmailThenGetException() {
        jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThatThrownBy(() -> jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234")))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Unique index or primary key violation");
    }

    /**
     * Test case: Try to find saved user by email and password and verify that found user the same as saved
     */
    @Test
    void whenTryToFindByEmailAndPasswordThenGetUser() {
        var user = jdbcTemplateUserRepository.save(new User(0, "Aleks Prig", "google@gmail.com", "1234"));
        assertThat(jdbcTemplateUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword())).isEqualTo(user);
    }

    /**
     * Test case: Try to find not saved user by email and password and verify that there is no such user
     */
    @Test
    void whenTryToFindNonExistingUserByEmailAndPasswordThenGetUser() {
        var user = new User(0, "Aleks Prig", "google@gmail.com", "1234");
        assertThat(jdbcTemplateUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).isEmpty();
    }

}