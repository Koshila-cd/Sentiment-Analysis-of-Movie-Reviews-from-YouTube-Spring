package com.example.movie.entity;

import com.google.api.client.util.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "movies_list")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "lastcommenttime")
    private String lastCommentTime;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getLastCommentTime() {
        return lastCommentTime;
    }

    public void setLastCommentTime(String lastCommentTime) {
        this.lastCommentTime = lastCommentTime;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", lastCommentTime=" + lastCommentTime +
                '}';
    }
}
