package com.cedricleprohon.birdsflashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Integer> exclude;
    private int responseCode = -1;
    private JSONObject bird = null;
    private int responseUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if((int)(Math.random() * 2) == 1) {
            // Is Picture
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        }else {
            // Is Sound
            findViewById(R.id.playButton).setVisibility(View.VISIBLE);

        }

        try {
            int randomBird = (int)(Math.random() * ((size() - 1) + 1)) + 1;

            bird = get(randomBird);


            exclude = new ArrayList<>();
            exclude.add(1);

            int random = (int)(Math.random() * ((3 - 1) + 1)) + 1;
            responseCode = random;

            RadioButton r1 = findViewById(R.id.response1RadioButton);
            RadioButton r2 = findViewById(R.id.response2RadioButton);
            RadioButton r3 = findViewById(R.id.response3RadioButton);

            ImageView birdImageView = findViewById(R.id.imageView);
            birdImageView.setImageResource(BirdsPicture.getResId(bird.getString("image")));

            Log.e("randomgenerator", String.valueOf(random));


            if(random == 1) {
                r1.setText(bird.getString("name"));
                r2.setText(getRandomName());
                r3.setText(getRandomName());
            }else if(random == 2) {
                r1.setText(getRandomName());
                r2.setText(bird.getString("name"));
                r3.setText(getRandomName());
            }else {
                r1.setText(getRandomName());
                r2.setText(getRandomName());
                r3.setText(bird.getString("name"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.response1RadioButton).setOnClickListener(this);
        findViewById(R.id.response2RadioButton).setOnClickListener(this);
        findViewById(R.id.response3RadioButton).setOnClickListener(this);
        findViewById(R.id.validateButton).setOnClickListener(this);
        findViewById(R.id.playButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        TextView answerTextView = findViewById(R.id.answerTextView);

        switch (v.getId()) {
            case R.id.response1RadioButton:
                responseUser = 1;
                break;
            case R.id.response2RadioButton:
                responseUser = 2;
                break;
            case R.id.response3RadioButton:
                responseUser = 3;
                break;
            case R.id.validateButton:
                if(responseUser != -1) {
                    if(responseUser == responseCode && responseUser != -1) {
                        answerTextView.setText("Bonne réponse");
                    }else {
                        try {
                            answerTextView.setText("Mauvaise réponse, la réponse était "+ bird.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.playButton:
                try {
                    MediaPlayer.create(this, BirdsSound.getResId(bird.getString("sound"))).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public JSONArray parseJSONData() {
        String JSONString = null;
        JSONArray JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = getResources().openRawResource(R.raw.data);

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            JSONObject = new JSONArray(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return JSONObject;
    }

    public JSONObject get(int id) throws JSONException {
        return new JSONObject(parseJSONData().get(id).toString());
    }

    public String getRandomName() throws JSONException {
        return get(generateRandom(0, size(), exclude)).getString("name");
    }

    public int generateRandom(int start, int end, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range) + 1;
        while(excludeRows.contains(random)) {
            random = rand.nextInt(range) + 1;
        }

        exclude.add(random);

        return random;
    }

    public int size() {
        return parseJSONData().length() - 1;
    }

}
