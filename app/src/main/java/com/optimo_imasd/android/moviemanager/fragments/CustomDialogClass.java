package com.optimo_imasd.android.moviemanager.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.optimo_imasd.android.moviemanager.R;
import com.optimo_imasd.android.moviemanager.adapters.MovieRecyclerViewAdapter;
import com.optimo_imasd.android.moviemanager.database.DataBaseDAO;
import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.models.Question;

import java.util.List;

/**
 * Created by Jose Maria on 11/04/2017.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public EditText question;
    public EditText answer;
    public QandA qa;

    public CustomDialogClass(Activity a, QandA _qa) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.qa=_qa;
    }
public QandA getQA(){

    return qa;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.save_question_and_answer);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        question=(EditText) findViewById(R.id.txtQuestion);
        answer=(EditText) findViewById(R.id.txtAnswer);
        /*qa.question=question.toString();
        qa.answer=answer.toString();
        qa.movies=movies;*/
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }
    private void SaveToDatabase(QandA _qa) {
       int count=_qa.movies.size()+1;
       // _qa.movies.add(new Movie(String.valueOf(count), _qa.question, _qa.answer, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
      /*  _qa.movies.add(new Question(_qa.question, _qa.answer));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
         qa=_qa;
        msgbox(qa.question, qa.answer + ". movies.size()=" + qa.movies.size());*/
        _qa.movies.add(new Question(_qa.question, _qa.answer));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
        qa=_qa;
       Question q = new Question();
        q.setQuestion(_qa.question);
        q.setAnswer(_qa.answer);
        DataBaseDAO dao = new DataBaseDAO(getContext());
        dao.open();
        dao.addQuestion(q);
        dao.close();

    }
    public void msgbox(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //c.finish();
                qa.question=question.getText().toString();
                qa.answer=answer.getText().toString();
                SaveToDatabase(qa);
                qa.adapter.notifyDataSetChanged();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}