package com.vodafone.controller;

import com.vodafone.model.Author;
import com.vodafone.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthorService authorService;

    @Test
    @DisplayName("getAuthorById_sendGetRequest_returnAuthor")
    public void getAuthorById_sendGetRequest_returnAuthor() throws Exception{
        // arrange
        Author author = new Author(1, "Muhammad", new ArrayList<>());
        when(authorService.getAuthorById(1)).thenReturn(author);
        // act & assert
        mockMvc.perform(get("/author/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(author.getName())))
                .andExpect(jsonPath("$.id", equalTo(author.getId())));
    }
}
