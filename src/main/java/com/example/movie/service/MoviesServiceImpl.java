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

import com.example.movie.entity.MovieRatesView;
import com.example.movie.entity.Movies;
import com.example.movie.entity.MoviesVO;
import com.example.movie.repository.MovieRateRepository;
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

    @Autowired
    private MovieRateRepository movieRateRepository;

    /**
     * {@inheritDoc}
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

            moviesRepository.save(movies);
        }

        return movies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Movies> getAllMovies() {
        Iterable<Movies> movies = this.moviesRepository.findAll();

        movies.forEach(movie -> {
            MovieRatesView rate = movieRateRepository.getRate(movie.getMovieId());
            if (rate != null) {
                movie.setRate(String.format("%.0f", rate.getRate()) + "%");
            }
        });

        return movies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Movies> getMovie(final Integer id) {

        Optional<Movies> movie = this.moviesRepository.findById(id);

        return movie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFromPython(String sentiment) {

        return sentiment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMovie(Movies movie) {
        moviesRepository.save(movie);
    }

    @Override
    public Boolean checkSchedulerRun() {
        return this.movieRateRepository.checkSchedulerRun();
    }

}
