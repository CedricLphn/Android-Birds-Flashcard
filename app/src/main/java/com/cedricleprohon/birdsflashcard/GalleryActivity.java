package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView RecyclerView;
    private BirdQuestionAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));


        setContentView(R.layout.activity_questions_list);

        Intent intent = getIntent();
        ArrayList<Flashcard> flashcards = intent.getParcelableArrayListExtra(BirdUtils.FLASHCARDS_LIST.toString());

        RecyclerView = findViewById(R.id.questionsList);

        myAdapter = new BirdQuestionAdapter(flashcards);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.setAdapter(myAdapter);
    }

}
