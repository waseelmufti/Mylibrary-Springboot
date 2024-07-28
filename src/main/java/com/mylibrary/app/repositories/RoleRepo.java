package com.mylibrary.app.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mylibrary.app.entities.Role;

@Repository
public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);

}
