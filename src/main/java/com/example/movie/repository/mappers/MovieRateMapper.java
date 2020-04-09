package com.example.movie.repository.mappers;

import com.example.movie.entity.MovieRatesView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRateMapper implements RowMapper<MovieRatesView> {

    @Override
    public MovieRatesView mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieRatesView mRate = new MovieRatesView();
        mRate.setMovieName(resultSet.getString("movie_name"));
        mRate.setRate(resultSet.getDouble("rate"));
        return mRate;
    }
}
