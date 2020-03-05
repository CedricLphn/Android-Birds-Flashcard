package com.cedricleprohon.birdsflashcard;

class BirdQuestion {
    private int id;
    private String theme;
    private int difficulty;

    BirdQuestion(int id, String theme, int difficulty){
        this.id = id;
        this.theme = theme;
        this.difficulty = difficulty;
    }
    public int getId(){return id;}

    public String getTheme() {
        return theme;
    }

    public int getDifficulty(){
        return difficulty;
    }
}
