package com.mylibrary.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylibrary.app.entities.Author;
import com.mylibrary.app.entities.Book;
import com.mylibrary.app.exceptions.ResourceNotFoundException;
import com.mylibrary.app.payloads.ApiResponse;
import com.mylibrary.app.payloads.AuthorDTO;
import com.mylibrary.app.payloads.BookDTO;
import com.mylibrary.app.repositories.AuthorRepo;
import com.mylibrary.app.repositories.BookRepo;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/v1/books")
public class BookController {
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    ModelMapper modelMapper;


    @GetMapping("")
    public ResponseEntity<List<BookDTO>> index() {
        List<Book> books = this.bookRepo.findAll();
        List<BookDTO> booksDTO = books.stream().map((book) -> {
            BookDTO bookDTO = this.modelMapper.map(book, BookDTO.class);
            List<AuthorDTO> authorDTOs = book.getAuthors().stream().map((author) -> this.modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
            bookDTO.setAuthorsDTO(authorDTOs);
            return bookDTO;
        }).collect(Collectors.toList());
        return new ResponseEntity<List<BookDTO>>(booksDTO, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<BookDTO> store(@RequestBody @Valid BookDTO bookDTO) {
        Book book = this.modelMapper.map(bookDTO, Book.class);
        List<AuthorDTO> authorsDTO = bookDTO.getAuthorsDTO();
        List<String> authorsIds = authorsDTO.stream().map((authorDTO) -> authorDTO.getId()).collect(Collectors.toList());
        List<Author> listOfAurthors = this.authorRepo.findAllById(authorsIds);
        book.setAuthors(listOfAurthors);
        book = this.bookRepo.insert(book);

        bookDTO = this.modelMapper.map(book, BookDTO.class);
        List<AuthorDTO> authorDTOs = book.getAuthors().stream().map((author) -> this.modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        bookDTO.setAuthorsDTO(authorDTOs);
        
        return new ResponseEntity<BookDTO>(bookDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> show(@PathVariable String id) {
        Book book = this.bookRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book", "BookId", id));
        List<AuthorDTO> authorDTOs = book.getAuthors().stream().map((author) -> this.modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        BookDTO bookDTO = this.modelMapper.map(book, BookDTO.class);
        bookDTO.setAuthorsDTO(authorDTOs);
        return new ResponseEntity<BookDTO>(bookDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable String id, @Valid @RequestBody BookDTO bookDTO) {
        Book book = this.bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "BookId", id));
        book.setTitle(bookDTO.getTitle());
        book.setSubtitle(bookDTO.getSubtitle());
        book.setStatus(bookDTO.isStatus());
        List<AuthorDTO> authorsDTO = bookDTO.getAuthorsDTO();
        List<String> authorsIds = authorsDTO.stream().map((authorDTO) -> authorDTO.getId()).collect(Collectors.toList());
        List<Author> listOfAurthors = this.authorRepo.findAllById(authorsIds);
        book.setAuthors(listOfAurthors);

        Book updatedBook = this.bookRepo.save(book);
        List<AuthorDTO> authorDTOs = updatedBook.getAuthors().stream().map((author) -> this.modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        BookDTO updateBookDTO = this.modelMapper.map(updatedBook, BookDTO.class);
        updateBookDTO.setAuthorsDTO(authorDTOs);
        return new ResponseEntity<BookDTO>(updateBookDTO, HttpStatus.OK);        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id){
        Book book = this.bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "BookId", id));
        this.bookRepo.delete(book);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Book deleted succesfully", true), HttpStatus.OK);
    }
    
}
