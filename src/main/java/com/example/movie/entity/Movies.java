package com.example.movie.entity;

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

    private String rate;

    @Column(name = "positive")
    private Integer positive;

    @Column(name = "comments")
    private Integer comments;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
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
                ", positive=" + positive +
                ", comments=" + comments +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
