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

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.adapters.NoteListAdapter;
import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.database.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private final int LAYOUT = R.layout.fragment_notes;

    private View view;

    private RecyclerView rvNotes;

    private SwipeRefreshLayout swipeRefresh;

    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        dbHelper = new DBHelper(getContext());

        rvNotes = (RecyclerView) view.findViewById(R.id.recycler_view_note_list);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));

        rvNotes.setAdapter(new NoteListAdapter(getNotes(), getContext()));

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_swipe_notes);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rvNotes.setAdapter(new NoteListAdapter(getNotes(), getContext()));
                rvNotes.getAdapter().notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private List<NoteEntity> getNotes(){
        List<NoteEntity> noteList= new ArrayList<NoteEntity>();

        // load all notes

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_NOTES, null);

        if(cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);

            do {
                noteList.add(new NoteEntity(cursor.getInt(id), cursor.getString(titleIndex),
                        cursor.getString(textIndex), cursor.getString(dateIndex)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbHelper.close();

        return noteList;
    }

}
