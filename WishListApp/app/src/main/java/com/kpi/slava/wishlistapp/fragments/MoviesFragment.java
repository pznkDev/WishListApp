package com.kpi.slava.wishlistapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.adapters.MovieUnseenListAdapter;
import com.kpi.slava.wishlistapp.entities.MovieEntity;

import java.util.ArrayList;
import java.util.List;


public class MoviesFragment extends Fragment {

    public static final String TAG = "MoviesFragment";
    private final int LAYOUT = R.layout.fragment_movies;

    private View view;

    private RecyclerView rvMovieUnseen;

    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

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

        return unseenMovieList;
    }

}