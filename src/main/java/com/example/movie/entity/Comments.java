package com.example.movie.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer comment_id;

    private String comments;

    private ZonedDateTime commenttime;
}
