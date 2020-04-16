package com.example.movie.entity;

import com.google.api.services.youtube.model.Thumbnail;

import java.math.BigInteger;

public class MovieDetails {

    private String title;

    private String description;

    private String thumbnail;

    private Integer likes;

    private Integer dislikes;

    private Integer total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MovieDetails{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", total=" + total +
                '}';
    }
}
