package com.kpi.slava.wishlistapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class BookEntity implements Parcelable {

    private int id;
    private String title, genre, author, date, rating;
    private byte read;

    public BookEntity(int id, String genre, String title, String author, String rating, String date, byte read) {
        this.id = id;
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.date = date;
        this.read = read;
    }

    protected BookEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genre = in.readString();
        author = in.readString();
        date = in.readString();
        rating = in.readString();
        read = in.readByte();
    }

    public static final Creator<BookEntity> CREATOR = new Creator<BookEntity>() {
        @Override
        public BookEntity createFromParcel(Parcel in) {
            return new BookEntity(in);
        }

        @Override
        public BookEntity[] newArray(int size) {
            return new BookEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public byte getRead() {
        return read;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(rating);
        dest.writeByte(read);
    }
}
