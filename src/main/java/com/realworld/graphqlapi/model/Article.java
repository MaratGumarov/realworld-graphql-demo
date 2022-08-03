package com.realworld.graphqlapi.model;

import java.util.UUID;

public interface Article {
    UUID getId();
    Author getAuthor();
    UUID getAuthorId();
    Label getLabel();
    String getDescription();

}
