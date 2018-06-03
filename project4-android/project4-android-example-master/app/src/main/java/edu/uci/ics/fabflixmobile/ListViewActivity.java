package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class ListViewActivity extends Activity {
    private Integer offset = 0;
    private String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "MOOOOOOOOOVIES", Toast.LENGTH_LONG).show();

        offset = bundle.getInt("offset");
        jsonString = bundle.getString("jsonArrayString");
        String movieId = "";
        String movieTitle = "";
        String movieYear = "";
        String movieDirector = "";
        String starNames = "";
        String genreNames = "";
        String rating = "";
        final ArrayList<Movie> movies = new ArrayList<>();
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int x = offset; x < jsonArray.length(); x++) {
                    movieId = jsonArray.getJSONObject(x).getString("movieId");
                    movieTitle = jsonArray.getJSONObject(x).getString("movieTitle");
                    movieYear = jsonArray.getJSONObject(x).getString("movieYear");
                    movieDirector = jsonArray.getJSONObject(x).getString("movieDirector");
                    starNames = jsonArray.getJSONObject(x).getString("starNames");
                    genreNames = jsonArray.getJSONObject(x).getString("genreNames");
                    rating = jsonArray.getJSONObject(x).getString("rating");
                    movies.add(new Movie(movieId, movieTitle, movieYear, movieDirector, genreNames, starNames, rating));
                    if ((jsonArray.length() > offset+5) && (x == (offset+4))) {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);

        }
    }

    public void goToNextPage(View view) {
        Intent goToIntent = new Intent(this, ListViewActivity.class);

        goToIntent.putExtra("jsonArrayString", jsonString);
        goToIntent.putExtra("offset", offset+5);

        startActivity(goToIntent);

    }

    public void goToPreviousPage(View view) {
        Intent goToIntent = new Intent(this, ListViewActivity.class);

        if((offset-5) < 0)
        {
            Toast.makeText(getApplicationContext(), "There ain't no movies in the previous page, man!", LENGTH_SHORT).show();
            return;
        }
        goToIntent.putExtra("jsonArrayString", jsonString);
        goToIntent.putExtra("offset", offset-5);

        startActivity(goToIntent);

    }

    public void goToSingleMoviePage(View view){
        Intent goToIntent = new Intent(this, SingleMovieActivity.class);
        Log.d("ID", ((TextView)view.findViewById(R.id.movieTitle)).getText().toString());
        goToIntent.putExtra("movieTitle", ((TextView)view.findViewById(R.id.movieTitle)).getText().toString());
        goToIntent.putExtra("jsonArrayString", jsonString);
        startActivity(goToIntent);
    }
}