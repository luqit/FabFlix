package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class GreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);

        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, "MOOOOOOOOOVIES", Toast.LENGTH_LONG).show();

        String jsonString = bundle.getString("jsonArrayString");
        String movieId = "";
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for(int x = 0; x < jsonArray.length(); x++)
                {
                    movieId = jsonArray.getJSONObject(x).getString("movieId");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((TextView) findViewById(R.id.last_page_msg_container)).setText(jsonString);
        }
    }


    public void goToRed(View view) {
        String msg = ((EditText) findViewById(R.id.green_2_red_message)).getText().toString();

        Intent goToIntent = new Intent(this, RedActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

    public void goToBlue(View view) {
        String msg = ((EditText) findViewById(R.id.green_2_blue_message)).getText().toString();

        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

}
