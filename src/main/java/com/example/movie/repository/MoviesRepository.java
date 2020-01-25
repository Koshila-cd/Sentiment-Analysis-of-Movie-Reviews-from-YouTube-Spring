package com.example.movie.repository;

import com.example.movie.entity.Movies;
import org.springframework.data.repository.CrudRepository;

public interface MoviesRepository extends CrudRepository<Movies, Integer> {
}
