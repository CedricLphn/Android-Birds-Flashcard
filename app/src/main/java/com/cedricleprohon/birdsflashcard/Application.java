package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.content.Intent;

public enum Application {
    DIFFICULTY("aDifficulty"),
    MAX_QUESTION("aMaxQuestions"),
    GOOD_QUESTION("aGoodAnswerCount"),
    CURRENT_QUESTION("aCurrentQuestionNumber"),
    FLASHCARDS_LIST("aFlashcards")

    ;


    private String name;

    Application(String name) {
        this.name = name;
    }

    public static void backToHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String toString() {
        return name;
    }
}
