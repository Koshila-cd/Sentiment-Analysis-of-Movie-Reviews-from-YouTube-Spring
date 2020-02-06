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

import com.example.movie.entity.MovieDetails;
import com.example.movie.entity.Movies;
import com.example.movie.entity.MoviesVO;
import com.example.movie.repository.MoviesRepository;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;
    private final YouTubeService youTubeService;
    private final CommentAnalysisService commentAnalysisService;
//    yyyy-MM-dd hh:mm:ss
    private DateFormat utubeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private static final String DEVELOPER_KEY = "AIzaSyBmOXI6Fvazn_IXX9YjjoOOptUk26zLIkU";
    private DateTime lastCommentTime;

    public MoviesServiceImpl(MoviesRepository moviesRepository, YouTubeService youTubeService, CommentAnalysisService commentAnalysisService) {
        this.moviesRepository = moviesRepository;
        this.youTubeService = youTubeService;
        this.commentAnalysisService = commentAnalysisService;
    }

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

        String[] vId = moviesVO.getTrailerUrl().split("watch\\?v=");
        String videoId = vId[1];

        DateTime time = commentAnalysisService.analysingComments("9ItBvH5J6ss");
        String formatTime = utubeDateFormat.format(time);

        movies.setLastCommentTime(formatTime);
        moviesRepository.save(movies);

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
        System.out.println(movies);

        return movies;
    }

    /**
     * Get the video ID in trailer URL
     *
     * @return a Movie from Optional<Movies>
     * @throws GeneralSecurityException, IOException
     */
    @Override
    public Optional<Movies> getMovie(final Integer id) {

        Optional<Movies> movie = this.moviesRepository.findById(id);

        return movie;
    }


    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException
     */
    public MovieDetails getMovieDetails(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();

        MovieDetails movieDetails = new MovieDetails();
        // Define and execute the API request
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet");
        VideoListResponse response = request.setKey(DEVELOPER_KEY)
                .setId(videoId).execute();

        response.getItems().forEach(item -> {
            movieDetails.setTitle(item.getSnippet().getTitle());
            movieDetails.setDescription(item.getSnippet().getDescription());
            movieDetails.setThumbnail(item.getSnippet().getThumbnails().getStandard());
        });

        return movieDetails;

    }

}
