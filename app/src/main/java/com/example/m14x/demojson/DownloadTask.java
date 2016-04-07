package com.example.m14x.demojson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView weatherData;
    private Context context;

    public  DownloadTask (TextView data, Context context){
        this.weatherData = data;
        this.context = context;
    }
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
        } catch (Exception e) {
            Toast.makeText(context,"Could not find weather", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            String main = "";
            String description = "";
            String msg = "";
            JSONObject jsonObject = new JSONObject(result);
            String weatherInfo = jsonObject.getString("weather");
            //Log.i("JEPB Weather content", weatherInfo);
            JSONArray jsonArray = new JSONArray(weatherInfo);

            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                main = jsonPart.getString("main");
                description = jsonPart.getString("description");

                if(main != "" && description != ""){
                    msg += main + ": " + description + "\r\n";
                }

            }

            if(msg != ""){
               this.weatherData.setText(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"City not specified", Toast.LENGTH_LONG).show();
        }


    }
}
