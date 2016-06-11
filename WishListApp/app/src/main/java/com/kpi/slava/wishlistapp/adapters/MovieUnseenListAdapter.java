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
import com.kpi.slava.wishlistapp.fragments.RatingFragment;

import java.util.ArrayList;
import java.util.List;

public class MovieUnseenListAdapter extends RecyclerView.Adapter<MovieUnseenListAdapter.ViewHolder> {

    List<MovieEntity> unseenMovieList = new ArrayList<MovieEntity>();
    Context context;
    DBHelper dbHelper;

    public MovieUnseenListAdapter(List<MovieEntity> unseenMovieList, Context context) {
        this.unseenMovieList = unseenMovieList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_unseen_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(unseenMovieList.get(position).getTitle());
        holder.genre.setText(unseenMovieList.get(position).getGenre());
        holder.release.setText(unseenMovieList.get(position).getReleaseYear());
        holder.date.setText(unseenMovieList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return unseenMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, genre, release, date;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_unseen_movie_title);
            genre = (TextView) itemView.findViewById(R.id.tv_unseen_movie_genre);
            release = (TextView) itemView.findViewById(R.id.tv_unseen_movie_release_year);
            date = (TextView) itemView.findViewById(R.id.tv_unseen_movie_date);

            itemView.findViewById(R.id.btn_unseen_movie_delete).setOnClickListener(this);
            itemView.findViewById(R.id.btn_unseen_movie_edit).setOnClickListener(this);
            itemView.findViewById(R.id.btn_unseen_movie_seen).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = unseenMovieList.get(getAdapterPosition()).getId();
            switch (v.getId()) {
                case (R.id.btn_unseen_movie_delete):

                    dbHelper = new DBHelper(context);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    int DelCount = database.delete(DBHelper.TABLE_MOVIES, DBHelper.KEY_ID + "=" + id, null);

                    if (DelCount > 0) {
                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        unseenMovieList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), unseenMovieList.size());
                    }
                    dbHelper.close();

                    break;

                case (R.id.btn_unseen_movie_edit):

                    ControlMovieFragment editMovieFragment = new ControlMovieFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Movie", unseenMovieList.get(getAdapterPosition()));

                    editMovieFragment.setArguments(bundle);

                    editMovieFragment.show(((FragmentActivity) context).getSupportFragmentManager(), ControlMovieFragment.TAG);

                    break;

                case (R.id.btn_unseen_movie_seen):

                    RatingFragment ratingFragment = new RatingFragment();

                    Bundle ratingMovieBundle = new Bundle();
                    ratingMovieBundle.putString("Type", "movie");
                    ratingMovieBundle.putParcelable("Movie", unseenMovieList.get(getAdapterPosition()));

                    ratingFragment.setArguments(ratingMovieBundle);

                    ratingFragment.show(((FragmentActivity) context).getSupportFragmentManager(), RatingFragment.TAG);

                    break;
            }
        }
    }
}
