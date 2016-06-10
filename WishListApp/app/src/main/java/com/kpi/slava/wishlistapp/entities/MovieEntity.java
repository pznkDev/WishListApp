package com.kpi.slava.wishlistapp.entities;

public class MovieEntity {

    private String id, title, genre, releaseYear, date, rating;
    private byte seen;

    public MovieEntity(String id, String title, String genre, String releaseYear, String date, String rating, byte seen) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.date = date;
        this.rating = rating;
        this.seen = seen;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public byte getSeen() {
        return seen;
    }
}
