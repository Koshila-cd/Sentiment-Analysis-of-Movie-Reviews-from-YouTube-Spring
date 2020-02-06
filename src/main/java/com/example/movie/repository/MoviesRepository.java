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

package com.example.movie.repository;

import com.example.movie.entity.Movies;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoviesRepository extends CrudRepository<Movies, Integer> {

    /**
     * find all datasets which matches the shared value (using JPQL) NativeQuery type was used due to
     * the complex scoring algorithm used for sorting.
     *
     * @return Page<Movies>
     */
    @Query(value = "SELECT * "
            + " FROM movies_list m ",
            nativeQuery = true)
    List<Movies> findAllMovies();

}
