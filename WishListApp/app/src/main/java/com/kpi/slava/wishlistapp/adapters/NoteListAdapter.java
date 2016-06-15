package com.kpi.slava.wishlistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.database.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{

    List<NoteEntity> noteList= new ArrayList<NoteEntity>();
    Context context;
    DBHelper dbHelper;

    public NoteListAdapter(List<NoteEntity> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, int position) {
        holder.title.setText(noteList.get(position).getTitle());
        holder.text.setText(noteList.get(position).getText());
        holder.date.setText(noteList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, text, date;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_note_title);
            text = (TextView) itemView.findViewById(R.id.tv_note_text);
            date = (TextView) itemView.findViewById(R.id.tv_note_date);

            itemView.findViewById(R.id.btn_note_delete).setOnClickListener(this);
            itemView.findViewById(R.id.btn_note_edit).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case (R.id.btn_note_delete) :

                    break;

                case (R.id.btn_note_edit) :

                    break;

            }

        }
    }
}
