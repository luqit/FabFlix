package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BlueActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        Bundle bundle = getIntent().getExtras();


        String msg = bundle.getString("message");
        if (msg != null && !"".equals(msg)) {
            ((TextView) findViewById(R.id.last_page_msg_container)).setText(msg);
        }
    }
        public void connectToTomcat(View view) {

            String fullTextSearch = ((EditText) findViewById(R.id.fullTextSearch)).getText().toString();
            Log.d("fullTextSearch", fullTextSearch);

            // Post request form data
            final Map<String, String> params = new HashMap<String, String>();
            params.put("fullTextSearch", fullTextSearch);

            final Intent goToIntent = new Intent(this, GreenActivity.class);

            // no user is logged in, so we must connect to the server

            // Use the same network queue across our application
            final RequestQueue queue = NetworkManager.sharedManager(this).queue;


            final StringRequest loginRequest = new StringRequest(Request.Method.GET, "https://192.168.0.111:8443/project2-api-example/MovieFullTextSearch?fullTextSearch=" + fullTextSearch,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("response$$$$$$$$$$", response);
                            if(response != null || !response.isEmpty()) {
                                goToIntent.putExtra("jsonArrayString", response);
                                startActivity(goToIntent);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("security.error", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }  // HTTP POST Form Datz
            };

            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(loginRequest);
        }



    public void goToRed(View view) {
        String msg = ((EditText) findViewById(R.id.fullTextSearch)).getText().toString();

        Intent goToIntent = new Intent(this, RedActivity.class);

        goToIntent.putExtra("last_activity", "blue");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

    public void goToGreen(View view) {
        String msg = ((EditText) findViewById(R.id.blue_2_green_message)).getText().toString();

        Intent goToIntent = new Intent(this, GreenActivity.class);

        goToIntent.putExtra("last_activity", "blue");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

}
