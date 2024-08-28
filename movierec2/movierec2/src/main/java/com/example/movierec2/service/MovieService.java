package com.example.movierec2.service;

import java.util.Collections;
import java.util.List;

import com.example.movierec2.Movie;
import com.example.movierec2.User;
import com.example.movierec2.dto.MovieDto;
import com.example.movierec2.dto.MovieResponse;
import com.example.movierec2.repository.MovieRepository;
import com.example.movierec2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<MovieDto> getPopularMovies() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        //debug statement
        if(response==null){System.out.println("API Request failed");}

        return response != null ? response.getResults() : Collections.emptyList();
    }

    public void addMovieToUserLikedList(User user, long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            if (!user.getLikedMovies().contains(movie)) {
                user.getLikedMovies().add(movie);
                userRepository.save(user);
                //debug statement
//                System.out.println("User " + user.getUsername() + " liked movie with ID: " + movieId);

            }
        }
        else{
            System.out.println("Movie not found.");
        }
    }

    public Movie convertDtoToEntity(MovieDto movieDto){
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        //movie.setOverview(movieDto.getOverview());   value too long to store in sql
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseDate(movieDto.getRelease_date());
        movie.setPosterPath(movieDto.getPoster_path());
        return movie;
    }
}
