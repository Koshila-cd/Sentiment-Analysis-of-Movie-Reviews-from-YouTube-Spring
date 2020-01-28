package com.example.movie.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer movie_id;

    private String movie_name;

    private String trailer_url;

    private ZonedDateTime lastcommenttime;

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public ZonedDateTime getLastcommenttime() {
        return lastcommenttime;
    }

    public void setLastcommenttime(ZonedDateTime lastcommenttime) {
        this.lastcommenttime = lastcommenttime;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movie_id=" + movie_id +
                ", movie_name='" + movie_name + '\'' +
                ", trailer_url='" + trailer_url + '\'' +
                ", lastcommenttime=" + lastcommenttime +
                '}';
    }
}
