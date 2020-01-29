/*
 *   Copyright © 2020 Koshila Dissanayake (2016540),
 *   Informatics Institute of Technology.
 *   All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains the property of
 *  Informatics Institute of Technology. The intellectual and technical concepts contained
 *  herein are proprietary to Informatics Institute of Technology
 *  and copying or distribution of this software is strictly forbidden
 *  unless prior written permission is obtained from Informatics Institute of Technology
 */

package com.example.movie.controller;

import com.example.movie.entity.Movies;
import com.example.movie.repository.MoviesRepository;
import com.example.movie.service.MoviesService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/movies")
public class MoviesResource {

    @Autowired
    private MoviesRepository moviesRepository;

    private final MoviesService moviesService;

    public MoviesResource(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<Movies> addNewMovie(@RequestParam String name
            , @RequestParam String url) throws GeneralSecurityException, IOException, ParseException {

        Movies movieAdded = this.moviesService.addNewMovie(name, url);

        return new ResponseEntity<>(movieAdded, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Movies> getAllMovies() {

        Iterable<Movies> movies = this.moviesService.getAllMovies();
        return movies;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Movies> getMovie(@PathVariable("id") final int id) throws GeneralSecurityException, IOException, ParseException {

        Optional<Movies> movie = this.moviesService.getMovie(id);
        return movie;
    }
}
