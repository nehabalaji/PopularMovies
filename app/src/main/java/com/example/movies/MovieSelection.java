package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieSelection extends AppCompatActivity {

    public static final String EXTRA_PARCEL = "com.example.android.popularMovies_stage1.parcel";
    public static final String EXTRA_ID = "com.example.android.popularMovies_stage1.id";

    RecyclerView mRecyclerView;
RecyclerView.LayoutManager layoutManager;
MovieAdapter mAdapter;
ArrayList<Movies> MovieList = new ArrayList<>();
ProgressBar mProgressBar;
TextView mErrorMessage;
int mPageNumber;
int mMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mProgressBar = findViewById(R.id.pb_loading_data);
        mErrorMessage = findViewById(R.id.tv_error_loading_message);
        mAdapter = new MovieAdapter(MovieList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    if(!mRecyclerView.canScrollVertically(1)){
                        new FetchMoviesTask().execute();
                    }
                }
            }
        });

        mPageNumber = 1;
        mMethod = 0;
        new FetchMoviesTask().execute();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_options:
                showPopUp();
                return true;

            case R.id.refresh:
                mPageNumber = 1;
                new FetchMoviesTask().execute();
                return true;

            default:
                super.onOptionsItemSelected(item);
                return true;
        }
    }

    private void showPopUp(){
        View menu = findViewById(R.id.sort_options);
        PopupMenu popupMenu = new PopupMenu(this, menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sort_movie_popular:
                        MovieList.clear();
                        mPageNumber = 1;
                        mMethod = 0;
                        new FetchMoviesTask().execute();
                        return true;
                    case R.id.sort_top_rated:
                        MovieList.clear();
                        mPageNumber=1;
                        mMethod = 1;
                        new FetchMoviesTask().execute();
                        return true;
                    default: return true;
                }
            }
        });

        popupMenu.inflate(R.menu.sort);
        popupMenu.show();


    }

    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movies>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movies> doInBackground(Void... voids) {
            return new MovieFetch().fetchMovies(mMethod, mPageNumber);
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            mProgressBar.setVisibility(View.INVISIBLE);

            if(movies.size() != 0){
                mRecyclerView.setVisibility(View.VISIBLE);
                mErrorMessage.setVisibility(View.INVISIBLE);
                MovieList.addAll(movies);
                mAdapter.notifyDataSetChanged();
                mPageNumber++;
            }
            else{
                mRecyclerView.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        }
    }
}
