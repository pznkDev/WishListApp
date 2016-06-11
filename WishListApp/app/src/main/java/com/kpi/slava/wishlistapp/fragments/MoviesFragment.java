package com.kpi.slava.wishlistapp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.adapters.MovieUnseenListAdapter;
import com.kpi.slava.wishlistapp.database.MovieEntity;

import java.util.ArrayList;
import java.util.List;


public class MoviesFragment extends Fragment {

    public static final String TAG = "MoviesFragment";
    private final int LAYOUT = R.layout.fragment_movies;

    private View view;

    private RecyclerView rvMovieUnseen;

    private SwipeRefreshLayout swipeRefresh;

    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        rvMovieUnseen = (RecyclerView) view.findViewById(R.id.recycler_view_movie_unseen_list);
        rvMovieUnseen.setLayoutManager(new LinearLayoutManager(getContext()));

        rvMovieUnseen.setAdapter(new MovieUnseenListAdapter(getUnseenMovies(), getContext()));

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_swipe_unseen_movie);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rvMovieUnseen.setAdapter(new MovieUnseenListAdapter(getUnseenMovies(), getContext()));
                rvMovieUnseen.getAdapter().notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private List<MovieEntity> getUnseenMovies(){
        List<MovieEntity> unseenMovieList = new ArrayList<MovieEntity>();

        // load all unseen movies

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_MOVIES + " WHERE " + DBHelper.KEY_SEEN
                + " = '0'", null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int genreIndex = cursor.getColumnIndex(DBHelper.KEY_GENRE);
            int relYearIndex = cursor.getColumnIndex(DBHelper.KEY_RELEASE_YEAR);
            int seenIndex = cursor.getColumnIndex(DBHelper.KEY_SEEN);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RATING);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);

            do {
                unseenMovieList.add(new MovieEntity(cursor.getInt(id), cursor.getString(titleIndex),
                        cursor.getString(genreIndex), cursor.getString(relYearIndex),
                        cursor.getString(dateIndex), cursor.getString(ratingIndex), (byte) cursor.getInt(seenIndex)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbHelper.close();

        return unseenMovieList;
    }

}