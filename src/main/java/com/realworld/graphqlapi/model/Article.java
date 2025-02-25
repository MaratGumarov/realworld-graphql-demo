package com.realworld.graphqlapi.model;

import java.util.UUID;

public interface Article {
    UUID getId();
    Author getAuthor();
    Label getLabel();
    String getDescription();

}
