package com.example.movierec2.service;

import com.example.movierec2.dto.MovieDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TMDbService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String apiKey;

    public TMDbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String searchMovie(String movieTitle) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + movieTitle;
        return restTemplate.getForObject(url, String.class);
    }

    public MovieDto getMovieDetails(long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, MovieDto.class);
    }
}
