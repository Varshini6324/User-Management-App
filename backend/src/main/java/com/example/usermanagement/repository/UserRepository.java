package com.example.usermanagement.repository;

import com.example.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    // Read all Users where status is true (active)
    List<User> findByStatusTrue();
    
    // Read user by ID where status is true
    Optional<User> findByIdAndStatusTrue(String id);
    
    // Check if email already exists
    boolean existsByEmail(String email);
}
