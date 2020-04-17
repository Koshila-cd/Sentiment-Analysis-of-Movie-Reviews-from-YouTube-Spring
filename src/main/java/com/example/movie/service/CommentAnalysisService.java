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

    @Autowired
    private PythonService pythonService;

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private MoviesService moviesService;

    private DateFormat lastTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    AtomicInteger noOfComments = new AtomicInteger();
    AtomicInteger positive = new AtomicInteger();

    /**
     * Add new movie to the database with the trailer URL
     *
     * @param videoId video Id split from the trailer URL
     * @return Movies
     */
    public Movies analysingComments(String videoId, Movies movie) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();
        Movies movies = movie;
        String description = youTubeService.getMovieDetails(videoId).getDescription();
        String title = movies.getMovieName();

        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet,replies");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(developerKey)
                .setVideoId(videoId)
                .setMaxResults(movie.getLastCommentTime() == null ? 100L : 3L)
                .execute());

        log.info("youtube response size {}", response.get().getItems().size());
        System.out.println("response");
        System.out.println(response);

        Date latestCommentTime = null;
        List<CommentThread> items = response.get().getItems();
        if (items.size() > 0) {
            latestCommentTime = new Date(items.get(0).getSnippet().getTopLevelComment().getSnippet().getUpdatedAt().getValue());
        }

        new Thread(() -> {

//            try {
            AtomicBoolean completed = new AtomicBoolean(false);
//
//                int loopCount = 0;

//                do {
            response.get().getItems()
                    .stream()
                    .forEach(item -> {

                        DateTime time = item.getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();
                        Date commentTime = new Date(time.getValue());
                        log.info("last time: {}, new comment time: {}", movie.getLastCommentTime() == null ? "NO" : lastTimeFormat.format(movie.getLastCommentTime()), lastTimeFormat.format(commentTime));

                        if (movie.getLastCommentTime() == null || movie.getLastCommentTime().before(commentTime)) {
                            String comment = item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay();

                            log.info("============================================");
                            log.info("new comment: {}", comment);

                            final String sentiment = pythonService.analyse(comment, description, title);
                            log.info("sentiment: {}", sentiment);

                            if (!"None".equals(sentiment)) {
                                noOfComments.getAndIncrement();
                                movies.setComments(noOfComments.intValue());
                            }

                            if ("p".equals(sentiment)) {
                                positive.getAndIncrement();
                                movies.setPositive(positive.intValue());

                                try {
                                    movies.setLikes(youTubeService.getMovieDetails(videoId).getLikes());
                                    movies.setDislikes(youTubeService.getMovieDetails(videoId).getDislikes());
                                } catch (GeneralSecurityException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            moviesService.updateMovie(movie);
                            log.info("updated");
                        } else {
                            completed.set(true);
                            return;
                        }

                    });

//                    loopCount++;
//
//                    if (movie.getLastCommentTime() == null) {
//                        response.set(request.setKey(developerKey)
//                                .setVideoId(videoId)
//                                .setMaxResults(10L)
//                                .setPageToken(response.get().getNextPageToken())
//                                .execute());
//
//                        Thread.sleep(1000);
//                        log.info("loop runs {}", loopCount);
//                    } else {
//                        break;
//                    }
//                } while (loopCount == 2);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }

        }).start();

        log.info("latest and next comment date: {}", latestCommentTime);
        if (latestCommentTime != null) movies.setLastCommentTime(latestCommentTime);
        log.info("updated positive: {}", positive);
        return movies;
    }
}
