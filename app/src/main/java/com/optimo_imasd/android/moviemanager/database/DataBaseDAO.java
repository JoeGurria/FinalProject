package com.optimo_imasd.android.moviemanager.database;

/**
 * Created by Jose Maria on 12/04/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.optimo_imasd.android.moviemanager.models.Question;

import static com.optimo_imasd.android.moviemanager.database.DataBaseHelper.COLUMN_QUESTION;
import static com.optimo_imasd.android.moviemanager.database.DataBaseHelper.COLUMN_ANSWER;
import static com.optimo_imasd.android.moviemanager.database.DataBaseHelper.TABLE_QUESTIONS;

/**
 * Created by Byron on 3/26/2017.
 */

public class DataBaseDAO {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBaseDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }

    //Insert a new contact on data base
    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question.getQuestion());
        values.put(COLUMN_ANSWER, question.getAnswer());

        database.insert(TABLE_QUESTIONS, null, values);
    }

    //Get all contacts from data base
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> contactArrayList = new ArrayList<Question>();

        Cursor cursor = database.rawQuery("SELECT  * FROM " + TABLE_QUESTIONS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question= new Question();
            question.setQuestion(cursor.getString(1));
            question.setAnswer(cursor.getString(2));
            contactArrayList.add(question);

            cursor.moveToNext();
        }

        cursor.close();
        return contactArrayList;
    }

}
