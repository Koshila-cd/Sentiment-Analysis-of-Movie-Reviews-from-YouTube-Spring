package com.example.movie.service;

import com.example.movie.entity.Movies;

public interface MoviesService {

    /**
     * add Movies.
     *
     * @param name
     * @return url
     */
    public Movies addNewMovie(String name, String url);
}
