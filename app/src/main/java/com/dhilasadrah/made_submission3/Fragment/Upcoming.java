package com.dhilasadrah.made_submission3.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dhilasadrah.made_submission3.Adapter.MovieAdapter;
import com.dhilasadrah.made_submission3.Model.Movies;
import com.dhilasadrah.made_submission3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Upcoming extends Fragment {
    private RecyclerView rv_upcoming;
    private ProgressBar progressBar;
    private Button button;

    private MovieAdapter movieAdapter;
    private ArrayList<Movies> movieList = new ArrayList<>();

    public Upcoming() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        button = view.findViewById(R.id.retry);
        progressBar = view.findViewById(R.id.pb_upcoming);
        rv_upcoming = view.findViewById(R.id.rv_upcoming);
        rv_upcoming.setLayoutManager(new LinearLayoutManager(getActivity()));

        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=4cb9ec1672f2d32cd3869b29775f7fc2&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray jsonArray = responseObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Movies movies = new Movies(object);
                        movieList.add(movies);
                    }
                    movieAdapter = new MovieAdapter(getActivity());
                    movieAdapter.setMovieList(movieList);
                    rv_upcoming.setAdapter(movieAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getFragmentManager() != null) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(Upcoming.this).attach(Upcoming.this).commit();
                        }
                    }
                });
                Toast.makeText(getContext(),R.string.check_connection, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
