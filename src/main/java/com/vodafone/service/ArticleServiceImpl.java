package com.vodafone.service;

import com.vodafone.errorhandling.DuplicateEntityException;
import com.vodafone.model.Article;
import com.vodafone.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vodafone.errorhandling.NotFoundException;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    ArticleRepo articleRepo;

    @Override
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleRepo.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("The Article with id '%s' was not found", id)));
    }

    @Override
    public List<Article> getArticlesByAuthorName(String authorName) {
        List<Article> articles = articleRepo.findAll();
        for (Article article : articles) {
            if(article.getAuthor().getName().equals(authorName)) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public Article addArticle(Article article) {
        if (article.getAuthor() == null) {
            throw new NullPointerException("the author is empty.");
        }
        else if (isArticleExist(article.getId())) {
            throw new DuplicateEntityException("This article is already exist.");
        }
        return articleRepo.save(article);
    }

    @Override
    public boolean deleteArticle(Integer id) {
        if (!isArticleExist(id)) {
            throw new NotFoundException("this article doesn't exist.");
        }
        articleRepo.deleteById(id);
        return true;
    }

    @Override
    public Article updateArticle(Integer id, Article article) {
        if (!isArticleExist(id)) {
            throw new NotFoundException("This article doesn't exist.");
        }
        else if (article == null) {
            throw new NullPointerException("The article is empty.");
        }
        Article tmpArticle = articleRepo.getReferenceById(id);
        tmpArticle.setName(article.getName());
        tmpArticle.setAuthor(article.getAuthor());
        return articleRepo.save(tmpArticle);
    }

    public boolean isArticleExist(int articleId) {
        return articleRepo.existsById(articleId);
    }
}
