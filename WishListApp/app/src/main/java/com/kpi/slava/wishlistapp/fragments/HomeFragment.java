package com.kpi.slava.wishlistapp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.DBHelper;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private final int LAYOUT = R.layout.fragment_home;

    private View view;

    DBHelper dbHelper;


    private TextView tvSeen, tvUnseen, tvRead, tvUnread, tvNotes;

    private final String SEEN = "Seen : ";
    private final String UNSEEN = "Unseen : ";
    private final String READ = "Read : ";
    private final String UNREAD = "Unread : ";
    private final String NOTE = "Notes : ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        tvSeen = (TextView) view.findViewById(R.id.tv_main_seen_movie_count);
        tvUnseen = (TextView) view.findViewById(R.id.tv_main_unseen_movie_count);
        tvRead = (TextView) view.findViewById(R.id.tv_main_read_book_count);
        tvUnread = (TextView) view.findViewById(R.id.tv_main_unread_book_count);
        tvNotes = (TextView) view.findViewById(R.id.tv_main_note_count);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor;

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_MOVIES + " WHERE " + DBHelper.KEY_SEEN
                + " = '0'", null);
        tvUnseen.setText(UNSEEN + cursor.getCount());

        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_MOVIES + " WHERE " + DBHelper.KEY_SEEN
                + " = '1'", null);
        tvSeen.setText(SEEN + cursor.getCount());


        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKS + " WHERE " + DBHelper.KEY_READ
                + " = '0'", null);
        tvUnread.setText(UNREAD + cursor.getCount());

        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKS + " WHERE " + DBHelper.KEY_READ
                + " = '1'", null);
        tvRead.setText(READ + cursor.getCount());

        cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_NOTES, null);
        tvNotes.setText(NOTE + cursor.getCount());

        cursor.close();
        dbHelper.close();
    }

}