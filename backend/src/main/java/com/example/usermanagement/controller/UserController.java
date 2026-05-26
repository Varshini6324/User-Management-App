package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user-api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create User
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new org.springframework.dao.DuplicateKeyException("Email already existed");
        }
        User savedUser = userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User Created");
        response.put("payload", savedUser);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read all Users
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> usersList = userRepository.findByStatusTrue();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users");
        response.put("payload", usersList);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Read a User by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String id) {
        Optional<User> userOpt = userRepository.findByIdAndStatusTrue(id);
        if (!userOpt.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "user not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User found");
        response.put("payload", userOpt.get());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a User by ID (soft delete: set status to false)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "user not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        User user = userOpt.get();
        user.setStatus(false);
        userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User removed");
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Activate User (change status to true)
    @PatchMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> activateUser(@PathVariable String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "user not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        User user = userOpt.get();
        user.setStatus(true);
        User updatedUser = userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User activated");
        response.put("payload", updatedUser);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
