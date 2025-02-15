package com.cedricleprohon.birdsflashcard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class BirdQuestionAdapter extends RecyclerView.Adapter<BirdQuestionAdapter.MyViewHolder> {

    private final List<Flashcard> flashcards;
    List<Topic> topics;

    //ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView themeTV;
        private TextView difficultyTV;
        private int i = 0;
        private ImageView birdImageView;
        private ConstraintLayout questionItem;
        private View itemView;


        MyViewHolder(View itemView){
            super(itemView);

            // Finding every itemView to fill them with the appropriate data
            birdImageView = itemView.findViewById(R.id.birdImageView);
            themeTV = itemView.findViewById(R.id.theme);
            difficultyTV = itemView.findViewById(R.id.difficulty);
            questionItem = itemView.findViewById(R.id.questionItem);

            this.itemView = itemView;

        }

        //Setting the Question Number, the image, the difficulty and the onclickListener for every item
        void display(Topic topic, int id){
            //this.i will be used for the onClick function
            this.i = id -1;

            themeTV.setText("Question " + id);
            Picasso.get().load(BirdUtils.URL_FOLDER.toString() + topic.image).into(birdImageView);
            difficultyTV.setText(BirdUtils.getDifficulty(topic.difficulty-1));


            questionItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ArrayList<Flashcard> flashcard = new ArrayList<>();
            flashcard.add(flashcards.get(i));

            //Creating Intent to pass the needed data of the clicked item to MainActivity
            Intent intent = new Intent(this.itemView.getContext(), MainActivity.class);
            intent.putExtra(BirdUtils.FLASHCARDS_LIST.toString(), flashcard);
            intent.putExtra(BirdUtils.DIFFICULTY.toString(), topics.get(i).difficulty);
            intent.putExtra(BirdUtils.GOOD_QUESTION.toString(), 0);
            intent.putExtra(BirdUtils.MAX_QUESTION.toString(), 1);

            itemView.getContext().startActivity(intent);
        }
    }


    BirdQuestionAdapter(List<Flashcard> flashcards){
        topics = new ArrayList<>();
        this.flashcards = flashcards;
        for(int i = 0; i < flashcards.size(); i++) {
            topics.add(flashcards.get(i).topic);
        }
    }

    @Override
    public BirdQuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bird_question_item, parent, false);
        return new MyViewHolder(view);
    }

    //For every Item of the Gallery list this function is called
    @Override
    public void onBindViewHolder(BirdQuestionAdapter.MyViewHolder holder, int position) {
        holder.display(topics.get(position), position+1);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
