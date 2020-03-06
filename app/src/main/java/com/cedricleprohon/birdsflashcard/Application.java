package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


public enum Application {
    DIFFICULTY("aDifficulty"),
    MAX_QUESTION("aMaxQuestions"),
    GOOD_QUESTION("aGoodAnswerCount"),
    CURRENT_QUESTION("aCurrentQuestionNumber"),
    FLASHCARDS_LIST("aFlashcards"),
    TOPICS_LIST("aTopicsList"),
    URL("http://13.93.86.145"),
    DATABASE(Application.URL.toString() + "/data"),
    URL_FOLDER(Application.URL.toString() + "/db/")

    ;

    private String name;

    Application(String name) {
        this.name = name;
    }

    /**
     * Go to home if user pressed the back button Android
     * @param context
     */
    public static void backToHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    /**
     * Get all difficulty
     * @return Table of difficulty
     */
    public static String[] getDifficulties() {
        return new String[]{
                "Facile",
                "Moyen",
                "Difficile"
        };

    }

    /**
     * Parsing difficulty
     * @param i difficulty
     * @return difficulty string
     */
    public static String getDifficulty(int i) {
        return getDifficulties()[i];
    }

    /**
     * Fast way to go the MainActivity.class
     * @param context
     * @param flashcards List of flashcards
     * @param goodAnswerCount The number of correct answers
     * @param currentQuestionNumber The current question
     * @param maxQuestions maxQuestions
     * @param difficulty Difficulty level
     */

    public static void startMainActivity(Context context, ArrayList<Flashcard> flashcards, int goodAnswerCount, int currentQuestionNumber, int maxQuestions, int difficulty) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putParcelableArrayListExtra(Application.FLASHCARDS_LIST.toString(), flashcards);
        intent.putExtra(Application.GOOD_QUESTION.toString(), goodAnswerCount);
        intent.putExtra(Application.CURRENT_QUESTION.toString(), currentQuestionNumber+1);
        intent.putExtra(Application.DIFFICULTY.toString(), difficulty);
        intent.putExtra(Application.MAX_QUESTION.toString(), maxQuestions);
        context.startActivity(intent);
    }

    @Override
    public String toString() {
        return name;
    }
}
