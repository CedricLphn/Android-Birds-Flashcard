package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EXTRA_MAX_QUESTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        setContentView(R.layout.activity_home);


        findViewById(R.id.playButton).setOnClickListener(this);
        findViewById(R.id.galleryButton).setOnClickListener(this);
        findViewById(R.id.aboutButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playButton:
                chooseDifficultyDialog();
                break;
        }
    }

    private void chooseDifficultyDialog() {
       AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

       final String[] difficultyString = new String[]{
               "Facile",
               "Moyen",
               "Difficile"
       };

       mBuilder.setTitle("Choisir la difficulté");
       mBuilder.setSingleChoiceItems(difficultyString, -1, new DialogInterface.OnClickListener() {

           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
               LaunchActivity(i+1);
           }
       });

       AlertDialog mDialog = mBuilder.create();
       mDialog.show();
    }

    private ArrayList<Flashcard> createFlashcardsRepository(int difficulty) {
        Repository repository = new Repository(this, difficulty);
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        boolean generatedFirstCard = false;

        while(flashcards.size() != EXTRA_MAX_QUESTION) {
            Random tmpRandBird = new Random();
            int randomBird = tmpRandBird.nextInt(repository.size() -1);
            Topic tmpBird = repository.get(randomBird);

            if(!generatedFirstCard) {
                generatedFirstCard = true;
                flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));
            }else {
                if(!flashcards.contains(tmpBird))
                    flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));

            }
        }

        return flashcards;
    }

    private void LaunchActivity(int difficulty) {
        ArrayList<Flashcard > flashcards = createFlashcardsRepository(difficulty);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Application.FLASHCARDS_LIST.toString(), flashcards);
        intent.putExtra(Application.DIFFICULTY.toString(), difficulty);
        intent.putExtra(Application.GOOD_QUESTION.toString(), 0);
        intent.putExtra(Application.MAX_QUESTION.toString(), EXTRA_MAX_QUESTION);
        startActivity(intent);
    }
}
