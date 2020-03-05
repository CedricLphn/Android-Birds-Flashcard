package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Repository {
    private JSONArray repo;
    private ArrayList<Topic> topics_all;
    public ArrayList<Topic> topics;

    private int idTopic;

    public Repository(Context context) {
        topics_all = new ArrayList<>();
        topics = new ArrayList<>();
        String JSONString = null;
        JSONArray JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = context.getResources().openRawResource(R.raw.data);

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
        }
        catch (JSONException x) {
            x.printStackTrace();
        }

        repo = JSONObject;

        for(int i = 0; i < size(); i++) {
            JSONObject object = null;
            try {
                object = repo.getJSONObject(i);

                topics_all.add(new Topic(object.getString("image"), object.getString("name"), object.getString("sound"), object.getInt("difficulty")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        topics.addAll(topics_all);

    }

    public Repository(Context context, int difficulty){
        this(context);
        topics = new ArrayList<>();

        ArrayList<Topic> tmp = new ArrayList<>();

        for(int i = 0; i < topics_all.size(); i++){
            if(topics_all.get(i).difficulty != difficulty)
            {
                tmp.add(topics_all.get(i));
            }
        }

        topics_all.removeAll(tmp);
        topics.addAll(topics_all);
    }

    public Topic get(int id)  {
        return topics_all.get(id);
    }


    public Flashcard generateFlashcard(List<Topic> topics, Topic topic, int count) {
        Flashcard flashcard = new Flashcard(topic);

        Random rand = new Random();

        flashcard.answer.add(topic.name);

        int i = 0;

        while (flashcard.answer.size() != count) {
            int random = rand.nextInt(topics.size());

            Topic t = topics.get(random);

            if (!flashcard.answer.contains(t.name)) {
                flashcard.answer.add(t.name);
            }

        }

        Collections.shuffle(flashcard.answer);

        Log.i("FLASHCARD", flashcard.toString());

        return flashcard;
    }


    public int size() {
        return repo.length();
    }

    @Override
    public String toString() {
        return "Repository{" +
                ", repo=" + repo +
                ", topics_all=" + topics_all +
                ", topics=" + topics +
                ", idTopic=" + idTopic +
                '}';
    }
}
