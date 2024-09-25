package com.example.movierec2.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.movierec2.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {

    private long id;
    private String title;
    private String overview;
    private String release_date;
    private String poster_path;
    private List<Long> genre_ids = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    @JsonProperty("genre_ids")  // Explicitly map the field name
    public List<Long> getGenre_Ids() {
        return genre_ids;
    }

    @JsonProperty("genre_ids")
    public void setGenre_Ids(List<Long> genre_ids) {
        this.genre_ids = genre_ids;
    }
}
