package com.vodafone.repository;

import com.mysql.cj.result.IntegerValueFactory;
import com.vodafone.model.Article;
import com.vodafone.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    //SELECT ed FROM EvoucherDefinition ed WHERE ed.id = :id
//    @Query("select a from article join author where author.id = ar.authorId and author.name = authorName;")
//    List<Article> findAritclesByAuthorName(@Param("authorName") String authorName);
}
