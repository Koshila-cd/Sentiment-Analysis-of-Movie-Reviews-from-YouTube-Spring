package com.example.movie.service;

import com.example.movie.entity.Movies;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public interface MoviesService {

    /**
     * add Movies.
     *
     * @param name
     * @return url
     */
    public Movies addNewMovie(String name, String url);

    public Iterable<Movies> getAllMovies();

    public Optional<Movies> getMovie(final Integer id) throws GeneralSecurityException, IOException;
}
