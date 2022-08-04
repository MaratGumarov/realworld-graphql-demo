package com.realworld.graphqlapi;

import com.realworld.graphqlapi.model.Article;
import com.realworld.graphqlapi.model.NewsArticle;
import com.realworld.graphqlapi.model.StaticImageArticle;
import com.realworld.graphqlapi.repository.ArticleRepository;
import com.realworld.graphqlapi.repository.AuthorRepository;
import com.realworld.graphqlapi.repository.DummyArticleRepository;
import com.realworld.graphqlapi.repository.DummyAuthorRepository;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.kickstart.tools.SchemaParserDictionary;
import graphql.kickstart.execution.config.InstrumentationProvider;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;

import graphql.kickstart.tools.SchemaParserDictionary;
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
    SchemaParserDictionary dictionary() {
        return new SchemaParserDictionary()
            .add("NewsArticle", NewsArticle.class)
            .add("StaticImageArticle", StaticImageArticle.class);
    }

    @Bean
    List<Instrumentation> instrumentations() {
        return new ArrayList<>(
            List.of(new MaxQueryDepthInstrumentation(4)));
    }
}
