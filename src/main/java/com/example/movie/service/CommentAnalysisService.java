package com.example.movie.service;

import com.example.movie.entity.MovieDetails;
import com.example.movie.entity.PythonVO;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentAnalysisService {

    private static final String DEVELOPER_KEY = "AIzaSyCntQXAorm69Yw5VKaAFUIOBwFOD6GQhig";
    private final YouTubeService youTubeService;
    //    private final MoviesServiceImpl moviesService;
    PythonVO pythonVO;

    @Autowired
    private PythonService pythonService;

    public CommentAnalysisService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
//        this.moviesService = moviesService;
    }

    public DateTime analysingComments(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();
        DateTime latestCommentTime;

        String description = youTubeService.getMovieDetails(videoId).getDescription();
        pythonVO.setDescription(description);
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setMaxResults(2L)
                .execute());

        latestCommentTime = response.get().getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();

        new Thread(() -> {

            try {
                String nextPageToken;
//                do {

                response.get().getItems().forEach(item -> {
                    System.out.println(item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
                    pythonVO.setComment(item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());

                    String analyse = pythonService.analyse("COMMNET IS HERE", "DESCRIP");
                    // Set the POST to python
                });

                nextPageToken = response.get().getNextPageToken();

                response.set(request.setKey(DEVELOPER_KEY)
                        .setVideoId(videoId)
                        .setMaxResults(100L)
                        .setPageToken(nextPageToken)
                        .execute());
                Thread.sleep(1000);

//                } while (nextPageToken != null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        return latestCommentTime;
    }
}
