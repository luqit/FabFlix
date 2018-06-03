package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieListViewAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movies;

    public MovieListViewAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.layout_listview_row, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_listview_row, parent, false);

        Movie movie = movies.get(position);

        TextView titleView = (TextView)view.findViewById(R.id.movieTitle);
        TextView movieYearView = (TextView)view.findViewById(R.id.movieYear);
        TextView movieDirectorView = (TextView)view.findViewById(R.id.movieDirector);
        TextView starNamesView = (TextView)view.findViewById(R.id.starNames);
        TextView genreNamesView = (TextView)view.findViewById(R.id.genreNames);

        titleView.setText(movie.getMovieTitle());
        movieYearView.setText(movie.getMovieYear());
        movieDirectorView.setText(movie.getMovieDirector());
        starNamesView.setText(movie.getStarNames());
        genreNamesView.setText(movie.getGenreNames());

        return view;
    }
}
