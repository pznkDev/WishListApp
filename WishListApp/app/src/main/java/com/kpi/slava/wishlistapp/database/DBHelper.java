package com.kpi.slava.wishlistapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "mediaDb";
    public static final String TABLE_MOVIES = "movies";
    public static final String TABLE_BOOKS = "books";
    public static final String TABLE_NOTES = "notes";
    //movie
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_RELEASE_YEAR = "releaseYear";
    public static final String KEY_SEEN = "seen";
    public static final String KEY_RATING = "rating";
    public static final String KEY_DATE = "date";
    //book
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_READ = "read";
    //notes
    public static final String KEY_TEXT = "text";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates movies db
        db.execSQL("create table " + TABLE_MOVIES + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text," + KEY_GENRE
                + " text," + KEY_RELEASE_YEAR + " text," + KEY_SEEN
                + " numeric," + KEY_RATING + " text," + KEY_DATE
                + " text" + ")");

        //creates books db
        db.execSQL("create table " + TABLE_BOOKS + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text," + KEY_GENRE
                + " text," + KEY_AUTHOR + " text," + KEY_READ
                + " numeric," + KEY_RATING + " text," + KEY_DATE
                + " text" + ")");

        //creates notes db
        db.execSQL("create table " + TABLE_NOTES + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text," + KEY_TEXT
                + " text," + KEY_DATE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MOVIES);
        db.execSQL("drop table if exists " + TABLE_BOOKS);
        db.execSQL("drop table if exists " + TABLE_NOTES);

        onCreate(db);
    }
}
