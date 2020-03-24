package com.example.movie.entity;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Thumbnail;

import javax.persistence.*;
import java.util.Date;

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
    private Date lastCommentTime;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "rate")
    private Double rate;

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

    public Date getLastCommentTime() {
        return lastCommentTime;
    }

    public void setLastCommentTime(Date lastCommentTime) {
        this.lastCommentTime = lastCommentTime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", lastCommentTime=" + lastCommentTime +
                ", thumbnail='" + thumbnail + '\'' +
                ", rate=" + rate +
                '}';
    }
}
