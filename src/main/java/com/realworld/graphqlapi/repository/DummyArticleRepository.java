package com.realworld.graphqlapi.repository;

import com.realworld.graphqlapi.exceptions.AuthorIsNotPresentException;
import com.realworld.graphqlapi.model.Article;
import com.realworld.graphqlapi.model.Label;
import com.realworld.graphqlapi.model.NewsArticle;
import com.realworld.graphqlapi.model.StaticImageArticle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummyArticleRepository implements ArticleRepository {

    private final List<Article> articles;

    public DummyArticleRepository(AuthorRepository authors) {
        articles = Stream.of(
                new NewsArticle(new UUID(1L, 2L), "article-1", "desc1", authors.getById(new UUID(1L, 2L)), new UUID(1L, 2L), "date1", Label.SPORT),
                new NewsArticle(new UUID(1L, 3L), "article-2", "desc2", authors.getById(new UUID(1L, 3L)), new UUID(1L, 3L), "date2", Label.POLITICS),
                new NewsArticle(new UUID(1L, 4L), "article-3", "desc3", authors.getById(new UUID(1L, 4L)), new UUID(1L, 4L), "date3", Label.SCIENCE),
                new NewsArticle(new UUID(1L, 5L), "article-4", "desc4", authors.getById(new UUID(1L, 5L)), new UUID(1L, 5L), "date4", Label.WORLD),
                new NewsArticle(new UUID(1L, 6L), "article-55", "desc55", null, null, "date5", null),
                new StaticImageArticle(new UUID(1L, 7L), "https://pictures/static-logo.jpg", authors.getById(new UUID(1L, 2L)), new UUID(1L, 2L), "desc7", Label.OTHER)
        ).sorted(Comparator.comparing(Article::getId)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Article> getAllArticles() {
        return articles;
    }

    @Override
    public Article getById(UUID id) {
        return articles.stream().filter(a -> id.equals(a.getId())).findFirst().orElse(null);
    }

    @Override
    public Article findArticleByIdWithAuthor(UUID id) {
        Article article = getById(id);

        if (article.getAuthorId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("articleId", id);
            throw new AuthorIsNotPresentException("Author is null for " + id, params);
        }
        return article;
    }

    @Override
    public List<Article> findArticlesByLabel(Label label) {
        return articles.stream().filter(article -> article.getLabel() == label).collect(Collectors.toList());
    }

    @Override
    public List<Article> getAfter(UUID id) {
        return articles.stream()
            .dropWhile(article -> article.getId().compareTo(id) < 0).collect(Collectors.toUnmodifiableList());
    }

//    @Override
//    public List<NewsArticle> findNewsArticles() {
//        return articles.stream().filter(article -> article instanceof NewsArticle)
//                .map(item -> (NewsArticle) item)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<StaticImageArticle> findStaticImagesArticles() {
//        return articles.stream().filter(article -> article instanceof StaticImageArticle)
//                .map(item -> (StaticImageArticle) item)
//                .collect(Collectors.toList());
//    }
}
