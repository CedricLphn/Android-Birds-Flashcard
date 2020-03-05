package com.cedricleprohon.birdsflashcard;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

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

    public Repository(Context context) {
        topics_all = new ArrayList<>();
        topics = new ArrayList<>();
        loadJson(context);
    }



    public Repository(Context context, int difficulty){
        this(context);
        // Clear all topic
        topics.clear();
        ArrayList<Topic> tmp = new ArrayList<>();

        // Remove all flashcards unwanted difficulty
        for(int i = 0; i < topics_all.size(); i++){
            if(topics_all.get(i).difficulty != difficulty)
            {
                // Add flashcard in a temporary list for remove after
                tmp.add(topics_all.get(i));
            }
        }

        // Remove all unwanted flashcards
        topics_all.removeAll(tmp);
        // Copy all topics with good difficulty
        topics.addAll(topics_all);
    }

    public Topic get(int id)  {
        return topics_all.get(id);
    }

    /**
     * Generate flashcard
     * @param topics Arraylist of Topic Class
     * @param topic The 'good' topic
     * @param count Number of desired potentially answer
     * @return Delightful Flashcard
     */
    public Flashcard generateFlashcard(List<Topic> topics, Topic topic, int count) {
        Flashcard flashcard = new Flashcard(topic);

        Random rand = new Random();

        // Add the good topic
        flashcard.answer.add(topic.name);

        while (flashcard.answer.size() != count) {
            // Generate a random number
            int random = rand.nextInt(topics.size());

            // Get a random Topic
            Topic t = topics.get(random);

            // Check for prevent duplicate answer
            if (!flashcard.answer.contains(t.name)) {
                flashcard.answer.add(t.name);
            }

        }

        // SHUFFLE !
        Collections.shuffle(flashcard.answer);

        Log.i("FLASHCARD", flashcard.toString());

        return flashcard;
    }


    public int size() {
        return topics.size();
    }

    /**
     * Load JSON
     * @param context
     */
    private void loadJson(Context context) {
        String JSONString;
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

        for(int i = 0; i < repo.length(); i++) {
            org.json.JSONObject object;
            try {
                object = repo.getJSONObject(i);
                topics_all.add(new Topic(object.getString("image"), object.getString("name"), object.getString("sound"), object.getInt("difficulty")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Copy all topics in topics
        topics.addAll(topics_all);

    }

    @Override
    public String toString() {
        return "Repository{" +
                ", repo=" + repo +
                ", topics_all=" + topics_all +
                ", topics=" + topics +
                '}';
    }

}
