package com.kpi.slava.wishlistapp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.adapters.BookReadListAdapter;
import com.kpi.slava.wishlistapp.adapters.MovieSeenListAdapter;
import com.kpi.slava.wishlistapp.database.BookEntity;
import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.database.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    public static String TAG = "LibraryFragment";

    private final int LAYOUT = R.layout.fragment_library;

    private View view;

    private Toolbar toolbar;

    String[] types = {"Movies", "Books"};
    Spinner spinner;

    boolean isMovie = true;

    private RecyclerView recyclerView;

    DBHelper dbHelper;

    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, types);
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_navigation);
        spinner.setAdapter(typeAdapter);
        spinner.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_library);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    //movies
                    case(0):
                        isMovie = true;
                        recyclerView.setAdapter(new MovieSeenListAdapter(loadSeenMovies(), getContext()));
                        break;
                    //books
                    case(1):
                        isMovie = false;
                        recyclerView.setAdapter(new BookReadListAdapter(loadReadBooks(), getContext()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_swipe_library);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(isMovie) recyclerView.setAdapter(new MovieSeenListAdapter(loadSeenMovies(), getContext()));
                else recyclerView.setAdapter(new BookReadListAdapter(loadReadBooks(), getContext()));

                recyclerView.getAdapter().notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private List<MovieEntity> loadSeenMovies() {

        List<MovieEntity> seenMovieList = new ArrayList<MovieEntity>();

        // load all seen movies

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_MOVIES + " WHERE " + DBHelper.KEY_SEEN
                + " = '1'", null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int genreIndex = cursor.getColumnIndex(DBHelper.KEY_GENRE);
            int relYearIndex = cursor.getColumnIndex(DBHelper.KEY_RELEASE_YEAR);
            int seenIndex = cursor.getColumnIndex(DBHelper.KEY_SEEN);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RATING);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);

            do {
                seenMovieList.add(new MovieEntity(cursor.getInt(id), cursor.getString(titleIndex),
                        cursor.getString(genreIndex), cursor.getString(relYearIndex),
                        cursor.getString(dateIndex), cursor.getString(ratingIndex), (byte) cursor.getInt(seenIndex)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbHelper.close();

        return seenMovieList;
    }

    private List<BookEntity> loadReadBooks() {
        List<BookEntity> readBookList = new ArrayList<BookEntity>();

        // load all read books
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKS + " WHERE " + DBHelper.KEY_READ
                + " = '1'", null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int genreIndex = cursor.getColumnIndex(DBHelper.KEY_GENRE);
            int authorIndex = cursor.getColumnIndex(DBHelper.KEY_AUTHOR);
            int readIndex = cursor.getColumnIndex(DBHelper.KEY_READ);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RATING);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);

            do {
                readBookList.add(new BookEntity(cursor.getInt(id), cursor.getString(titleIndex),
                        cursor.getString(genreIndex), cursor.getString(authorIndex),
                        cursor.getString(ratingIndex), cursor.getString(dateIndex), (byte) cursor.getInt(readIndex)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbHelper.close();

        return readBookList;
    }

}
