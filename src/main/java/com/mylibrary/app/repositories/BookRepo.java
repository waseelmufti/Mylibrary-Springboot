package com.mylibrary.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mylibrary.app.entities.Book;

public interface BookRepo extends MongoRepository<Book, String> {

}
