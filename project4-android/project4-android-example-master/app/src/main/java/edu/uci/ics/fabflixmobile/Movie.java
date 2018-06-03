package edu.uci.ics.fabflixmobile;

public class Movie {
    private String movieId;
    private String movieTitle;
    private String movieYear;
    private String movieDirector;
    private String genreNames;
    private String starNames;
    private String rating;

    public Movie(String movieId, String movieTitle, String movieYear,
                 String movieDirector, String genreNames, String starNames, String rating) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        this.genreNames = genreNames;
        this.starNames = starNames;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() { return movieTitle; }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public String getGenreNames() {
        return genreNames;
    }

    public String getStarNames() { return starNames; }

    public String getRating() { return rating; }

}
