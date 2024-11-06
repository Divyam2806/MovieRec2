package com.example.movierec2.controller;

import java.security.Principal;
import java.util.List;

import com.example.movierec2.Movie;
import com.example.movierec2.User;
import com.example.movierec2.dto.MovieDto;
import com.example.movierec2.repository.UserRepository;
import com.example.movierec2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/liked")
    public String showLikedMovies(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        List<Movie> likedMovies = userService.getLikedMovies(user.getId());
        model.addAttribute("movies", likedMovies);
        return "likedMovies";
    }

    @GetMapping("/suggest")
    public String showSuggestedMovies(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        List<Movie> movies = userService.getSuggestedMovies(user);
        model.addAttribute("movies", movies);

        return "suggestedMovies";
    }
}
