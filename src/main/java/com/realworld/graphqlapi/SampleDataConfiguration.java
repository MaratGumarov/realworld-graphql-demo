package com.realworld.graphqlapi;

import com.realworld.graphqlapi.model.NewsArticle;
import com.realworld.graphqlapi.model.StaticImageArticle;
import com.realworld.graphqlapi.repository.ArticleRepository;
import com.realworld.graphqlapi.repository.AuthorRepository;
import com.realworld.graphqlapi.repository.DummyArticleRepository;
import com.realworld.graphqlapi.repository.DummyAuthorRepository;
import com.realworld.graphqlapi.resolver.ArticleResolver;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.kickstart.tools.SchemaParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SampleDataConfiguration {
    @Bean
    ArticleRepository articleRepository(AuthorRepository authors) {
        return new DummyArticleRepository(authors);
    }

    @Bean
    AuthorRepository authorRepository() {
        return new DummyAuthorRepository();
    }

    @Bean
    SchemaParser parser(ArticleRepository repository, ArticleResolver resolver) {
        return SchemaParser.newParser()
            .resolvers(resolver)
            .file("graphql/schema.graphqls")
            .dictionary("NewsArticle", NewsArticle.class)
            .dictionary("StaticImageArticle", StaticImageArticle.class)
            .build();
    }

    @Bean
    List<Instrumentation> instrumentations() {
        return new ArrayList<>(
            List.of(new MaxQueryDepthInstrumentation(4)));
    }
}
