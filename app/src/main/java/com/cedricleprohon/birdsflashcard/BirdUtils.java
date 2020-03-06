package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


public enum BirdUtils {
    DIFFICULTY("aDifficulty"),
    MAX_QUESTION("aMaxQuestions"),
    GOOD_QUESTION("aGoodAnswerCount"),
    CURRENT_QUESTION("aCurrentQuestionNumber"),
    FLASHCARDS_LIST("aFlashcards"),
    TOPICS_LIST("aTopicsList"),
    URL("http://13.93.86.145"),
    DATABASE(BirdUtils.URL.toString() + "/data"),
    URL_FOLDER(BirdUtils.URL.toString() + "/db/")

    ;

    private String name;

    BirdUtils(String name) {
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
        intent.putParcelableArrayListExtra(BirdUtils.FLASHCARDS_LIST.toString(), flashcards);
        intent.putExtra(BirdUtils.GOOD_QUESTION.toString(), goodAnswerCount);
        intent.putExtra(BirdUtils.CURRENT_QUESTION.toString(), currentQuestionNumber+1);
        intent.putExtra(BirdUtils.DIFFICULTY.toString(), difficulty);
        intent.putExtra(BirdUtils.MAX_QUESTION.toString(), maxQuestions);
        context.startActivity(intent);
    }

    @Override
    public String toString() {
        return name;
    }
}
