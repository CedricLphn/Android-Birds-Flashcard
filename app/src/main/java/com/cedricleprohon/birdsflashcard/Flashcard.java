package com.cedricleprohon.birdsflashcard;

import java.util.ArrayList;
import java.util.List;

public class Flashcard {
    public final Topic topic;
    public final List<String> answer = new ArrayList<>();

    public Flashcard(Topic t) {
        topic = t;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "topic=" + topic +
                ", answer=" + answer +
                '}';
    }
}
