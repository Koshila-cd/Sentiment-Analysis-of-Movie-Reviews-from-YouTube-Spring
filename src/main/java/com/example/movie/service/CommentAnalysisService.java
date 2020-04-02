package com.example.movie.service;

import com.example.movie.entity.Movies;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CommentAnalysisService {

    private final Logger log = LoggerFactory.getLogger(CommentAnalysisService.class);

    @Value(value = "${youtube.api.key}")
    private String developerKey;

    private int noOfComments = 0;
    private int positive = 0;
    private int negative = 0;
    private Double rate = 0.0;

    @Autowired
    private PythonService pythonService;

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private MoviesService moviesService;

    private DateFormat lastTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Movies analysingComments(String videoId, Movies movie) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();
        Movies movies = movie;
        String description = youTubeService.getMovieDetails(videoId).getDescription();

        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(developerKey)
                .setVideoId(videoId)
                .setMaxResults(2L)
                .execute());

        log.info("youtube response size {}", response.get().getItems().size());

        Date latestCommentTime = null;

//        new Thread(() -> {

//            try {
        String nextPageToken;
        AtomicBoolean completed = new AtomicBoolean(false);
        AtomicInteger positive = new AtomicInteger();

//                do {
        response.get().getItems()
                .stream()
                .forEach(item -> {

                    DateTime time = item.getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();
                    Date commentTime = new Date(time.getValue());

                    log.info("last time: {}, new comment time: {}", lastTimeFormat.format(movie.getLastCommentTime()), lastTimeFormat.format(commentTime));
                    if (movie.getLastCommentTime().before(commentTime)) {
                        String comment = item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay();
                        log.info("============================================");
                        log.info("new comment: {}", comment);
                        final String sentiment = pythonService.analyse(comment, description);
                        if ("p".equals(sentiment)) {
                            positive.getAndIncrement();
                        }
                    } else {
                        completed.set(true);
                        return;
                    }

                });

//                nextPageToken = response.get().getNextPageToken();
//
//                response.set(request.setKey(developerKey)
//                        .setVideoId(videoId)
//                        .setMaxResults(10L)
//                        .setPageToken(nextPageToken)
//                        .execute());

        List<CommentThread> items = response.get().getItems();
        if (items.size() > 0) {
            latestCommentTime = new Date(items.get(0).getSnippet().getTopLevelComment().getSnippet().getUpdatedAt().getValue());
        }

//                Thread.sleep(1000);

//                } while (nextPageToken != null);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }

//        }).start();

        log.info("latest and next comment date: {}", latestCommentTime);
        if (latestCommentTime != null) movies.setLastCommentTime(latestCommentTime);
        return movies;
    }
}
