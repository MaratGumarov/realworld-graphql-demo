package com.realworld.graphqlapi;

import com.realworld.graphqlapi.model.Article;
import com.realworld.graphqlapi.model.NewsArticle;
import com.realworld.graphqlapi.model.StaticImageArticle;
import com.realworld.graphqlapi.repository.ArticleRepository;
import com.realworld.graphqlapi.repository.AuthorRepository;
import com.realworld.graphqlapi.repository.DummyArticleRepository;
import com.realworld.graphqlapi.repository.DummyAuthorRepository;
import com.realworld.graphqlapi.resolver.ArticleResolver;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
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
    SchemaParser parser(ArticleRepository repository, List<GraphQLQueryResolver> queryResolvers, List<GraphQLResolver<Article>> resolvers) {
        return SchemaParser.newParser()
                .resolvers(resolvers)
                .resolvers(queryResolvers)
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
//
//    Other way to add child classes
//    @Bean
//    public SchemaParserDictionary schemaParserDictionary() {
//        return new SchemaParserDictionary()
//                .add(NewsArticle.class)
//                .add(StaticImageArticle.class);
//    }
}
