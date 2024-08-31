package com.example.movierec2.controller;

import java.security.Principal;
import java.util.List;

import com.example.movierec2.Movie;
import com.example.movierec2.User;
import com.example.movierec2.dto.MovieDto;
import com.example.movierec2.repository.MovieRepository;
import com.example.movierec2.repository.UserRepository;
import com.example.movierec2.service.MovieService;
import com.example.movierec2.service.TMDbService;
import com.example.movierec2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMDbService tmdbService;

    @GetMapping("/search")
    public String searchMovie(@RequestParam String title) {
        return tmdbService.searchMovie(title);
    }

    @GetMapping("/movie")
    public MovieDto getMovieDetails(@RequestParam long id) {
        return tmdbService.getMovieDetails(id);
    }

    @GetMapping("/popular")
    public String showAndSavePopularMovies(Model model) {
        List<MovieDto> movies = movieService.getPopularMovies();
        model.addAttribute("movies", movies);
        for (MovieDto movieDto : movies) {
            Movie movie = movieService.convertDtoToEntity(movieDto);
            boolean exists = movieRepository.existsById(movie.getId());
            if(!exists){
            movieRepository.save(movie);
            }
        }
        return "popularMovieList";
    }

    @PostMapping("/like")
    public String likeMovie(@RequestParam long movieId, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        movieService.addMovieToUserLikedList(user, movieId);
        return "redirect:/movies/popular";
    }
}
