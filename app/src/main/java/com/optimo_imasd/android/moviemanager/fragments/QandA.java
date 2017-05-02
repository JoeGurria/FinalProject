package com.optimo_imasd.android.moviemanager.fragments;

import com.optimo_imasd.android.moviemanager.adapters.MovieRecyclerViewAdapter;
import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.models.Question;

import java.util.List;

/**
 * Created by Jose Maria on 11/04/2017.
 */

public class QandA {
public  String answer;
    public String question;
    public List<Question> movies;
    public MovieRecyclerViewAdapter adapter;

        public QandA(String sQuestion, String sAnswer, List<Question> movies, MovieRecyclerViewAdapter _adapter){
            this.answer=sAnswer;
            this.question=sQuestion;
            this.movies=movies;
            this.adapter=_adapter;

        }
        public String getAnswer(){

            return this.answer;
        }

    public String getQuestion(){
        return this.question;
    }
}
