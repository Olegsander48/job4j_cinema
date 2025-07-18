package ru.job4j.cinema.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null");
        }
        var userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User with email: " + email + " and password: " + password + " not found");
        }
        return userOptional;
    }
}
