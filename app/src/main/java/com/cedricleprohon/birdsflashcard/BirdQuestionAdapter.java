package com.cedricleprohon.birdsflashcard;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BirdQuestionAdapter extends RecyclerView.Adapter<BirdQuestionAdapter.MyViewHolder> {

    List<BirdQuestion> birdQuestionList;

    //classe

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView themeTV;
        private TextView difficultyTV;
        private TextView buttonTV;

        MyViewHolder(View itemView){
            super(itemView);

            themeTV = itemView.findViewById(R.id.theme);
            difficultyTV = itemView.findViewById(R.id.difficulty);
        }


        void superFonction(){
            Log.i("toto", "coucou");
        }

        void display(BirdQuestion birdQuestion, int id){
            themeTV.setText(birdQuestion.getTheme());
            difficultyTV.setText(String.valueOf(birdQuestion.getDifficulty()));
            themeTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.i("toto", "tata");
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
        holder.display(birdQuestionList.get(position), birdQuestionList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return birdQuestionList.size();
    }
}
