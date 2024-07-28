package com.mylibrary.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mylibrary.app.entities.Author;

public interface AuthorRepo extends MongoRepository<Author, String> {

}
