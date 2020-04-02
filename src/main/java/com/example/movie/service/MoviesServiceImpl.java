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
import com.example.movie.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class MoviesServiceImpl implements MoviesService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private CommentAnalysisService commentAnalysisService;

    /**
     * Add new movie to the database with the trailer URL
     *
     * @param moviesVO a new Movie to be added from {@link MoviesVO}
     * @return Movies
     */
    @Override
    public Movies addNewMovie(MoviesVO moviesVO) throws GeneralSecurityException, IOException {

        Movies movies = new Movies();
        movies.setMovieName(moviesVO.getMovieName());
        movies.setTrailerUrl(moviesVO.getTrailerUrl());

        String[] vId = moviesVO.getTrailerUrl().split("=");
        if (vId.length > 0) {
            String videoId = vId[1];
            Movies movies1 = commentAnalysisService.analysingComments(videoId, movies);
            movies.setLastCommentTime(movies1.getLastCommentTime());
            movies.setThumbnail(youTubeService.getMovieDetails(videoId).getThumbnail());
            movies.setRate(movies1.getRate());

            moviesRepository.save(movies);
        }

        return movies;
    }

    /**
     * Get all the Movies
     *
     * @return Movies
     */
    @Override
    public Iterable<Movies> getAllMovies() {
        Iterable<Movies> movies = this.moviesRepository.findAll();
        return movies;
    }

    /**
     * Get the video ID in trailer URL
     *
     * @return aMovie from Optional<Movies>
     * @throws GeneralSecurityException, IOException
     */
    @Override
    public Optional<Movies> getMovie(final Integer id) {

        Optional<Movies> movie = this.moviesRepository.findById(id);

        return movie;
    }


    @Override
    public String getFromPython(String sentiment) {

        return sentiment;
    }

    @Override
    public void updateMovie(Movies movie) {
        moviesRepository.save(movie);
    }

}
