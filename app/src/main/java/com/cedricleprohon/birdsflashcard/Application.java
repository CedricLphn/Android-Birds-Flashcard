package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.content.Intent;

public enum Application {
    DIFFICULTY("aDifficulty"),
    MAX_QUESTION("aMaxQuestions"),
    GOOD_QUESTION("aGoodAnswerCount"),
    CURRENT_QUESTION("aCurrentQuestionNumber"),
    FLASHCARDS_LIST("aFlashcards"),
    TOPICS_LIST("aTopicsList")

    ;


    private String name;

    Application(String name) {
        this.name = name;
    }

    public static void backToHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static String getDifficulty(int i) {
        String[] difficulty = new String[]{
                "N/A",
                "Facile",
                "Moyen",
                "Difficile"
        };

        return difficulty[i];
    }

    @Override
    public String toString() {
        return name;
    }
}
