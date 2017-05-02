package com.optimo_imasd.android.moviemanager.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optimo_imasd.android.moviemanager.R;
//import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.fragments.NotificationsDialogClass;
import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.models.Question;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    Question movie;
   //@BindView(R.id.ivMovieBackDrop)
    //ImageView ivMovieBackDrop;
   @BindView(R.id.tvTitle)
   TextView tvTitle;
    @BindView(R.id.tvOverview)
    TextView tvOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movie = (Question) extras.getSerializable("MOVIE");
            tvTitle.setText(movie.getQuestion());
            tvOverview.setText(movie.getAnswer());
       /* String jsonMyObject="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("MOVIE");
        }
        Question myObject = new Gson().fromJson(jsonMyObject, Question.class);
*/


           /* Picasso.with(this)
                    .load(movie.getBackdropPath())
                    .into(ivMovieBackDrop);
*/

        }


    }

}
