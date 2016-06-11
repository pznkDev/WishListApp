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
import com.kpi.slava.wishlistapp.database.MovieEntity;
import com.kpi.slava.wishlistapp.fragments.ControlMovieFragment;

import java.util.ArrayList;
import java.util.List;

public class MovieSeenListAdapter extends RecyclerView.Adapter<MovieSeenListAdapter.ViewHolder> {

    List<MovieEntity> seenMovieList = new ArrayList<MovieEntity>();
    Context context;

    private DBHelper dbHelper;

    public MovieSeenListAdapter(List<MovieEntity> seenMovieList, Context context) {
        this.seenMovieList = seenMovieList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_seen_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(seenMovieList.get(position).getTitle());
        holder.genre.setText(seenMovieList.get(position).getGenre());
        holder.release.setText(seenMovieList.get(position).getReleaseYear());
        holder.date.setText(seenMovieList.get(position).getDate());
        holder.rating.setText(seenMovieList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return seenMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, genre, release, date, rating;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_seen_movie_title);
            genre = (TextView) itemView.findViewById(R.id.tv_seen_movie_genre);
            release = (TextView) itemView.findViewById(R.id.tv_seen_movie_release_year);
            date = (TextView) itemView.findViewById(R.id.tv_seen_movie_date);
            rating = (TextView) itemView.findViewById(R.id.tv_seen_movie_rating);

            itemView.findViewById(R.id.btn_seen_movie_delete).setOnClickListener(this);
            itemView.findViewById(R.id.btn_seen_movie_edit).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int id = seenMovieList.get(getAdapterPosition()).getId();
            switch (v.getId()){
                case (R.id.btn_seen_movie_delete) :

                    dbHelper = new DBHelper(context);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    int DelCount = database.delete(DBHelper.TABLE_MOVIES, DBHelper.KEY_ID + "=" + id, null);

                    seenMovieList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), seenMovieList.size());

                    if(DelCount>0) Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

                    dbHelper.close();

                    break;

                case (R.id.btn_seen_movie_edit) :

                    ControlMovieFragment editMovieFragment = new ControlMovieFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Movie", seenMovieList.get(getAdapterPosition()));

                    editMovieFragment.setArguments(bundle);

                    editMovieFragment.show(((FragmentActivity) context).getSupportFragmentManager(), ControlMovieFragment.TAG);

                    break;
            }

        }
    }
}
