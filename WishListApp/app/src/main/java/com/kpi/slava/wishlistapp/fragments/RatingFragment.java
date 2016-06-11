package com.kpi.slava.wishlistapp.fragments;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.DBHelper;
import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.entities.BookEntity;
import com.kpi.slava.wishlistapp.entities.MovieEntity;

public class RatingFragment extends DialogFragment {

    public static final String TAG = "ControlMovieFragment";
    private final int LAYOUT = R.layout.fragment_rating;

    private String[] ratings = {"*", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private View view;

    private Spinner ratingSpinner;

    // if movie then true, else if book then false
    private boolean isMovie = false;

    private MovieEntity movie;

    private BookEntity book;

    private DBHelper dbHelper;

    private ArrayAdapter<String> ratingAdapter;

    private int id;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setTitle("Rate this book");

        view = inflater.inflate(LAYOUT, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.getString("Type").equals("movie")) isMovie = true;

            if(isMovie) {
                movie = bundle.getParcelable("Movie");
                getDialog().setTitle("Rate " + "\"" + movie.getTitle() + "\"");
                id = movie.getId();
            }
            else {
                book = bundle.getParcelable("Book");
                getDialog().setTitle("Rate " + "\"" + book.getTitle() + "\"");
                id = book.getId();
            }

        }

        ratingAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ratingSpinner = (Spinner) view.findViewById(R.id.spinner_rating);
        ratingSpinner.setAdapter(ratingAdapter);

        dbHelper = new DBHelper(getContext());

        view.findViewById(R.id.btn_rating_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ratingSpinner.getSelectedItemPosition() != 0){

                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    if(isMovie){
                        int updCount = database.update(DBHelper.TABLE_MOVIES, setMovieContentValues(),
                                DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)} );

                    }
                    else {
                        int updCount = database.update(DBHelper.TABLE_BOOKS, setBookContentValues(),
                                DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)} );
                    }

                } else Toast.makeText(getContext(), "Choose your mark", Toast.LENGTH_SHORT).show();
                dbHelper.close();
                dismiss();
            }
        });

        view.findViewById(R.id.btn_rating_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private ContentValues setBookContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, book.getTitle());
        contentValues.put(DBHelper.KEY_GENRE, book.getGenre());
        contentValues.put(DBHelper.KEY_AUTHOR, book.getAuthor());
        contentValues.put(DBHelper.KEY_READ, 1);
        contentValues.put(DBHelper.KEY_RATING, ratings[ratingSpinner.getSelectedItemPosition()]);
        contentValues.put(DBHelper.KEY_DATE, book.getDate());

        return contentValues;
    }

    private ContentValues setMovieContentValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, movie.getTitle());
        contentValues.put(DBHelper.KEY_GENRE, movie.getGenre());
        contentValues.put(DBHelper.KEY_RELEASE_YEAR, movie.getReleaseYear());
        contentValues.put(DBHelper.KEY_SEEN, 1);
        contentValues.put(DBHelper.KEY_RATING, ratings[ratingSpinner.getSelectedItemPosition()]);
        contentValues.put(DBHelper.KEY_DATE, movie.getDate());

        return contentValues;
    }

}
