package com.cedricleprohon.birdsflashcard;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Flashcard implements Parcelable {
    public final Topic topic;
    public List<String> answer = new ArrayList<>();

    public Flashcard(Topic t) {
        topic = t;
    }

    protected Flashcard(Parcel in) {
        topic = in.readParcelable(Topic.class.getClassLoader());
        answer = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(topic, flags);
        dest.writeStringList(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };

    @Override
    public String toString() {
        return "Flashcard{" +
                "topic=" + topic +
                ", answer=" + answer +
                '}';
    }
}
