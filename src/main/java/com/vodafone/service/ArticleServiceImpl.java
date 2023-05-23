package com.vodafone.service;

import com.vodafone.model.Article;
import com.vodafone.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vodafone.errorhandling.NotFoundException;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleRepo articleRepo;

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
        return articleRepo.save(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleRepo.deleteById(id);
    }

    @Override
    public Article updateArticle(Integer id, Article article) {
        Article tmpArticle = articleRepo.getReferenceById(id);
        tmpArticle.setName(article.getName());
        tmpArticle.setAuthor(article.getAuthor());
        return articleRepo.save(tmpArticle);
    }

}
