package com.vodafone.service;

import com.vodafone.errorhandling.DuplicateEntityException;
import com.vodafone.errorhandling.NotFoundException;
import com.vodafone.model.Article;
import com.vodafone.model.Author;
import com.vodafone.repository.ArticleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    ArticleRepo articleRepo;
    ArticleServiceImpl articleService;


    @BeforeEach
    public void setup() {
        articleService = new ArticleServiceImpl();
        articleService.articleRepo = articleRepo;
    }

    @Test
    @DisplayName("getArticleByIdTest_sendArticleId_returnInstanceOfArticle")
    public void getArticleByIdTest_sendArticleId_returnInstanceOfArticle() {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleRepo.findById(1)).thenReturn(Optional.of(article));
        // act
        Article returnedArticle = articleService.getArticleById(1);
        // assert
        assertNotNull(returnedArticle);
    }

    @Test
    @DisplayName("getArticleByIdTest_sendArticleIdNotExisting_returnNotFoundException")
    public void getArticleByIdTest_sendArticleIdNotExisting_returnNotFoundException() {
        // arrange
        when(articleRepo.findById(3)).thenThrow(NotFoundException.class);
        // act & assert
        assertThrows(NotFoundException.class, ()-> articleService.getArticleById(3));
    }

    @Test
    @DisplayName("getAllArticlesTest_returnAllArticleSuccess")
    public void getAllArticlesTest_returnAllArticleSuccess() {
        // arrange
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1, "clean code", null));
        articles.add(new Article(2, "algorithm", null));
        when(articleRepo.findAll()).thenReturn(articles);
        // act
        List<Article> returnedArticles = articleService.getAllArticles();
        // assert
        assertIterableEquals(articles, returnedArticles);
    }

    @Test
    @DisplayName("addArticleTest_sendInstanceOfArticle_returnInstanceOfArticle")
    public void addArticleTest_sendInstanceOfArticle_returnInstanceOfArticle() {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleRepo.save(article)).thenReturn(article);
        // act
        Article returnArticle = articleService.addArticle(article);
        // assert
        assertEquals(article, returnArticle);
    }

    @Test
    @DisplayName("addArticleTest_sendInstanceOfArticleAlreadyExist_returnDuplicateException")
    public void addArticleTest_sendInstanceOfArticleAlreadyExist_returnDuplicateException() {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleService.isArticleExist(1)).thenReturn(true);
        // act & assert
        assertThrows(DuplicateEntityException.class, ()-> articleService.addArticle(article));
    }

    @Test
    @DisplayName("addArticleTest_sendInstanceOfArticleAlreadyExist_returnNullPointerException")
    public void addArticleTest_sendInstanceOfArticleAlreadyExist_returnNullPointerException() {
        // arrange
        Article article = new Article(1, "clean code", null);
        // act & assert
        assertThrows(NullPointerException.class, ()-> articleService.addArticle(article));
    }

    @Test
    @DisplayName("updateArticleTest_sendArticleIdAndInstanceOfArticle_returnInstanceOfUpdatedArticle")
    public void updateArticleTest_sendArticleIdAndInstanceOfArticle_returnInstanceOfUpdatedArticle() {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleService.isArticleExist(1)).thenReturn(true);
        when(articleRepo.getReferenceById(1)).thenReturn(article);
        when(articleRepo.save(article)).thenReturn(article);
        // act
        Article returnedArticle = articleService.updateArticle(1, article);
        // assert
        assertEquals(article, returnedArticle);
    }

    @Test
    @DisplayName("updateArticleTest_sendArticleIdAndInstanceOfArticle_returnNotFoundException")
    public void updateArticleTest_sendArticleIdAndInstanceOfArticle_returnNotFoundException() {
        // arrange
        Article article = new Article(1, "clean code", new Author());
        when(articleService.isArticleExist(1)).thenReturn(false);
        // act & assert
        assertThrows(NotFoundException.class, ()-> articleService.updateArticle(1, article));
    }

    @Test
    @DisplayName("updateArticleTest_sendArticleIdAndInstanceOfArticle_returnNullPointerException")
    public void updateArticleTest_sendArticleIdAndInstanceOfArticle_returnNullPointerException() {
        // arrange
        Article article = new Article();
        when(articleService.isArticleExist(1)).thenReturn(true);
        // act & assert
        assertThrows(NullPointerException.class, ()-> articleService.updateArticle(1, article));
    }

    @Test
    @DisplayName("deleteArticleTest_sendArticleId_returnNotFoundException")
    public void deleteArticleTest_sendArticleId() {
        // arrange
        when(articleService.isArticleExist(1)).thenReturn(true);
        // act
        articleService.deleteArticle(1);
        // assert
        verify(articleRepo, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("deleteArticleTest_sendArticleId_returnNotFoundException")
    public void deleteArticleTest_sendArticleId_returnNotFoundException() {
        // arrange
        when(articleService.isArticleExist(1)).thenReturn(false);
        // act & assert
        assertThrows(NotFoundException.class, ()-> articleService.deleteArticle(1));
    }
}
