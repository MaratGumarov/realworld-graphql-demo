package com.realworld.graphqlapi.repository;

import com.realworld.graphqlapi.model.Author;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface AuthorRepository {
    Iterable<Author> getAllAuthors();

    Author getById(UUID id);

    default List<Author> getAuthorsById(List<UUID> ids) {
        return ids.stream().map(this::getById).collect(Collectors.toList());
    }

    default List<Author> getAuthorsByIds(UUID... uuids) {
        return getAuthorsById(Arrays.asList(uuids));
    }
}
