package com.cedricleprohon.birdsflashcard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Topic implements Parcelable {
    public String image;
    public String name;
    public String sound;
    public int difficulty;

    public Topic(String image, String name, String sound, int difficulty) {
        this.image = image;
        this.name = name;
        this.sound = sound;
        this.difficulty = difficulty;
    }

    protected Topic(Parcel in) {
        image = in.readString();
        name = in.readString();
        sound = in.readString();
        difficulty = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(sound);
        dest.writeInt(difficulty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    @Override
    public String toString() {
        return "Topic{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", sound='" + sound + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
