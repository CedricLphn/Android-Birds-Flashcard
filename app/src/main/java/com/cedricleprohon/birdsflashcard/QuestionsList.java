package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class QuestionsList extends AppCompatActivity {

    private RecyclerView RecyclerView;
    private List<BirdQuestion> birdQuestions;
    private BirdQuestionAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        RecyclerView = (RecyclerView)findViewById(R.id.questionsList);

        birdQuestions = new ArrayList<>();

        birdQuestions.add(new BirdQuestion(1,"tropical", 2));
        birdQuestions.add(new BirdQuestion(2,"desertique", 2));
        birdQuestions.add(new BirdQuestion(3,"zone froide", 2));
        birdQuestions.add(new BirdQuestion(4, "africain", 1));
        birdQuestions.add(new BirdQuestion(5,"causasien", 1));
        birdQuestions.add(new BirdQuestion(6,"a longue plume", 2));
        birdQuestions.add(new BirdQuestion(7,"tropical", 2));
        birdQuestions.add(new BirdQuestion(8,"montagnard", 1));
        birdQuestions.add(new BirdQuestion(9,"tropical", 2));
        birdQuestions.add(new BirdQuestion(10,"a longue plûme", 2));
        birdQuestions.add(new BirdQuestion(11,"à bec crochu", 3));
        birdQuestions.add(new BirdQuestion(12,"migrateur", 2));
        birdQuestions.add(new BirdQuestion(13,"migrateur", 1));
        birdQuestions.add(new BirdQuestion(14,"tropical", 2));

        myAdapter = new BirdQuestionAdapter(birdQuestions);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.setAdapter(myAdapter);
    }
}
