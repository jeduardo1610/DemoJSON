package com.example.m14x.demojson;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by m14x on 04/06/2016.
 */
public class DownloadTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo = jsonObject.getString("weather");
            Log.i("JEPB Weather content", weatherInfo);
            JSONArray jsonArray = new JSONArray(weatherInfo);

            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                Log.i("JEPB main",jsonPart.getString("main"));
                Log.i("JEPB description",jsonPart.getString("description"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
