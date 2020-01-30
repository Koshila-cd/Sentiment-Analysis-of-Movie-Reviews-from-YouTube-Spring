package com.example.movie.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentAnalysisService {

    private static final String DEVELOPER_KEY = "AIzaSyBmOXI6Fvazn_IXX9YjjoOOptUk26zLIkU";
    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void analysingComments(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setMaxResults(2L)
                .execute());

        new Thread(() -> {

            try {
                String nextPageToken;
                do {
                    response.get().getItems().forEach(item -> {
                        System.out.println(item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
                    });

                    nextPageToken = response.get().getNextPageToken();

                    response.set(request.setKey(DEVELOPER_KEY)
                            .setVideoId(videoId)
                            .setMaxResults(2L)
                            .setPageToken(nextPageToken)
                            .execute());
                    Thread.sleep(1000);
                } while (nextPageToken != null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


        }).start();

    }
}
