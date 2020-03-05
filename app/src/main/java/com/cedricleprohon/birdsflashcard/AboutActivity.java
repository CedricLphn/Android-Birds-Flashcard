package com.cedricleprohon.birdsflashcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE49C")));

        setContentView(R.layout.about);


        findViewById(R.id.backButton).setOnClickListener(this);


        TextView versionView = findViewById(R.id.appVersion);
        TextView devNames = findViewById(R.id.devNames);
        TextView appName = findViewById(R.id.appName);

        //setting VERSION_NAME, App Name, Devs Names to UI
        String versionCode = BuildConfig.VERSION_NAME;
        String versionCodeString = "version " + versionCode;
        versionView.setText(versionCodeString);

        String dev_names = "By Cédric Leprohon et Melec du Halgouet";
        devNames.setText(dev_names);

        String app_name = "Birds Flashcard";
        appName.setText(app_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                backToHome();
                break;
            case R.id.aboutBird:
                //goToGallery();
                break;

        }
    }

    private void backToHome() {
        Application.backToHome(this);
    }
}
