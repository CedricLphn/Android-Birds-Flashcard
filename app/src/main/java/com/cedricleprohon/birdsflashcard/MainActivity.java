package com.cedricleprohon.birdsflashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static boolean isFirst = true;

    /**
     * Flashcard
     */
    private Topic bird = null;
    private ArrayList<Flashcard> flashcards;


    /**
     * Set The repository
     */
    private int difficulty;
    private int maxQuestions;
    private int currentQuestionNumber;
    private int goodAnswerCount;
    private boolean nextQuestion = false;

    /**
     * UX
     */
    private String responseUser = null;
    private boolean getResult = false;
    List<RadioButton> radios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Set UX
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        /**
         * Go to home if user click in back button
         */
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.i("MainActivity", "handleOnBackPressed: PRESSED");
                backToHome();
            }
        };

        // Initialize variable
        isFirst = false;
        flashcards = new ArrayList<>();
        Intent srcIntent = getIntent();
        currentQuestionNumber = srcIntent.getIntExtra(BirdUtils.CURRENT_QUESTION.toString(), 0);
        goodAnswerCount = srcIntent.getIntExtra(BirdUtils.GOOD_QUESTION.toString(), 0);
        flashcards = srcIntent.getParcelableArrayListExtra(BirdUtils.FLASHCARDS_LIST.toString());
        difficulty = srcIntent.getIntExtra(BirdUtils.DIFFICULTY.toString(), 1);
        maxQuestions = srcIntent.getIntExtra(BirdUtils.MAX_QUESTION.toString(), 0);

        // Get the current topic of flashcard
        bird = flashcards.get(currentQuestionNumber).topic;

        // Set the list of radio Button
        radios = new ArrayList<>();

        RadioButton r1 = findViewById(R.id.response1RadioButton);
        RadioButton r2 = findViewById(R.id.response2RadioButton);
        RadioButton r3 = findViewById(R.id.response3RadioButton);
        radios.add(r1);
        radios.add(r2);
        radios.add(r3);

        initQuestion();

        // Binding click
        findViewById(R.id.response1RadioButton).setOnClickListener(this);
        findViewById(R.id.response2RadioButton).setOnClickListener(this);
        findViewById(R.id.response3RadioButton).setOnClickListener(this);
        findViewById(R.id.validateButton).setOnClickListener(this);
        findViewById(R.id.playButton).setOnClickListener(this);
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        // Show the current question
        int uiNumber = currentQuestionNumber+1;
        setTitle("Question " + uiNumber + " sur " + maxQuestions);

    }

    /**
     * Fast way to go home
     */
    private void backToHome() {
        BirdUtils.backToHome(this);
    }

    /**
     * Initialize UX flashcard interface
     */
    private void initQuestion() {
        // Hide Pictures birds and sound
        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        findViewById(R.id.playButton).setVisibility(View.INVISIBLE);
        TextView answerresp = findViewById(R.id.answerTextView);
        answerresp.setText("");

        nextQuestion = false;

        // Computer choose beetween picture or sound
        if((int)(Math.random() * 2) == 1) {
            // Is Picture
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        }else {
            // Is Sound
            findViewById(R.id.playButton).setVisibility(View.VISIBLE);
            TextView question = findViewById(R.id.questionTextView);
            question.setText("Quel oiseau produit ce cri ?");
        }

        // Load Picture
        ImageView birdImageView = findViewById(R.id.imageView);
        Picasso.get().load(BirdUtils.URL_FOLDER.toString() + bird.image)
                .resize(400,400)
                .into(birdImageView);

        // Get temporary current flashcard
        Flashcard answer = flashcards.get(currentQuestionNumber);
        Log.i("MainActivity", "initQuestion: " + answer);

        // Show possibility answer in radioButton
        for(int i = 0; i < radios.size(); i++) {
            radios.get(i).setText(answer.answer.get(i));
        }

    }

    public String getTextRadioButton(int idRadio) {
        return radios.get(idRadio).getText().toString();
    }

    @Override
    public void onClick(View v) {
        TextView answerTextView = findViewById(R.id.answerTextView);

        /**
         * Check if
         *  - User pressed first radio
         *  - User pressed the second radio
         *  - User pressed third radio
         *  - User pressed Validate Button
         *  - User pressed the Listen Button
         */
        switch (v.getId()) {
            case R.id.response1RadioButton:
                responseUser = getTextRadioButton(0);
                break;
            case R.id.response2RadioButton:
                responseUser = getTextRadioButton(1);
                break;
            case R.id.response3RadioButton:
                responseUser = getTextRadioButton(2);
                break;
            case R.id.validateButton:
                /**
                 * Check if
                 * - getResult is not true --> So the user is not at the last question
                 * - nextquestion is false --> The user did not click on the button to go to the following question
                 * - responseUser is null --> the user did not answer
                 */
                if(!getResult) {
                    if(!nextQuestion && responseUser != null) {
                        nextQuestion = true;
                        // Disable all radio button
                        for(int i = 0; i < radios.size(); i++) {
                            radios.get(i).setEnabled(false);
                        }
                        // If the user has the right answer
                        if(responseUser.equals(flashcards.get(currentQuestionNumber).topic.name)) {
                            answerTextView.setText("Bonne réponse");
                            goodAnswerCount = goodAnswerCount + 1;
                        }else {
                            answerTextView.setText("Mauvaise réponse, la réponse était "+ bird.name);
                        }

                        Button btn = findViewById(R.id.validateButton);
                        if(currentQuestionNumber != (maxQuestions -1)) {
                            btn.setText("Question suivante");
                        }else {
                            btn.setText("Voir le résultat");
                            getResult = true;
                        }

                    }else {
                        if(responseUser != null) {
                            BirdUtils.startMainActivity(
                                    this,
                                    flashcards,
                                    goodAnswerCount,
                                    currentQuestionNumber,
                                    maxQuestions,
                                    difficulty
                            );
                        }

                    }
                }else {
                    // If the number of questions is reached, the user is redirected to the result page.
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra(BirdUtils.DIFFICULTY.toString(), difficulty);
                    intent.putExtra(BirdUtils.GOOD_QUESTION.toString(), goodAnswerCount);
                    intent.putExtra(BirdUtils.MAX_QUESTION.toString(), maxQuestions);
                    startActivity(intent);
                }


                break;
            case R.id.playButton:
                // Play the beautiful song of bird
                    MediaPlayer mp = new MediaPlayer();

                try {
                    mp.setDataSource(BirdUtils.URL_FOLDER + bird.sound);
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
