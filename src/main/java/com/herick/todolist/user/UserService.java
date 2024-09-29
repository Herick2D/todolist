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

    public ResponseEntity<UserDTO> createUser(User user) {
        User savedUser = userRepository.save(user);
        UserDTO dto = new UserDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    public ResponseEntity<UserDTO> getUserById(UUID userId) {
        var optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + userId + " not found.");
        } else {
            User user = optionalUser.get();
            UserDTO dto = new UserDTO(user.getId(), user.getEmail(), user.getUsername());
            return ResponseEntity.ok(dto);
        }
    }

}
