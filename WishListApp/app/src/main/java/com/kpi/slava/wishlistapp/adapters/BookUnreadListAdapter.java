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

import com.kpi.slava.wishlistapp.database.DBHelper;
import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.database.BookEntity;
import com.kpi.slava.wishlistapp.fragments.ControlBookFragment;
import com.kpi.slava.wishlistapp.fragments.RatingFragment;

import java.util.ArrayList;
import java.util.List;


public class BookUnreadListAdapter extends RecyclerView.Adapter<BookUnreadListAdapter.ViewHolder>{

    List<BookEntity> unreadBookList = new ArrayList<BookEntity>();
    Context context;
    DBHelper dbHelper;

    public BookUnreadListAdapter(List<BookEntity> unreadBookList, Context context) {
        this.unreadBookList= unreadBookList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_unread_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(unreadBookList.get(position).getTitle());
        holder.genre.setText(unreadBookList.get(position).getGenre());
        holder.author.setText(unreadBookList.get(position).getAuthor());
        holder.date.setText(unreadBookList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return unreadBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, genre, author, date;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_unread_book_title);
            genre = (TextView) itemView.findViewById(R.id.tv_unread_book_genre);
            author = (TextView) itemView.findViewById(R.id.tv_unread_book_author);
            date = (TextView) itemView.findViewById(R.id.tv_unread_book_date);

            itemView.findViewById(R.id.btn_unread_book_delete).setOnClickListener(this);
            itemView.findViewById(R.id.btn_unread_book_edit).setOnClickListener(this);
            itemView.findViewById(R.id.btn_unread_book_read).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = unreadBookList.get(getAdapterPosition()).getId();
            switch (v.getId()){
                case (R.id.btn_unread_book_delete) :

                    dbHelper = new DBHelper(context);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    int DelCount = database.delete(DBHelper.TABLE_BOOKS, DBHelper.KEY_ID + "=" + id, null);

                    unreadBookList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), unreadBookList.size());

                    if(DelCount>0) Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

                    dbHelper.close();

                    break;

                case (R.id.btn_unread_book_edit) :

                    ControlBookFragment editBookFragment = new ControlBookFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Book", unreadBookList.get(getAdapterPosition()));

                    editBookFragment.setArguments(bundle);

                    editBookFragment.show(((FragmentActivity) context).getSupportFragmentManager(), ControlBookFragment.TAG);

                    break;

                case (R.id.btn_unread_book_read) :

                    RatingFragment ratingFragment = new RatingFragment();

                    Bundle ratingBookBundle = new Bundle();
                    ratingBookBundle.putString("Type", "book");
                    ratingBookBundle.putParcelable("Book", unreadBookList.get(getAdapterPosition()));

                    ratingFragment.setArguments(ratingBookBundle);

                    ratingFragment.show(((FragmentActivity) context).getSupportFragmentManager(), RatingFragment.TAG);

                    break;
            }
        }
    }
}