package com.realworld.graphqlapi.repository;

import com.realworld.graphqlapi.model.Article;
import com.realworld.graphqlapi.model.Label;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository {
    Iterable<Article> getAllArticles();

    Article getById(UUID id);

    Article findArticleByIdWithAuthor(UUID id);

    List<Article> findArticlesByLabel(Label label);
}
