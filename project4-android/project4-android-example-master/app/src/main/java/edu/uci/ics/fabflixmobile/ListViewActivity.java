package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListViewActivity extends Activity {
    private String offset = "";
    private String jsonString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "MOOOOOOOOOVIES", Toast.LENGTH_LONG).show();

        offset = bundle.getString("offset");
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
                for (int x = 0; x < jsonArray.length(); x++) {
                    movieId = jsonArray.getJSONObject(x).getString("movieId");
                    movieTitle = jsonArray.getJSONObject(x).getString("movieTitle");
                    movieYear = jsonArray.getJSONObject(x).getString("movieYear");
                    movieDirector = jsonArray.getJSONObject(x).getString("movieDirector");
                    starNames = jsonArray.getJSONObject(x).getString("starNames");
                    genreNames = jsonArray.getJSONObject(x).getString("genreNames");
                    rating = jsonArray.getJSONObject(x).getString("rating");
                    movies.add(new Movie(movieId, movieTitle, movieYear, movieDirector, genreNames, starNames, rating));
                    if (jsonArray.length() > 5 && (x == 4)) {
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
        Intent goToIntent = new Intent(this, Pagination.class);

        goToIntent.putExtra("jsonArrayString", jsonString);
        goToIntent.putExtra("offset", 5);

        startActivity(goToIntent);

    }
}