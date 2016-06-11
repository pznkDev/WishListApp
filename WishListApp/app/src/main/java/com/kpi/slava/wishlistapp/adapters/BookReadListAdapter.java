package com.kpi.slava.wishlistapp.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.BookEntity;
import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.fragments.ControlBookFragment;

import java.util.ArrayList;
import java.util.List;


public class BookReadListAdapter extends RecyclerView.Adapter<BookReadListAdapter.ViewHolder> {

    List<BookEntity> readBookList = new ArrayList<BookEntity>();
    Context context;

    private DBHelper dbHelper;

    public BookReadListAdapter(List<BookEntity> readBookList, Context context) {
        this.readBookList = readBookList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_read_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(readBookList.get(position).getTitle());
        holder.genre.setText(readBookList.get(position).getGenre());
        holder.author.setText(readBookList.get(position).getAuthor());
        holder.date.setText(readBookList.get(position).getDate());
        holder.rating.setText(readBookList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return readBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, genre, author, date, rating;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_read_book_title);
            genre = (TextView) itemView.findViewById(R.id.tv_read_book_genre);
            author = (TextView) itemView.findViewById(R.id.tv_read_book_author);
            date = (TextView) itemView.findViewById(R.id.tv_read_book_date);
            rating = (TextView) itemView.findViewById(R.id.tv_read_book_rating);

            itemView.findViewById(R.id.btn_read_book_delete).setOnClickListener(this);
            itemView.findViewById(R.id.btn_read_book_edit).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int id = readBookList.get(getAdapterPosition()).getId();
            switch (v.getId()) {
                case (R.id.btn_read_book_delete):

                    dbHelper = new DBHelper(context);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    int DelCount = database.delete(DBHelper.TABLE_BOOKS, DBHelper.KEY_ID + "=" + id, null);

                    readBookList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), readBookList.size());

                    if (DelCount > 0)
                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

                    dbHelper.close();

                    break;

                case (R.id.btn_read_book_edit):

                    ControlBookFragment editBookFragment = new ControlBookFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Book", readBookList.get(getAdapterPosition()));

                    editBookFragment.setArguments(bundle);

                    editBookFragment.show(((FragmentActivity) context).getSupportFragmentManager(), ControlBookFragment.TAG);

                    break;
            }

        }
    }
}
