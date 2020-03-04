package com.cedricleprohon.birdsflashcard;

import org.json.JSONException;
import org.json.JSONObject;

public class Topic {
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
