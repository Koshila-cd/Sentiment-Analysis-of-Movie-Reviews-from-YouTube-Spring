package com.example.movie.service;

import com.example.movie.entity.Movies;
import com.example.movie.repository.MoviesRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
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

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    /**
     * Add new movie to the database with the trailer URL
     *
     * @param name Movie name, url Trailer url
     * @return Movies
     */
    @Override
    public Movies addNewMovie(String name, String url) {

        Movies movies = new Movies();
        movies.setMovie_name("Venom");
        movies.setTrailer_url("https://www.youtube.com/watch?v=u9Mv98Gr5pY");
        moviesRepository.save(movies);

        System.out.println(movies);
        return movies;
    }

    /**
     * Get all the Movies
     *
     * @return Movies
     * @throws GeneralSecurityException, IOException
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
    public Optional<Movies> getMovie(final Integer id) throws GeneralSecurityException, IOException {

        Optional<Movies> movie = this.moviesRepository.findById(id);
        String videoId;

        if (movie != null) {
            String[] vId = movie.get().getTrailer_url().split("watch\\?v=");
            videoId = vId[1];

            getComments(videoId);
        }
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
     *
     * @throws GeneralSecurityException, IOException
     */
    public void getComments(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setMaxResults(1L)
                .execute();
        System.out.println(response);
    }
}
