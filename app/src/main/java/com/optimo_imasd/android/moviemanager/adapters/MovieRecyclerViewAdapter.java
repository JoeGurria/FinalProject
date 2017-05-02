package com.optimo_imasd.android.moviemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.optimo_imasd.android.moviemanager.R;
import com.optimo_imasd.android.moviemanager.activities.MovieDetailActivity;
import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.models.Question;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jose Maria on 14/03/2017.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    List<Question> movies;
    Context context;

public MovieRecyclerViewAdapter(Context context, List<Question> movies){

    this.movies=movies;
    this.context=context;

}

private Context getContext(){

    return context;
}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Question movie=movies.get(position);
        holder.tvTitle.setText(movie.getQuestion());
        //holder.tvOverview.setText(movie.getOverview());
       // Picasso.with(getContext())
            //    .load(movie.getPosterPath())
             //   .into(holder.ivMovieImage);




    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       // @BindView(R.id.ivMovieImage)
       // ImageView ivMovieImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.cvMovie)
        CardView cvMovie;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Question movie=movies.get(getAdapterPosition());

            Intent intent=new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("MOVIE", movie);
            getContext().startActivity(intent);

            /*Intent activity = new Intent(getContext(),MovieDetailActivity.class);
            activity.putExtra("MOVIE", new Gson().toJson(movie));
            getContext().startActivity(activity);
*/
        }
    }
}
