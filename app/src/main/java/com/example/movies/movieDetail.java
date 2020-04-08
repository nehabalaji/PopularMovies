package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        mviewPager = findViewById(R.id.ViewPager);
        mviewPager.setAdapter(new MoviePagerAdapter(this));

        int recievedMovieId = 0;
        if(RecievedIntent.hasExtra(MovieSelection.EXTRA_ID)){
            recievedMovieId = RecievedIntent.getIntExtra(MovieSelection.EXTRA_ID, 0);
        }
        for(int i=0; i<mMovieList.size(); i++){
            if(mMovieList.get(i).getId() == recievedMovieId){
                mviewPager.setCurrentItem(i);
                break;
            }
        }
    }

    private class MoviePagerAdapter extends PagerAdapter {

        private Context mContext;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_movie_detail, container, false);

            mMoviePoster = layout.findViewById(R.id.moviePosterImg);
            mMovieTitle = layout.findViewById(R.id.tv_movie_title);
            mMovieOverview = layout.findViewById(R.id.tv_movie_overview);
            mMovieReleaseDate =  layout.findViewById(R.id.tv_movie_release_date);
            mMovieVoteResults =  layout.findViewById(R.id.tv_movie_vote_results);

            Movies recievedMovies = null;
            if(mMovieList.size() !=0 && mMovieList !=null){
                recievedMovies = mMovieList.get(position);

                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185/" + recievedMovies.getPosterPath())
                        .into(mMoviePoster);

                mMovieTitle.setText(recievedMovies.getTitle());
                mMovieOverview.setText(recievedMovies.getOverview());

                String formattedVoteCount = NumberFormat.getIntegerInstance().format(recievedMovies.getVoteCount());
                mMovieVoteResults.setText(Float.toString(recievedMovies.getAvg()) + "/10" + " (" + formattedVoteCount + " )");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = null;
                try {
                    Date MovieReleasedDate = simpleDateFormat.parse(recievedMovies.getReleaseDate());
                    formattedDate = new SimpleDateFormat("MMMM dd, yyyy").format(MovieReleasedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mMovieReleaseDate.setText("Release Date: " + formattedDate);
            }
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public MoviePagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mMovieList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
