package com.example.movie.repository;

import com.example.movie.entity.MovieRatesView;


public interface MovieRateRepository {

    MovieRatesView getRate(Integer movieId);

    Boolean checkSchedulerRun();
}
