/*
 *   Copyright © 2020 Koshila Dissanayake (2016540),
 *   Informatics Institute of Technology.
 *   All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains the property of
 *  Informatics Institute of Technology. The intellectual and technical concepts contained
 *  herein are proprietary to Informatics Institute of Technology
 *  and copying or distribution of this software is strictly forbidden
 *  unless prior written permission is obtained from Informatics Institute of Technology
 */

package com.example.movie.service;

import com.example.movie.entity.Movies;
import org.json.simple.parser.ParseException;

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
    public Movies addNewMovie(String name, String url) throws GeneralSecurityException, IOException, ParseException;

    public Iterable<Movies> getAllMovies();

    public Optional<Movies> getMovie(final Integer id) throws GeneralSecurityException, IOException, ParseException;
}
