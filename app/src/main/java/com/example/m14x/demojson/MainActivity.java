package com.example.m14x.demojson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText cityName;
    TextView weatherInformation;
    Button weatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = (EditText) findViewById(R.id.cityName);
        weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(this);
        weatherInformation = (TextView) findViewById(R.id.weatherInformation);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("weather", weatherInformation.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        weatherInformation.setText(savedInstanceState.getString("weather"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weatherButton:
                String city = "";
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0); //hide the keyboard when the button is tapped
                try {
                    city = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
                    DownloadTask task = new DownloadTask(weatherInformation,getApplicationContext());
                    task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=2a72b61ea7a95fbb9d480ce2fdb50c02");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
