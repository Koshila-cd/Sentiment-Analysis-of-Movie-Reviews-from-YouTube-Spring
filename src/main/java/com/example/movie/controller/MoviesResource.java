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
import com.example.movie.entity.MoviesVO;
import com.example.movie.entity.PythonVO;
import com.example.movie.service.MoviesService;
import com.example.movie.service.PythonService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/movies")
public class MoviesResource {

    private final Logger log = LoggerFactory.getLogger(MoviesResource.class);

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private PythonService pythonService;

    @PostMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<Movies> addNewMovie(@RequestBody MoviesVO moviesVO) throws GeneralSecurityException, IOException, ParseException {
        log.info("addNewMovie() moviesVO {}", moviesVO);
        Movies movieAdded = this.moviesService.addNewMovie(moviesVO);
        return new ResponseEntity<>(movieAdded, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Movies> getAllMovies() {
        log.info("getAllMovies()");
        Iterable<Movies> movies = this.moviesService.getAllMovies();
        return movies;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Optional<Movies> getMovie(@PathVariable("id") final int id) throws GeneralSecurityException, IOException, ParseException {
        log.info("getMovie()");
        Optional<Movies> movie = this.moviesService.getMovie(id);
        return movie;
    }

//    @Autowired
//    private CommentAnalysisService commentAnalysisService;

//    @GetMapping("analysingComments/{id}")
//    public void analysingComments(@PathVariable("id") String id) {
//        try {
//            commentAnalysisService.analysingComments(id, movie);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    @GetMapping(path = "/py")
//    public @ResponseBody
//    String getFromPython() {
//
//        HttpHeaders headers = new HttpHeaders();
//        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
//        headers.setContentType(mediaType);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://127.0.0.1:5000/sentiment",
//                HttpMethod.GET, entity, String.class);
//
//
//        String sentiment = this.moviesService.getFromPython(response.getBody());
//
//        return sentiment;
//
//    }

    @PostMapping(path = "/py")
    public @ResponseBody
    String getFromPython(@RequestBody PythonVO pythonVO) {
        log.info("getFromPython() pythonVO {}", pythonVO);
        return pythonService.analyse(pythonVO.getComment(), pythonVO.getDescription(), pythonVO.getTitle());
    }
}
