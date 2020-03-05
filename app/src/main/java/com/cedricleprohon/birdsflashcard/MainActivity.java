package com.cedricleprohon.birdsflashcard;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    public static final String EXTRA_GOOD_QUESTION = "aGoodAnswerCount";

    static boolean isFirst = true;

    private Repository repository = null;
    private Topic bird = null;
    private String responseUser = null;

    private int maxQuestions;
    private int currentQuestionNumber;
    private int goodAnswerCount;
    private boolean nextQuestion = false;

    private int difficulty;

    private ArrayList<Flashcard> flashcards;

    private boolean getResult = false;

    List<RadioButton> radios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.i("MainActivity", "handleOnBackPressed: PRESSED");
                backToHome();
            }
        };

        flashcards = new ArrayList<>();
        Intent srcIntent = getIntent();
        currentQuestionNumber = srcIntent.getIntExtra(EXTRA_CURRENT_QUESTION, 0);
        goodAnswerCount = srcIntent.getIntExtra(EXTRA_GOOD_QUESTION, 0);
        isFirst = false;
        flashcards = srcIntent.getParcelableArrayListExtra("aFlashcards");
        goodAnswerCount = srcIntent.getIntExtra("aGoodAnswerCount", 0);
        bird = flashcards.get(currentQuestionNumber).topic;

        difficulty = srcIntent.getIntExtra(Application.DIFFICULTY.toString(), 1);
        maxQuestions = srcIntent.getIntExtra(Application.MAX_QUESTION.toString(), 0);


        initQuestion();

        findViewById(R.id.response1RadioButton).setOnClickListener(this);
        findViewById(R.id.response2RadioButton).setOnClickListener(this);
        findViewById(R.id.response3RadioButton).setOnClickListener(this);
        findViewById(R.id.validateButton).setOnClickListener(this);
        findViewById(R.id.playButton).setOnClickListener(this);
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        int uiNumber = currentQuestionNumber+1;
        setTitle("Question " + uiNumber + " sur " + maxQuestions);

    }

    private void backToHome() {
        Application.backToHome(this);
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
            TextView question = findViewById(R.id.questionTextView);
            question.setText("Quel oiseau produit ce cri ?");
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
                if(!getResult) {
                    if(!nextQuestion) {
                        nextQuestion = true;
                        if(responseUser != null) {
                            for(int i = 0; i < radios.size(); i++) {
                                radios.get(i).setEnabled(false);
                            }
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

                        }
                    }else {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putParcelableArrayListExtra(Application.FLASHCARDS_LIST.toString(), flashcards);
                        intent.putExtra(Application.GOOD_QUESTION.toString(), goodAnswerCount);
                        intent.putExtra(Application.CURRENT_QUESTION.toString(), currentQuestionNumber+1);
                        intent.putExtra(Application.DIFFICULTY.toString(), difficulty);
                        intent.putExtra(Application.MAX_QUESTION.toString(), maxQuestions);
                        startActivity(intent);
                    }
                }else {
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("aDifficulty", difficulty);
                    intent.putExtra(EXTRA_GOOD_QUESTION, goodAnswerCount);
                    intent.putExtra("aMaxQuestions", maxQuestions);
                    startActivity(intent);
                }


                break;
            case R.id.playButton:
                    MediaPlayer.create(this, getResources().getIdentifier(bird.sound, "raw", getPackageName())).start();
                break;
        }
    }
}
