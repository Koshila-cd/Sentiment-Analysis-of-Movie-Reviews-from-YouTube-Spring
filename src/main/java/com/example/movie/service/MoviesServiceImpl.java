package com.example.movie.service;

import com.example.movie.entity.Movies;
import com.example.movie.repository.MoviesRepository;
import org.springframework.stereotype.Service;

@Service
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Movies addNewMovie(String name, String url) {

        Movies movies = new Movies();
        movies.setMovie_name("Venom");
        movies.setTrailer_url("https://www.youtube.com/watch?v=u9Mv98Gr5pY");
        moviesRepository.save(movies);

        System.out.println(movies);
        return movies;
    }
}
