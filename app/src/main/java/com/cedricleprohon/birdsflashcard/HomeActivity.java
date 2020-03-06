package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    // Modify this line for more flashcard
    private static final int EXTRA_MAX_QUESTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change background color of Title
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        setContentView(R.layout.activity_home);

        // Listen click event
        findViewById(R.id.playButton).setOnClickListener(this);
        findViewById(R.id.galleryButton).setOnClickListener(this);
        findViewById(R.id.aboutButton).setOnClickListener(this);
    }

    /**
     * Onclick bind
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playButton:
                chooseDifficultyDialog();
                break;
            case R.id.galleryButton:
                try {
                    goToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.aboutButton:
                goToAbout();
                break;
        }
    }


    /**
     * Fast way to go About Page
     */

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Fast way to go Gallery Page
     */
    
    private void goToGallery() throws IOException {
        Repository repository = new Repository(this);
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        for(int i = 0; i < repository.topics.size(); i++) {
            flashcards.add(repository.generateFlashcard(repository.topics, repository.get(i), 3));
        }

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(BirdUtils.FLASHCARDS_LIST.toString(), flashcards);
        startActivity(intent);
    }

    /**
     * Create alert for choose difficulty
     */
    private void chooseDifficultyDialog() {
        // Create Alert Dialog component
       AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

       mBuilder.setTitle("Choisir la difficulté");
       mBuilder.setSingleChoiceItems(BirdUtils.getDifficulties(), -1, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
               try {
                   LaunchActivity(i+1);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });

       AlertDialog mDialog = mBuilder.create();
       mDialog.show();
    }

    /**
     * Create all flashcards
     * @param difficulty Set the difficulty (1-3)
     * @return
     */
    private ArrayList<Flashcard> createFlashcardsRepository(int difficulty) throws IOException {
        Repository repository = new Repository(this, difficulty);
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        boolean generatedFirstCard = false;

        while(flashcards.size() != EXTRA_MAX_QUESTION) {
            // Get a random flashcard
            Random tmpRandBird = new Random();
            int randomBird = tmpRandBird.nextInt(repository.size() -1);
            Topic tmpBird = repository.get(randomBird);

            // This is the first card ?
            if(!generatedFirstCard) {
                // Adding the flashcard without checks
                generatedFirstCard = true;
                flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));
            }else {
                // No, check to prevent duplicates flashcards
                if(!flashcards.contains(tmpBird))
                    flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));

            }
        }

        return flashcards;
    }

    /**
     * Launch the Main activity
     * Requirement :
     *  - a Flashcards Repository
     *  - difficulty
     *  - good questions number
     *  - maximum questions for this game
     * @param difficulty
     */
    private void LaunchActivity(int difficulty) throws IOException {
        ArrayList<Flashcard > flashcards = createFlashcardsRepository(difficulty);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BirdUtils.FLASHCARDS_LIST.toString(), flashcards);
        intent.putExtra(BirdUtils.DIFFICULTY.toString(), difficulty);
        intent.putExtra(BirdUtils.GOOD_QUESTION.toString(), 0);
        intent.putExtra(BirdUtils.MAX_QUESTION.toString(), EXTRA_MAX_QUESTION);
        startActivity(intent);
    }
}
