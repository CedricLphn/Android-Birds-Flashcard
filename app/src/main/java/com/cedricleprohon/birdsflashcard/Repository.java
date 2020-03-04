package com.cedricleprohon.birdsflashcard;

import android.content.Context;
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
    private ArrayList<Integer> exclude;
    private JSONArray repo = null;
    private List<Topic> topics_all;
    public List<Topic> topics;

    private int idTopic;

    public Repository(Context context) {
        exclude = new ArrayList<>();
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

    public JSONObject get(int id) throws JSONException {
        return new JSONObject(repo.get(id).toString());
    }

    public String getRandomName() throws JSONException {
        return get(generateRandom(0, size())).getString("name");
    }

    public int generateRandom(int start, int end) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range) + 1;
        while(exclude.contains(random)) {
            random = rand.nextInt(range) + 1;
        }

        exclude.add(random);

        return random;
    }

    public Flashcard generateFlashcard(List<Topic> topics, Topic topic, int count) {
        Flashcard flashcard = new Flashcard(topic);

        Random rand = new Random();

       /* int range = 1 - count + 1;
        int random = rand.nextInt(count); */

        flashcard.answer.add(topic.name);

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

}
