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
import com.kpi.slava.wishlistapp.adapters.BookUnreadListAdapter;
import com.kpi.slava.wishlistapp.database.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    public static final String TAG = "BooksFragment";
    private final int LAYOUT = R.layout.fragment_books;

    private View view;

    private RecyclerView rvBookUnread;

    private SwipeRefreshLayout swipeRefresh;

    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        rvBookUnread = (RecyclerView) view.findViewById(R.id.recycler_view_book_unread_list);
        rvBookUnread.setLayoutManager(new LinearLayoutManager(getContext()));

        rvBookUnread.setAdapter(new BookUnreadListAdapter(getUnreadBooks(), getContext()));

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_swipe_unread_book);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rvBookUnread.setAdapter(new BookUnreadListAdapter(getUnreadBooks(), getContext()));
                rvBookUnread.getAdapter().notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private List<BookEntity> getUnreadBooks(){
        List<BookEntity> unreadBookList = new ArrayList<BookEntity>();

        // load all unread books

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_BOOKS + " WHERE " + DBHelper.KEY_READ
                + " = '0'", null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int genreIndex = cursor.getColumnIndex(DBHelper.KEY_GENRE);
            int authorIndex= cursor.getColumnIndex(DBHelper.KEY_AUTHOR);
            int readIndex = cursor.getColumnIndex(DBHelper.KEY_READ);
            int ratingIndex = cursor.getColumnIndex(DBHelper.KEY_RATING);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);

            do {
                unreadBookList.add(new BookEntity(cursor.getInt(id), cursor.getString(titleIndex),
                        cursor.getString(genreIndex), cursor.getString(authorIndex),
                        cursor.getString(ratingIndex), cursor.getString(dateIndex), (byte) cursor.getInt(readIndex)));
                System.out.println(cursor.getString(titleIndex) + "   " + cursor.getString(authorIndex));
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbHelper.close();

        return unreadBookList;
    }

}