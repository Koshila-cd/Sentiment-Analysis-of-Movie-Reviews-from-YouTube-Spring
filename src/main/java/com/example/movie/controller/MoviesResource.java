package com.example.movie.controller;

import com.example.movie.entity.Movies;
import com.example.movie.repository.MoviesRepository;
import com.example.movie.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/movies")
public class MoviesResource {

    @Autowired
    private MoviesRepository moviesRepository;

    private final MoviesService moviesService;

    public MoviesResource(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<Movies> addNewMovie(@RequestParam String name
            , @RequestParam String url) {

        System.out.println(name);
        Movies movieAdded = this.moviesService.addNewMovie(name, url);

        return new ResponseEntity<>(movieAdded, HttpStatus.CREATED);
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Movies> getAllMovies() {
        System.out.println(moviesRepository.findAll());
        return moviesRepository.findAll();
    }
}
