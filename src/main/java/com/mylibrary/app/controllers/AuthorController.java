package com.mylibrary.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylibrary.app.entities.Author;
import com.mylibrary.app.exceptions.ResourceNotFoundException;
import com.mylibrary.app.payloads.ApiResponse;
import com.mylibrary.app.payloads.AuthorDTO;
import com.mylibrary.app.repositories.AuthorRepo;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    ModelMapper modelMapper;


    @GetMapping("")
    public ResponseEntity<Page<AuthorDTO>> index(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("name"));
        Page<Author> page = this.authorRepo.findAll(pageable);
        List<AuthorDTO> authorDTOs = page.getContent().stream().map((author) -> this.modelMapper.map(author, AuthorDTO.class)).collect(Collectors.toList());
        Page<AuthorDTO> authorDTOsPage = new PageImpl<>(authorDTOs, pageable, page.getTotalElements());

        
        return new ResponseEntity<Page<AuthorDTO>>(authorDTOsPage, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<AuthorDTO> store(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = this.modelMapper.map(authorDTO, Author.class);
        author = this.authorRepo.insert(author);

        authorDTO = this.modelMapper.map(author, AuthorDTO.class);
        
        return new ResponseEntity<AuthorDTO>(authorDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> show(@PathVariable String id) {
        Author author = this.authorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "AuthorId", id));
        return new ResponseEntity<AuthorDTO>(this.modelMapper.map(author, AuthorDTO.class), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable String id, @Valid @RequestBody AuthorDTO authorDTO) {
        Author author = this.authorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "AuthorId", id));
        author.setName(authorDTO.getName());
        author.setStatus(authorDTO.isStatus());
        Author updatedAuthor = this.authorRepo.save(author);
        AuthorDTO updateAuthorDTO = this.modelMapper.map(updatedAuthor, AuthorDTO.class);
        return new ResponseEntity<AuthorDTO>(updateAuthorDTO, HttpStatus.OK);        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id){
        Author author = this.authorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "AuthorId", id));
        this.authorRepo.delete(author);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Author deleted succesfully", true), HttpStatus.OK);
    }
    
}
