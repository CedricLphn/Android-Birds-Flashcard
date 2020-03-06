package com.cedricleprohon.birdsflashcard;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.i("ResultActivity", "handleOnBackPressed: PRESSED");
                backToHome();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        Intent intent = getIntent();
        int goodAnswerCount = intent.getIntExtra(Application.GOOD_QUESTION.toString(), 0);
        int maxQuestionsCount = intent.getIntExtra(Application.MAX_QUESTION.toString(), 0);
        int difficultyInteger = intent.getIntExtra(Application.DIFFICULTY.toString(), 0);

        TextView goodAnswer = findViewById(R.id.goodAnswerTextView);
        TextView percent = findViewById(R.id.resultTextView);
        TextView difficulty = findViewById(R.id.difficultyTextView);

        difficulty.setText(Application.getDifficulty(difficultyInteger-1));

        goodAnswer.setText(goodAnswerCount + "/ "+ maxQuestionsCount);

        // Calcul percent of win
        double winRate = (double)goodAnswerCount / (double)maxQuestionsCount * 100;

        percent.setText((int)winRate + "%");
        setTitle("Résultat");
    }

    private void backToHome() {
        Application.backToHome(this);
    }
}
