package com.example.movie.service;

import com.example.movie.entity.MovieDetails;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;

@Service
public class YouTubeService {

    @Value(value = "${youtube.api.key}")
    private String developerKey;

    @Value(value = "${youtube.api.client_secret}")
    private String clientSecret;

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String CLIENT_SECRETS = "client_secret.json";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");



    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public YouTube getService() throws GeneralSecurityException, IOException {
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
    public MovieDetails getMovieDetails(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        MovieDetails movieDetails = new MovieDetails();
        // Define and execute the API request
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet,statistics");
        VideoListResponse response = request.setKey(developerKey)
                .setId(videoId).execute();

        response.getItems().forEach(item -> {
            movieDetails.setTitle(item.getSnippet().getTitle());
            movieDetails.setDescription(item.getSnippet().getDescription());
            movieDetails.setThumbnail(item.getSnippet().getThumbnails().getStandard().getUrl());
            movieDetails.setLikes(item.getStatistics().getLikeCount().intValue());
            movieDetails.setTotal(item.getStatistics().getLikeCount().intValue() + item.getStatistics().getDislikeCount().intValue());
            System.out.println("count!!!!");
            System.out.println(item.getStatistics().getLikeCount());
            System.out.println(item.getStatistics().getLikeCount().intValue() + item.getStatistics().getDislikeCount().intValue());
        });

        return movieDetails;

    }

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = YouTubeService.class.getResourceAsStream(clientSecret);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .build();
        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

}
