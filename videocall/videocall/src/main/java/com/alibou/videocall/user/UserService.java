package com.alibou.videocall.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserService {

    // Simulating a database with an in-memory map
    private static final ConcurrentMap<String, User> USERS_MAP = new ConcurrentHashMap<>();

    public void register(User user) {
        // In a real application, you should hash the password before storing it.
        user.setStatus("online");
        USERS_MAP.put(user.getEmail(), user);
    }

    public User login(User user) {
        Optional<User> storedUser = USERS_MAP.values().stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findAny();

        if (storedUser.isPresent()) {
            User existingUser = storedUser.get();
            // In a real application, you should compare hashed passwords.
            if (existingUser.getPassword().equals(user.getPassword())) {
                existingUser.setStatus("online");
                return existingUser;
            } else {
                throw new PasswordIncorrectException("Password is incorrect");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void logout(String email) {
        User user = USERS_MAP.get(email);
        if (user != null) {
            user.setStatus("offline");
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(USERS_MAP.values());
    }
}
