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

        birdQuestions.add(new BirdQuestion("tropical", 2));
        birdQuestions.add(new BirdQuestion("desertique", 2));
        birdQuestions.add(new BirdQuestion("zone froide", 2));
        birdQuestions.add(new BirdQuestion("africain", 1));
        birdQuestions.add(new BirdQuestion("causasien", 1));
        birdQuestions.add(new BirdQuestion("a longue plume", 2));
        birdQuestions.add(new BirdQuestion("tropical", 2));
        birdQuestions.add(new BirdQuestion("montagnard", 1));
        birdQuestions.add(new BirdQuestion("tropical", 2));
        birdQuestions.add(new BirdQuestion("a longue plûme", 2));
        birdQuestions.add(new BirdQuestion("à bec crochu", 3));
        birdQuestions.add(new BirdQuestion("migrateur", 2));
        birdQuestions.add(new BirdQuestion("migrateur", 1));
        birdQuestions.add(new BirdQuestion("tropical", 2));

        myAdapter = new BirdQuestionAdapter(birdQuestions);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.setAdapter(myAdapter);
    }
}
