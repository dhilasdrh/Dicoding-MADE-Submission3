package com.dhilasadrah.made_submission3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dhilasadrah.made_submission3.Model.Movies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieDetails extends AppCompatActivity {
    ImageView detail_backdrop, detail_poster;
    TextView detail_title, detail_releaseDate, detail_runtime, detail_genres, detail_overview;
    RatingBar detail_averageVote;
    ProgressBar progressBar;
    Button btnRetry;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        progressBar = findViewById(R.id.pb_details);
        btnRetry = findViewById(R.id.retry);
        scrollView = findViewById(R.id.scrollView);
        detail_backdrop = findViewById(R.id.detail_backdrop);
        detail_poster = findViewById(R.id.detail_poster);
        detail_title = findViewById(R.id.detail_title);
        detail_releaseDate = findViewById(R.id.detail_releaseDate);
        detail_runtime = findViewById(R.id.detail_runtime);
        detail_genres = findViewById(R.id.detail_genre);
        detail_overview = findViewById(R.id.detail_overview);
        detail_averageVote = findViewById(R.id.detail_voteAverage);

        Movies movies = getIntent().getParcelableExtra("movies");
        getDetail(movies.getMovieId());

        String title = movies.getTitle();
        String release_date = movies.getReleaseDate();
        String poster = movies.getPoster();
        String backdrop = movies.getBackdrop();
        float rating = (float) movies.getVoteAverage();

        detail_title.setText(title);
        detail_releaseDate.setText(release_date);
        detail_averageVote.setRating(rating/2);

        Glide.with(MovieDetails.this)
                .load("https://image.tmdb.org/t/p/w185/"+poster)
                .into(detail_poster);

        Glide.with(MovieDetails.this)
                .load("https://image.tmdb.org/t/p/w500/"+backdrop)
                .into(detail_backdrop);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movies.getTitle());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getDetail(int movie_id) {
        String url = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=4cb9ec1672f2d32cd3869b29775f7fc2&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    String overview = object.getString("overview");
                    detail_overview.setText(overview);

                    String runtime = String.valueOf(object.getInt("runtime"));
                    String duration = String.format(getString(R.string.minutes), runtime);
                    detail_runtime.setText(duration);

                    JSONArray jsonArray = object.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    detail_genres.setText(genres);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                btnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(getIntent());
                        finish();
                    }
                });
                Toast.makeText(getApplicationContext(),R.string.check_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}