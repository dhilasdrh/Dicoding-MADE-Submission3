package com.dhilasadrah.made_submission3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhilasadrah.made_submission3.MovieDetails;
import com.dhilasadrah.made_submission3.Model.Movies;
import com.dhilasadrah.made_submission3.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movies> movieList = new ArrayList<>();
    public MovieAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Movies> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movies, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        Movies movies = getMovieList().get(i);
        movieViewHolder.tv_title.setText(movies.getTitle());
        movieViewHolder.tv_overview.setText(movies.getOverview());
        movieViewHolder.tv_releaseDate.setText(movies.getReleaseDate());
        movieViewHolder.rating.setRating((float) (movies.getVoteAverage() / 2));

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185/"+movieList.get(i).getPoster())
                .into(movieViewHolder.img_poster);
    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView tv_title, tv_releaseDate, tv_overview;
        RatingBar rating;

        MovieViewHolder(View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.poster);
            tv_title = itemView.findViewById(R.id.title);
            tv_releaseDate = itemView.findViewById(R.id.release_date);
            tv_overview = itemView.findViewById(R.id.overview);
            rating = itemView.findViewById(R.id.voteAverage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("movies", movieList.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }
}