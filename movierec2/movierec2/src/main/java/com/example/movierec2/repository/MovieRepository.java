package com.example.movierec2.repository;

import java.util.List;

import com.example.movierec2.Genre;
import com.example.movierec2.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g IN :genres")
    List<Movie> findMoviesByGenresIn(@Param("genres") List<Genre> genres);
}
