package ru.job4j.cinema.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        if (userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
