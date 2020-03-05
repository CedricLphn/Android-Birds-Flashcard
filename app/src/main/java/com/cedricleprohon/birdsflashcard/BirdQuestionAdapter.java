package com.cedricleprohon.birdsflashcard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BirdQuestionAdapter extends RecyclerView.Adapter<BirdQuestionAdapter.MyViewHolder> {

    List<BirdQuestion> birdQuestionList;


    //classe

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView themeTV;
        private TextView difficultyTV;

        MyViewHolder(View itemView){
            super(itemView);

            themeTV = itemView.findViewById(R.id.theme);
            difficultyTV = itemView.findViewById(R.id.difficulty);
        }

        void display(BirdQuestion birdQuestion){
            themeTV.setText(birdQuestion.getTheme());
            difficultyTV.setText(String.valueOf(birdQuestion.getDifficulty()));
        }
    }


    BirdQuestionAdapter(List<BirdQuestion> birdQuestions){
        this.birdQuestionList = birdQuestions;
    }

    @Override
    public BirdQuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bird_question_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BirdQuestionAdapter.MyViewHolder holder, int position) {
        holder.display(birdQuestionList.get(position));
        Log.i("toto", "coucou");
        //BirdQuestion question = birdQuestionList.get(position);

        //holder.themeTV.setText(question.getTheme());
        //holder.difficultyTV.setText(String.valueOf(question.getDifficulty()));
    }

    @Override
    public int getItemCount() {
        return birdQuestionList.size();
    }
}
