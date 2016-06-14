package com.kpi.slava.wishlistapp.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.MovieEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControlMovieFragment extends DialogFragment{

    public static final String TAG = "ControlMovieFragment";
    private final int LAYOUT = R.layout.fragment_movie_control;

    private TextInputLayout tilTitle, tilReleaseYear;

    private EditText edtTitle, edtReleaseYear;

    private Spinner movieGenreSpinner;
    private Spinner movieRatingSpinner;

    private LinearLayout linLayoutMovieRating;

    private RadioButton radioUnseen, radioSeen;

    private String[] genres = {"<--choose-->", "adventure", "action", "fantasy", "horror"};
    private String[] ratings = {"rate", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private View view;

    public String title, genre, releaseYear, rating, date;
    private boolean isSeen = false;

    // if create - true, if edit - false
    private boolean create = true;
    private int id ;

    private DBHelper dbHelper;

    private MovieEntity movieBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("New movie");

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        radioUnseen = (RadioButton) view.findViewById(R.id.radio_movie_unseen);
        radioUnseen.setChecked(true);
        radioSeen = (RadioButton) view.findViewById(R.id.radio_movie_seen);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        movieGenreSpinner = (Spinner) view.findViewById(R.id.spinner_movie_genre);
        movieGenreSpinner.setAdapter(genreAdapter);

        tilTitle = (TextInputLayout) view.findViewById(R.id.til_enter_movie_title);
        edtTitle = (EditText) tilTitle.findViewById(R.id.edt_enter_movie_title);
        tilTitle.setHint("Enter movie title*");

        tilReleaseYear = (TextInputLayout) view.findViewById(R.id.til_enter_movie_release_year);
        edtReleaseYear = (EditText) tilReleaseYear.findViewById(R.id.edt_enter_movie_release_year);
        tilReleaseYear.setHint("Enter release year");

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        movieRatingSpinner = (Spinner) view.findViewById(R.id.spinner_movie_rating);
        movieRatingSpinner.setAdapter(ratingAdapter);

        linLayoutMovieRating = (LinearLayout) view.findViewById(R.id.lin_layout_movie_rating);

        linLayoutMovieRating.setVisibility(View.GONE);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_movie);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_movie_unseen) {
                    linLayoutMovieRating.setVisibility(View.GONE);
                    isSeen = false;
                } else {
                    linLayoutMovieRating.setVisibility(View.VISIBLE);
                    isSeen = true;
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            movieBundle = bundle.getParcelable("Movie");
            id = movieBundle.getId();
            create = false;
            getDialog().setTitle("Edit  " + "\" " + movieBundle.getTitle() + " \"");
            radioGroup.setVisibility(View.GONE);

            if(movieBundle.getSeen() == 0) isSeen = false;
            else {
                isSeen = true;
                linLayoutMovieRating.setVisibility(View.VISIBLE);
            }
            loadData();
        }

        view.findViewById(R.id.btn_enter_movie_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btn_enter_movie_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all values for Movie-object
                title = edtTitle.getText().toString();

                if (movieGenreSpinner.getSelectedItemPosition() != 0)
                    genre = genres[movieGenreSpinner.getSelectedItemPosition()];
                else genre = "";

                releaseYear = edtReleaseYear.getText().toString();

                if (movieRatingSpinner.getSelectedItemPosition() != 0)
                    rating = ratings[movieRatingSpinner.getSelectedItemPosition()];
                else rating = "";

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                date = dateFormat.format(new Date());

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                if(!isSeen){

                    if((!title.equals("")) && (!title.equals(" "))){

                        if(create){
                            database.insert(DBHelper.TABLE_MOVIES, null, setContentValues((byte) 0, ""));
                        }
                        else{
                            int updCount = database.update(DBHelper.TABLE_MOVIES, setContentValues((byte) 0, ""),
                                    DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)} );
                            if(updCount > 0) Toast.makeText(getContext(), "Successfully changed", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();

                    } else Toast.makeText(getContext(), "Enter title", Toast.LENGTH_SHORT).show();
                }
                else {
                    if((!title.equals("")) && (!title.equals(" ")) && (!rating.equals(""))){

                        if(create){
                            database.insert(DBHelper.TABLE_MOVIES, null, setContentValues((byte) 1, rating));
                        }
                        else{
                            int updCount = database.update(DBHelper.TABLE_MOVIES, setContentValues((byte) 1, rating),
                                    DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)} );
                            if(updCount > 0) Toast.makeText(getContext(), "Successfully changed", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }
                    else Toast.makeText(getContext(), "Enter title and rating", Toast.LENGTH_SHORT).show();
                }

                dbHelper.close();

            }
        });
        return view;
    }

    private void loadData() {
        edtTitle.setText(movieBundle.getTitle());
        edtReleaseYear.setText(movieBundle.getReleaseYear());

        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(movieBundle.getGenre())) {
                movieGenreSpinner.setSelection(i);
                break;
            }
        }

        if(isSeen){
            for(int i = 0; i<ratings.length; i++){
                if(ratings[i].equals(movieBundle.getRating())){
                    movieRatingSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private ContentValues setContentValues(byte seen, String rating){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, title);
        contentValues.put(DBHelper.KEY_GENRE, genre);
        contentValues.put(DBHelper.KEY_RELEASE_YEAR, releaseYear);
        contentValues.put(DBHelper.KEY_SEEN, seen);
        contentValues.put(DBHelper.KEY_RATING, rating);
        contentValues.put(DBHelper.KEY_DATE, date);

        return contentValues;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        edtTitle.setText("");
        edtReleaseYear.setText("");
        radioUnseen.setChecked(true);
        movieRatingSpinner.setSelection(0);
        movieGenreSpinner.setSelection(0);
        super.onDismiss(dialog);
    }

}
