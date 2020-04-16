package com.example.movie.controller;

import com.example.movie.entity.Movies;
import com.example.movie.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@RunWith(SpringRunner.class)
//@WebMvcTest(MoviesResource.class)
public class MovieResourceIntegrationTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private MoviesService moviesService;
//
////    @Test
//    public void whenGetAllMovies_thenReturnJson()
//            throws Exception {
//
//        mvc.perform(get("/movies/all")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
////    @Test
//    public void whenAddAMovie_thenReturnObjectMovie() throws Exception {
//
//        mvc.perform(post("/movies/add")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
