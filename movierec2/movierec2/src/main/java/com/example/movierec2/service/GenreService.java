package com.example.movierec2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.movierec2.Genre;
import com.example.movierec2.dto.GenreDto;
import com.example.movierec2.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private TMDbService tmdbService;

    @Autowired
    private GenreRepository genreRepository;

    private Map<Long, Genre> genreMap = new HashMap<>(); // Cache for genres

    @PostConstruct // This method will run once when the application starts
    public void initializeGenreCache() {
        System.out.println("Fetching genres from TMDb...");
        List<GenreDto> genres = tmdbService.getAllGenres();
        for (GenreDto genreDto : genres) {
            genreMap.put(genreDto.getId(), new Genre(genreDto.getId(), genreDto.getName()));
            Genre genre = new Genre(genreDto.getId(), genreDto.getName());
            genreRepository.save(genre);
        }
        System.out.println("Genres fetched and cached successfully.");
    }

    public List<Genre> mapGenreIdsToGenres(List<Long> genreIds) {
        return genreIds.stream()
                       .map(genreMap::get) // Map genre ID to Genre object
                       .filter(Objects::nonNull) // Filter out any nulls
                       .collect(Collectors.toList());
    }

    public Map<Long, Genre> getGenreMap() {
        return genreMap;
    }
}