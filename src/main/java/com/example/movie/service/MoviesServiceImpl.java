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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;

    private static final String DEVELOPER_KEY = "AIzaSyBmOXI6Fvazn_IXX9YjjoOOptUk26zLIkU";

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private DateTime lastCommentTime;
    private MovieDetails movieDetails = new MovieDetails();

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
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

        getComments(videoId);

        movies.setLastCommentTime(lastCommentTime);
        System.out.println(movies);
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
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *moviesRepository.save(movies);
     * @throws GeneralSecurityException, IOException
     */
    public DateTime getComments(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setMaxResults(2L)
                .execute();

        System.out.println(response);
        response.getItems().forEach(item -> {
            lastCommentTime = item.getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();
        });

        System.out.println(lastCommentTime);
        return lastCommentTime;
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *moviesRepository.save(movies);
     * @throws GeneralSecurityException, IOException
     */
    public MovieDetails getMovieDetails(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

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
