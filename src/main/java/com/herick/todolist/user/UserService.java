package com.herick.todolist.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public ResponseEntity<User> createUser(User user) {
        var result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    public ResponseEntity<User> getUserById(UUID userId) {
        var optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + userId + " not found.");
        }
        return ResponseEntity.ok(optionalUser.get());
    }
}
