package com.kpi.slava.wishlistapp.database;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieEntity implements Parcelable {

    private int id;
    private String title, genre, releaseYear, date, rating;
    private byte seen;

    public MovieEntity(int id, String title, String genre, String releaseYear, String date, String rating, byte seen) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.date = date;
        this.rating = rating;
        this.seen = seen;
    }

    protected MovieEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genre = in.readString();
        releaseYear = in.readString();
        date = in.readString();
        rating = in.readString();
        seen = in.readByte();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public byte getSeen() {
        return seen;
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeString(releaseYear);
        dest.writeString(date);
        dest.writeString(rating);
        dest.writeByte(seen);
    }
}
