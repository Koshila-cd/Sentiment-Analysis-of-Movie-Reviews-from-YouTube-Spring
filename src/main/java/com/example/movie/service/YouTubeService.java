package com.example.movie.service;

import com.example.movie.entity.MovieDetails;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class YouTubeService {

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String DEVELOPER_KEY = "AIzaSyBERRsW1tvyhIFH4FaTbzwF5BETUq0ojpQ";

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
    public static MovieDetails getMovieDetails(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        MovieDetails movieDetails = new MovieDetails();
        // Define and execute the API request
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet");
        VideoListResponse response = request.setKey(DEVELOPER_KEY)
                .setId(videoId).execute();

        response.getItems().forEach(item -> {
            movieDetails.setTitle(item.getSnippet().getTitle());
            movieDetails.setDescription(item.getSnippet().getDescription());
            movieDetails.setThumbnail(item.getSnippet().getThumbnails().getStandard().getUrl());
        });

        return movieDetails;

    }

}
