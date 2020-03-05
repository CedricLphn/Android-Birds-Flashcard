package com.cedricleprohon.birdsflashcard;

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

    @Override
    public String toString() {
        return name;
    }
}
