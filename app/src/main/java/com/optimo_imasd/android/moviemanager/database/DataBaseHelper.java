package com.optimo_imasd.android.moviemanager.database;

/**
 * Created by Jose Maria on 12/04/2017.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.optimo_imasd.android.moviemanager.models.Question;

/**
 * Created by Byron on 3/26/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questions.db";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";
    public static final String TABLE_QUESTIONS = "questions";
    private static final int DATABASE_VERSION = 2;
    public static final String[] ALL_COLUMNS =
            {COLUMN_ID,COLUMN_QUESTION,COLUMN_ANSWER};

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "create table "
                + TABLE_QUESTIONS + "( "
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_QUESTION + " TEXT, "
                + COLUMN_ANSWER + " TEXT "
                + ")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

}

