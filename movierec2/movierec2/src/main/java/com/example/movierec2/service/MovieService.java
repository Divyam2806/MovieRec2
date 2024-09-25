package com.example.movierec2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.movierec2.Genre;
import com.example.movierec2.Movie;
import com.example.movierec2.User;
import com.example.movierec2.dto.MovieDto;
import com.example.movierec2.dto.GenreDto;
import com.example.movierec2.dto.MovieResponse;
import com.example.movierec2.repository.GenreRepository;
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
    private GenreRepository genreRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GenreService genreService;

    public List<MovieDto> getPopularMoviesInDtoFormat() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        if (response != null) {
            return response.getResults();
        } else {
            return Collections.emptyList();
        }
    }

    public void addMovieToUserLikedList(User user, long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null && !user.getLikedMovies().contains(movie)) {
            user.getLikedMovies().add(movie);
            userRepository.save(user);
        }
    }

    public Movie convertDtoToEntity(MovieDto movieDto){

        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseDate(movieDto.getRelease_date());
        movie.setPosterPath(movieDto.getPoster_path());

        // Create a list to hold the Genre entities
        List<Genre> genres = new ArrayList<>();

        // Map genre IDs to Genre entities
        if (movieDto.getGenre_Ids() != null) {

//            System.out.println(movieDto.getGenreIds());
            for (Long genreId : movieDto.getGenre_Ids()) {
                Genre genre = genreRepository.findById(genreId)
                                             .orElse(null); // Fetch genre from the database

                if (genre != null) {

                    genres.add(genre); // Add to the list if found
                }

            }
        }
        movie.setGenres(genres);

        return movie;
    }
}
