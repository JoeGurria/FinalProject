package com.optimo_imasd.android.moviemanager.models;

import java.io.Serializable;

/**
 * Created by Jose Maria on 12/04/2017.
 */

public class Question  implements Serializable {

    private String question;
    private String answer;

    public Question() {
    }

    public Question(String question, String answer) {
        this.question= question;
        this.answer= answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question= question ;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer= answer;
    }
}
