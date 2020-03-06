package com.cedricleprohon.birdsflashcard;


import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BirdsCloud {
    private final OkHttpClient client = new OkHttpClient();

    public String data;

    public JSONArray toJSON() {
        JSONArray JSONArray = null;
        try {
            JSONArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return JSONArray;
    }

    BirdsCloud() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request = new Request.Builder()
                .url(BirdUtils.DATABASE.toString())
                .build();

        try(Response response = client.newCall(request).execute()) {
            data = response.body().string();

        }
    }

}
