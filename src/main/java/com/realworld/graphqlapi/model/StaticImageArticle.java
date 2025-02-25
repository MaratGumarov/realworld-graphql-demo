package com.realworld.graphqlapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class StaticImageArticle implements Article {
    private UUID id;
    private String url;
    private Author author;
    private String description;
    private Label label;
}
