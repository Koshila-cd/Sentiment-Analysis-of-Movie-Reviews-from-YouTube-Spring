package com.example.movie.scheduler;

import com.example.movie.service.YouTubeService;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CommentRetrieveScheduler {

    private static final String DEVELOPER_KEY = "AIzaSyBmOXI6Fvazn_IXX9YjjoOOptUk26zLIkU";
    private final YouTubeService youTubeService;

    String time = "2020-01-29 17:21:02";

    private DateFormat utubeDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public CommentRetrieveScheduler(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    public String getComment(String videoId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();

        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet");

        AtomicReference<CommentThreadListResponse> response = new AtomicReference<>(request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setMaxResults(1L)
                .execute());

        DateTime latestCommentTime = response.get().getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getUpdatedAt();
//        String format = utubeDateFormat.format(latestCommentTime);

        return "2020-01-31 01:58:02";
    }

//    private void compareTime() throws GeneralSecurityException, IOException {
//        String newTime = getComment("9ItBvH5J6ss");
//    }

    @Scheduled(fixedRate = 5000)
    public void getCommentScheduler() throws GeneralSecurityException, IOException {
//        String newTime = getComment("9ItBvH5J6ss");
    }
}
