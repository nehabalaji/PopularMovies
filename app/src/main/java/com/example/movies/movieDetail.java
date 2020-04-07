package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class movieDetail extends AppCompatActivity {

    ImageView mMoviePoster;
    TextView mMovieTitle, mMovieOverview, mMovieReleaseDate, mMovieVoteResults;
    ArrayList<Movies> mMovieList;
    ViewPager mviewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent RecievedIntent = getIntent();
        if(RecievedIntent.hasExtra(MovieSelection.EXTRA_PARCEL)){
            mMovieList = RecievedIntent.getParcelableArrayListExtra(MovieSelection.EXTRA_PARCEL);

            for(int i = 0; i < mMovieList.size(); i++){
                Movies CurrentMovie = mMovieList.get(i);
            }
        }
        else{
            mMovieList = null;
        }
    }

}
