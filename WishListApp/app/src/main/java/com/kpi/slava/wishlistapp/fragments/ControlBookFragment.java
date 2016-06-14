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
import com.kpi.slava.wishlistapp.database.BookEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControlBookFragment extends DialogFragment {

    public static final String TAG = "ControlBookFragment";
    private final int LAYOUT = R.layout.fragment_book_control;

    private TextInputLayout tilTitle, tilAuthor;

    private EditText edtTitle, edtAuthor;

    private Spinner bookGenreSpinner;
    private Spinner bookRatingSpinner;

    private LinearLayout linLayoutBookRating;

    private RadioButton radioUnread, radioRead;

    private String[] genres = {"<--choose-->", "adventure", "biography", "fantasy", "history", "horror", "romance", "science"};
    private String[] ratings = {"<---->", "1", "2", "3", "4", "5"};

    private View view;

    public String title, genre, author, rating, date;
    private boolean isRead = false;

    // if create - true, if edit - false
    private boolean create = true;
    private int id ;

    private DBHelper dbHelper;

    private BookEntity bookEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("New book");

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        radioUnread = (RadioButton) view.findViewById(R.id.radio_book_unread);
        radioUnread.setChecked(true);
        radioRead = (RadioButton) view.findViewById(R.id.radio_book_read);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bookGenreSpinner = (Spinner) view.findViewById(R.id.spinner_book_genre);
        bookGenreSpinner.setAdapter(genreAdapter);

        tilTitle = (TextInputLayout) view.findViewById(R.id.til_enter_book_title);
        edtTitle = (EditText) tilTitle.findViewById(R.id.edt_enter_book_title);
        tilTitle.setHint("Enter book title*");

        tilAuthor = (TextInputLayout) view.findViewById(R.id.til_enter_book_author);
        edtAuthor = (EditText) tilAuthor.findViewById(R.id.edt_enter_book_author);
        tilAuthor.setHint("Enter book's author");

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bookRatingSpinner = (Spinner) view.findViewById(R.id.spinner_book_rating);
        bookRatingSpinner.setAdapter(ratingAdapter);

        linLayoutBookRating = (LinearLayout) view.findViewById(R.id.lin_layout_book_rating);

        linLayoutBookRating.setVisibility(View.GONE);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_book);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_book_unread) {
                    linLayoutBookRating.setVisibility(View.GONE);
                    isRead = false;
                } else {
                    linLayoutBookRating.setVisibility(View.VISIBLE);
                    isRead = true;
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            bookEntity = bundle.getParcelable("Book");
            id = bookEntity.getId();
            create = false;
            getDialog().setTitle("Edit  " + "\" " + bookEntity.getTitle() + " \"");
            radioGroup.setVisibility(View.GONE);

            if(bookEntity.getRead() == 0) isRead = false;
            else {
                isRead = true;
                linLayoutBookRating.setVisibility(View.VISIBLE);
            }
            loadData();
        }

        view.findViewById(R.id.btn_enter_book_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btn_enter_book_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all values for Movie-object
                title = edtTitle.getText().toString();

                if (bookGenreSpinner.getSelectedItemPosition() != 0)
                    genre = genres[bookGenreSpinner.getSelectedItemPosition()];
                else genre = "";

                author = edtAuthor.getText().toString();

                if (bookRatingSpinner.getSelectedItemPosition() != 0)
                    rating = ratings[bookRatingSpinner.getSelectedItemPosition()];
                else rating = "";

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                date = dateFormat.format(new Date());

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                if(!isRead){

                    if((!title.equals("")) && (!title.equals(" "))){

                        if(create){
                            database.insert(DBHelper.TABLE_BOOKS, null, setContentValues((byte) 0, ""));
                        }
                        else{
                            int updCount = database.update(DBHelper.TABLE_BOOKS, setContentValues((byte) 0, ""),
                                    DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)} );
                            if(updCount > 0) Toast.makeText(getContext(), "Successfully changed", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();

                    } else Toast.makeText(getContext(), "Enter title", Toast.LENGTH_SHORT).show();
                }
                else {
                    if((!title.equals("")) && (!title.equals(" ")) && (!rating.equals(""))){

                        if(create){
                            database.insert(DBHelper.TABLE_BOOKS, null, setContentValues((byte) 1, rating));
                        }
                        else{
                            int updCount = database.update(DBHelper.TABLE_BOOKS, setContentValues((byte) 1, rating),
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
        edtTitle.setText(bookEntity.getTitle());
        edtAuthor.setText(bookEntity.getAuthor());

        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(bookEntity.getGenre())) {
                bookGenreSpinner.setSelection(i);
                break;
            }
        }

        if(isRead){
            for(int i = 0; i<ratings.length; i++){
                if(ratings[i].equals(bookEntity.getRating())){
                    bookRatingSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private ContentValues setContentValues(byte read, String rating){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, title);
        contentValues.put(DBHelper.KEY_GENRE, genre);
        contentValues.put(DBHelper.KEY_AUTHOR, author);
        contentValues.put(DBHelper.KEY_READ, read);
        contentValues.put(DBHelper.KEY_RATING, rating);
        contentValues.put(DBHelper.KEY_DATE, date);

        return contentValues;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        edtTitle.setText("");
        edtAuthor.setText("");
        radioUnread.setChecked(true);
        bookRatingSpinner.setSelection(0);
        bookGenreSpinner.setSelection(0);
        super.onDismiss(dialog);
    }

}
