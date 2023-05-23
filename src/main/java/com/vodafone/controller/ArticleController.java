package com.vodafone.controller;

import com.vodafone.model.Article;
import com.vodafone.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping(value = "/articles/{id}")
    public Article getArticleById(@PathVariable(name = "id") Integer id) {
        return articleService.getArticleById(id);
    }

    @PostMapping(value = "/articles", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        article = articleService.addArticle(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles", produces = {"application/json"})
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(name = "authorName" , required = false) String authorName) {
        if (authorName != null) {
            return new ResponseEntity<>(articleService.getArticlesByAuthorName(authorName), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }


    @DeleteMapping(value = "/articles/{id}", produces = {"application/json"})
    public ResponseEntity<Article> deleteArticle(@PathVariable(name = "id") Integer id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/articles/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Article> updateArticle(@PathVariable(name = "id") Integer id, @RequestBody Article article) {
        return new ResponseEntity<>(articleService.updateArticle(id, article), HttpStatus.OK);
    }

}
