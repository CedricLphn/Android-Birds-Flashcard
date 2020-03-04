package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_CURRENT_QUESTION = "aCurrentQuestionNumber";
    static boolean isFirst = true;

    private ArrayList<Integer> exclude;
    private Repository repository = null;
    private Topic bird = null;
    private String responseUser = null;

    private int maxQuestions = 2;
    private int currentQuestionNumber = 0;
    private int goodAnswerCount = 0;
    private boolean nextQuestion = false;

    private int difficulty = 1;

    private ArrayList<Flashcard> flashcards;

    List<RadioButton> radios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcards = new ArrayList<>();

        if (isFirst) {
            isFirst=  false;
            repository = new Repository(this, difficulty);


            boolean generatedFirstCard = false;
            while(flashcards.size() != maxQuestions) {
                Random tmpRandBird = new Random();
                int randomBird = tmpRandBird.nextInt(repository.size() -1);
                Topic tmpBird = repository.get(randomBird);

                if(!generatedFirstCard) {
                    flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));
                }else if(!flashcards.contains(tmpBird)) {
                    flashcards.add(repository.generateFlashcard(repository.topics, tmpBird, 3));
                }else {
                    Log.e("FLASHCARD", "already exist");
                }
            }

            bird = flashcards.get(0).topic;

        }else {
            Intent srcIntent = getIntent();
            currentQuestionNumber = srcIntent.getIntExtra(EXTRA_CURRENT_QUESTION, 0);
            currentQuestionNumber++;
            isFirst = false;
            flashcards = srcIntent.getParcelableArrayListExtra("aFlashcards");
            goodAnswerCount = srcIntent.getIntExtra("aGoodAnswerCount", 0);
            bird = flashcards.get(currentQuestionNumber).topic;

        }

        initQuestion();

        findViewById(R.id.response1RadioButton).setOnClickListener(this);
        findViewById(R.id.response2RadioButton).setOnClickListener(this);
        findViewById(R.id.response3RadioButton).setOnClickListener(this);
        findViewById(R.id.validateButton).setOnClickListener(this);
        findViewById(R.id.playButton).setOnClickListener(this);

        int uiNumber = currentQuestionNumber+1;
        setTitle("Flashcard " + uiNumber + "/" + maxQuestions);

    }

    private void initQuestion() {
        radios = new ArrayList<>();

        RadioButton r1 = findViewById(R.id.response1RadioButton);
        RadioButton r2 = findViewById(R.id.response2RadioButton);
        RadioButton r3 = findViewById(R.id.response3RadioButton);
        radios.add(r1);
        radios.add(r2);
        radios.add(r3);

        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        findViewById(R.id.playButton).setVisibility(View.INVISIBLE);
        TextView answerresp = findViewById(R.id.answerTextView);
        answerresp.setText("");

        nextQuestion = false;

        if((int)(Math.random() * 2) == 1) {
            // Is Picture
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        }else {
            // Is Sound
            findViewById(R.id.playButton).setVisibility(View.VISIBLE);

        }

        ImageView birdImageView = findViewById(R.id.imageView);
        birdImageView.setImageResource(getResources().getIdentifier(bird.image, "drawable", getPackageName()));

        Flashcard answer = flashcards.get(currentQuestionNumber);
        Log.i("MainActivity", "initQuestion: " + answer);

        for(int i = 0; i < radios.size(); i++) {
            radios.get(i).setText(answer.answer.get(i));
        }

    }

    @Override
    public void onClick(View v) {
        TextView answerTextView = findViewById(R.id.answerTextView);

        switch (v.getId()) {
            case R.id.response1RadioButton:
                responseUser = radios.get(0).getText().toString();
                break;
            case R.id.response2RadioButton:
                responseUser = radios.get(1).getText().toString();
                break;
            case R.id.response3RadioButton:
                responseUser = radios.get(2).getText().toString();
                break;
            case R.id.validateButton:
                if(!nextQuestion) {
                    nextQuestion = true;
                    if(responseUser != null) {
                        for(int i = 0; i < radios.size(); i++) {
                            radios.get(i).setEnabled(false);
                        }
                        if(responseUser.equals(flashcards.get(currentQuestionNumber).topic.name)) {
                            answerTextView.setText("Bonne réponse");
                            goodAnswerCount++;
                        }else {
                            answerTextView.setText("Mauvaise réponse, la réponse était "+ bird.name);
                        }

                        Button btn = findViewById(R.id.validateButton);
                        if(currentQuestionNumber != (maxQuestions -1)) {
                            btn.setText("Question suivante");
                        }else {
                            btn.setText("Voir le résultat");
                        }

                    }
                }else {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putParcelableArrayListExtra("aFlashcards", flashcards);
                    intent.putExtra("aIsFirst", isFirst);
                    intent.putExtra("aGoodAnswerQuestion", goodAnswerCount);
                    intent.putExtra(EXTRA_CURRENT_QUESTION, currentQuestionNumber);
                    intent.putExtra("aDifficulty", difficulty);
                    startActivity(intent);
                }

                break;
            case R.id.playButton:
                    MediaPlayer.create(this, getResources().getIdentifier(bird.sound, "raw", getPackageName())).start();
                break;
        }
    }
}
