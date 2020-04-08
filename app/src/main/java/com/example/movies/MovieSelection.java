package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieSelection extends AppCompatActivity {

    public static final String EXTRA_PARCEL = "com.example.android.popularMovies_stage1.parcel";
    public static final String EXTRA_ID = "com.example.android.popularMovies_stage1.id";

    RecyclerView mRecyclerView;
RecyclerView.LayoutManager layoutManager;
MovieAdapter mAdapter;
ArrayList<Movies> MovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    private class MovieAdapter extends RecyclerView.Adapter<mMovieHolder>{
        public ArrayList<Movies> moviesArrayList;

        public MovieAdapter(ArrayList<Movies> movies){
            moviesArrayList = movies;
        }

        @NonNull
        @Override
        public mMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view= inflater.inflate(R.layout.movie_list, parent, false);
            return new mMovieHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull mMovieHolder holder, int position) {
            Movies currentMovie = moviesArrayList.get(position);
            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w185/" + currentMovie.getPosterPath())
                    .into(holder.movieView);
        }

        @Override
        public int getItemCount() {
            return moviesArrayList.size();
        }
    }

    private class mMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieView;
        public mMovieHolder(@NonNull View itemView) {
            super(itemView);
            movieView = itemView.findViewById(R.id.moviePosterImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movies clickedMovie = MovieList.get(mRecyclerView.getChildAdapterPosition(v));
            Intent movieDetailsIntent = new Intent(getApplicationContext(), movieDetail.class);
            movieDetailsIntent.putParcelableArrayListExtra(EXTRA_PARCEL, MovieList);
            movieDetailsIntent.putExtra(EXTRA_ID, clickedMovie.getId());
            startActivity(movieDetailsIntent);
        }
    }
}
