package com.vodafone.controller;

import com.vodafone.model.Author;
import com.vodafone.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }
}
