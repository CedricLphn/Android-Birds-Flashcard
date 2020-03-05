package com.cedricleprohon.birdsflashcard;

class BirdQuestion {
    private String theme;
    private int difficulty;

    BirdQuestion(String theme, int difficulty){
        this.theme = theme;
        this.difficulty = difficulty;
    }

    public String getTheme() {
        return theme;
    }

    public int getDifficulty(){
        return difficulty;
    }
}
