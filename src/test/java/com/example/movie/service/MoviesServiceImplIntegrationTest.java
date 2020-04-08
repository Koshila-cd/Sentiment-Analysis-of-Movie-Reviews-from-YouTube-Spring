package com.example.movie.service;

import com.example.movie.entity.Movies;
import com.example.movie.repository.MoviesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class MoviesServiceImplIntegrationTest {

    @TestConfiguration
    static class MoviesServiceImplTestContextConfiguration {

        @Bean
        public MoviesService moviesService() {
            return new MoviesServiceImpl();
        }
    }

    @Autowired
    private MoviesService moviesService;

    @MockBean
    private MoviesRepository moviesRepository;

    private static final String MOVIE_NAME = "JUMANJI";
    private static final String TRAILER_URL = "https://www.youtube.com/watch?v=rBxcF-r9Ibs";
    private static final String THUMBNAIL = "https://i.ytimg.com/vi/rBxcF-r9Ibs/sddefault.jpg";

    @Before
    public void setUp() {
        Movies movies = new Movies();

        Mockito.when(moviesRepository.findAll())
                .thenReturn((Iterable<Movies>) movies);
    }

    @Test
    public void whenMovie_thenAllMoviesShouldBeFound() {

        Iterable<Movies> allMovies = moviesService.getAllMovies();

        List<Movies> movies = StreamSupport.stream(allMovies.spliterator(), false)
                .collect(Collectors.toList());

        movies.stream().forEach(movie -> {
            assertThat(movie.getMovieName()).isEqualTo(MOVIE_NAME);
            assertThat(movie.getTrailerUrl()).isEqualTo(TRAILER_URL);
            assertThat(movie.getThumbnail()).isEqualTo(THUMBNAIL);
        });

    }

    @Test
    public void whenMovieNameandTrailerUrl_thenAddToTheDatabase() {

    }


}
