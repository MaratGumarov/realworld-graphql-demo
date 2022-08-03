package com.realworld.graphqlapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Author {
    private UUID id;
    private String firstName;
    private String lastName;
    private List<Author> friends;
}
