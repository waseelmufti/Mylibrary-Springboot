package com.mylibrary.app.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mylibrary.app.entities.User;


@Repository
public interface UserRepo extends MongoRepository<User, String>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
}
