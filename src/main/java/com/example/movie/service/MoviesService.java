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
import com.example.movie.entity.MoviesVO;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public interface MoviesService {

    /**
     * Add new movie to the database with the trailer URL
     *
     * @param moviesVO a new Movie to be added from {@link MoviesVO}
     * @return Movies
     */
    public Movies addNewMovie(MoviesVO moviesVO) throws GeneralSecurityException, IOException, ParseException;

    /**
     * Get all the Movies
     *
     * @return Movies
     */
    public Iterable<Movies> getAllMovies();

    /**
     * Get the video ID in trailer URL
     *
     * @param id movie Id
     * @return movie from Optional<Movies>
     */
    public Optional<Movies> getMovie(final Integer id) throws GeneralSecurityException, IOException, ParseException;

    /**
     * Get Python response after sending comment and description
     *
     * @param sentiment python response of sentiment polarity
     *
     * @return sentiment
     */
    public String getFromPython(String sentiment);

    /**
     * Update movie parameters in database
     *
     * @param movie a new Movie to be added from {@link Movies}
     *
     */
    void updateMovie(Movies movie);

    Boolean checkSchedulerRun();
}
