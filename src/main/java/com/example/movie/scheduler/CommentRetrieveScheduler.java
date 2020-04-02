package com.example.movie.scheduler;

import com.example.movie.entity.Movies;
import com.example.movie.service.CommentAnalysisService;
import com.example.movie.service.MoviesService;
import com.example.movie.service.YouTubeService;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CommentRetrieveScheduler {

    private final Logger log = LoggerFactory.getLogger(CommentRetrieveScheduler.class);

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private CommentAnalysisService commentAnalysisService;

    @Scheduled(fixedRate = 30000)
    public void getCommentScheduler() {
        log.info("comment scheduler started...");
        Iterable<Movies> allMovies = moviesService.getAllMovies();
        if (allMovies != null) {
            List<Movies> movies = StreamSupport.stream(allMovies.spliterator(), false)
                    .collect(Collectors.toList());
            movies.stream().forEach(movie -> {
                try {
                    commentAnalysisService.analysingComments(movie.getTrailerUrl().split("=")[1], movie);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            });
        }
        log.info("comment scheduler end.");
    }
}
