package com.example.movie.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentAnalysisService {

    private static final String DEVELOPER_KEY = "AIzaSyBmOXI6Fvazn_IXX9YjjoOOptUk26zLIkU";
    private final YouTubeService youTubeService;

    public CommentAnalysisService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    public DateTime analysingComments(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();
        DateTime latestCommentTime;
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(DEVELOPER_KEY)
                .setVideoId("9ItBvH5J6ss")
                .setMaxResults(2L)
                .execute());

        latestCommentTime = response.get().getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();

        new Thread(() -> {

            try {
                String nextPageToken;
                do {
                    response.get().getItems().forEach(item -> {
                        System.out.println(item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
                    });

                    nextPageToken = response.get().getNextPageToken();

                    response.set(request.setKey(DEVELOPER_KEY)
                            .setVideoId("9ItBvH5J6ss")
                            .setMaxResults(2L)
                            .setPageToken(nextPageToken)
                            .execute());
                    Thread.sleep(1000);
                } while (nextPageToken != null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        return latestCommentTime;
    }
}
