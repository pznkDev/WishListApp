package com.kpi.slava.wishlistapp.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.DBHelper;

public class SettingsFragment extends Fragment{

    public final static String TAG = "SettingsFragment";

    private final int LAYOUT = R.layout.fragment_settings;

    private View view;

    private DBHelper dbHelper;

    SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        view.findViewById(R.id.btn_delete_all_movies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_MOVIES, null, null);
                database.close();

                Toast.makeText(getContext(), "All movies have been deleted", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.btn_delete_all_books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_BOOKS, null, null);
                database.close();

                Toast.makeText(getContext(), "All books have been deleted", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.btn_delete_all_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_NOTES, null, null);
                database.close();

                Toast.makeText(getContext(), "All notes have been deleted", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
