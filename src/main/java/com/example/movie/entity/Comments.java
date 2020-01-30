package com.example.movie.entity;

import com.google.api.client.util.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer comment_id;

    private String comments;

    private DateTime commenttime;
}
