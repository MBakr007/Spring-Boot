package com.vodafone.service;

import com.vodafone.errorhandling.NotFoundException;
import com.vodafone.model.Author;
import com.vodafone.repository.AuthorRepo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    AuthorRepo authorRepo;

    @Test
    @DisplayName("getAuthorByIdTest_sendAuthorId_returnInstanceOfAuthor")
    public void getAuthorByIdTest_sendAuthorId_returnInstanceOfAuthor() {
        // arrange
        AuthorServiceImpl authorService = new AuthorServiceImpl();
        authorService.authorRepo = authorRepo;
        Author author = new Author(1, "Muhammad", new ArrayList<>());
        when(authorRepo.findById(1)).thenReturn(Optional.of(author));
        // act
        Author returnedAuthor = authorService.getAuthorById(1);
        // assert
        assertEquals(author, returnedAuthor);
    }

    @Test
    @DisplayName("getAuthorByIdTest_sendAuthorId_returnInstanceOfAuthor")
    public void getAuthorByIdTest_sendAuthorId_returnNotFoundException() {
        // arrange
        AuthorServiceImpl authorService = new AuthorServiceImpl();
        authorService.authorRepo = authorRepo;
        when(authorRepo.findById(1)).thenThrow(NotFoundException.class);
        // act & assert
        assertThrows(NotFoundException.class, ()-> authorService.getAuthorById(1));
    }


}
