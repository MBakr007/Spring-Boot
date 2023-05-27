package com.vodafone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.errorhandling.NotFoundException;
import com.vodafone.model.Article;
import com.vodafone.model.Author;
import com.vodafone.service.ArticleService;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;
    private String asJsonString(Article article) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(article);
    }

    @Test
    @DisplayName("getArticleByIdTest_sendGetRequest_returnArticle")
    public void getArticleByIdTest_sendGetRequest_returnArticle() throws Exception {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleService.getArticleById(1)).thenReturn(article);
        // act & assert
        mockMvc.perform(get("/articles/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(article.getId())))
                .andExpect(jsonPath("$.name", equalTo(article.getName())));
    }

    @Test
    @DisplayName("addArticleTest_sendPostRequest_returnArticle")
    public void addArticleTest_sendPostRequest_returnArticle() throws Exception {
        // arrange
        Article article = new Article(1, "clean code", new Author(1, "bob"));
        when(articleService.addArticle(article)).thenReturn(article);

        // act & assert
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(article)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.id").value(article.getId()));
//                .andExpect(jsonPath("$.name", equalTo(article.getName())))
//                .andExpect(jsonPath("$.author", equalTo(article.getAuthor())));
    }

    @Test
    @DisplayName("getAllArticlesTest_sendGetRequest_returnAllArticles")
    public void getAllArticlesTest_sendGetRequest_returnAllArticles() throws Exception {
        // arrange
        Article article1 = new Article(1, "clean code", new Author());
        Article article2 = new Article(2, "algorithms", new Author());
        List<Article> articles = Arrays.asList(article1, article2);
        when(articleService.getAllArticles()).thenReturn(articles);
        // act & assert
        mockMvc.perform(get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(articles.get(0).getId())))
                .andExpect(jsonPath("$[0].name", equalTo(articles.get(0).getName())))
                .andExpect(jsonPath("$[1].id", equalTo(articles.get(1).getId())))
                .andExpect(jsonPath("$[1].name", equalTo(articles.get(1).getName())));
    }

    @Test
    @DisplayName("deleteArticleTest_sendDeleteRequest_returnNoContentException")
    public void deleteArticleTest_sendDeleteRequest_returnNoContentException() throws Exception {
        // arrange
        int id = 1;
        when(articleService.deleteArticle(id)).thenReturn(true);
        // act & assert
        mockMvc.perform(delete("/articles/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("deleteArticleTest_sendDeleteRequest_returnNotFoundException")
    public void deleteArticleTest_sendDeleteRequest_returnNotFoundException() throws Exception {
        // arrange
        int id = 1;
        when(articleService.deleteArticle(id)).thenThrow(NotFoundException.class);
        // act & assert
        mockMvc.perform(delete("/articles/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateArticleTest_sendPutRequest_returnUpdatedArticle")
    public void updateArticleTest_sendPutRequest_returnUpdatedArticle() throws Exception {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleService.updateArticle(1, article)).thenReturn(article);
        // act & assert
        mockMvc.perform(put("/articles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(article)))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("updateArticleTest_sendPutRequest_returnNotFoundException")
//    public void updateArticleTest_sendPutRequest_returnNotFoundException() throws Exception {
//        // arrange
//        Article article = new Article(1, "clean code", new Author());
//        when(articleService.updateArticle(1, article)).thenThrow(NotFoundException.class);
//        // act & assert
//        mockMvc.perform(put("/articles/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(article)))
//                .andExpect(status().isNotFound());
//    }
}
