package com.example.movierec2.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.movierec2.Genre;
import com.example.movierec2.Movie;
import com.example.movierec2.User;
import com.example.movierec2.repository.MovieRepository;
import com.example.movierec2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public List<Movie> getLikedMovies(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return user.getLikedMovies();
    }

    public List<Genre> getUserPreferredGenres(User user) {
        return user.getLikedMovies().stream()
                   .flatMap(movie -> movie.getGenres().stream())
                   .collect(Collectors.toList());
    }

    public List<Movie> getSuggestedMovies(User user) {
        List<Long> likedMovieIds = user.getLikedMovies().stream()
                                       .map(Movie::getId)
                                       .toList();

        List<Genre> preferredGenres = getUserPreferredGenres(user);

        return movieRepository.findMoviesByGenresIn(preferredGenres).stream()
                              .filter(movie -> !likedMovieIds.contains(movie.getId()))
                              .collect(Collectors.toList());
    }

}
