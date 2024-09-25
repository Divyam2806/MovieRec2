package com.example.movierec2.dto;

import java.util.List;

public class GenreResponse {

    private List<GenreDto> genres;

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }
}
