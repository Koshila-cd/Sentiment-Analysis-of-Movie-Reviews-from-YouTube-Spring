package com.example.movie.repository;

import com.example.movie.entity.MovieRatesView;
import com.example.movie.repository.mappers.MovieRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRateRepositoryImpl implements MovieRateRepository {

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public MovieRatesView getRate(Integer movieId) {
        List<MovieRatesView> query = jdbcTemplate.query("SELECT * FROM movies_rate WHERE movie_id = " + movieId, new MovieRateMapper());

        if (query.size() > 0) {
            return query.get(0);
        }
        return null;
    }

    @Override
    public Boolean checkSchedulerRun() {
        return this.jdbcTemplate.queryForObject("SELECT value FROM properties WHERE name = 'scheduler' LIMIT 1", Boolean.class);
    }
}
