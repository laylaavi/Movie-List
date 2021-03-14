package com.example.mobi6006_la26_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobi6006_la26_group_project.Object.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rvHome;
    AdapterHome adapterHome;
    DatabaseHelper dbHelper;
    public static ArrayList<Movie> movieArrayList;
    private static String URL_JSON;
    int userId;
    String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DatabaseHelper(this, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION);
        movieArrayList = new ArrayList<>();

        rvHome = findViewById(R.id.rvHome);
        URL_JSON = "https://gist.githubusercontent.com/Urdzik/de477f8e3d7baf4366c9d797cfe63531/raw/38c6afa2937ef222323392cc34c8c8c77e02fc40/Movie.json";
        movieJSON();

        userId = getIntent().getIntExtra("userId",0);
        currentPassword = getIntent().getStringExtra("currentPassword");
    }

    private void movieJSON() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET, URL_JSON, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject movieObject = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(movieObject.getString("Title"));
                                movie.setGenre(movieObject.getString("Genre"));
                                movie.setPoster(movieObject.getString("Poster"));
                                movie.setRuntime(movieObject.getString("Runtime"));
                                movie.setReleased(movieObject.getString("Released"));
                                movie.setDirector(movieObject.getString("Director"));
                                movie.setWriter(movieObject.getString("Writer"));
                                movie.setActor(movieObject.getString("Actors"));
                                movie.setProduction(movieObject.getString("Production"));
                                movie.setDescription(movieObject.getString("Plot"));
                                movie.setRating(movieObject.getDouble("imdbRating"));
                                movieArrayList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        rvHome.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapterHome = new AdapterHome(movieArrayList, getApplication());
                        rvHome.setAdapter(adapterHome);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menubar = getMenuInflater();
        menubar.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.changePassword:
                intent = new Intent(this, ChangePasswordActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("currentPassword", currentPassword);
                startActivity(intent);
                return true;
            case R.id.aboutUs:
                intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Thank you for visiting Movie Time!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}