package com.cedricleprohon.birdsflashcard;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView themeTV;
    private TextView difficultyTV;

    MyViewHolder(View itemView){
        super(itemView);

        themeTV = (TextView)itemView.findViewById(R.id.theme);
        difficultyTV = (TextView)itemView.findViewById(R.id.difficulty);
    }

    void display(BirdQuestion birdQuestion){
        themeTV.setText(birdQuestion.getTheme());
        difficultyTV.setText(birdQuestion.getDifficulty());
    }
}
