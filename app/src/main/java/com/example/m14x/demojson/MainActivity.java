package com.example.m14x.demojson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView weatherInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = (EditText) findViewById(R.id.cityName);
        weatherInformation = (TextView) findViewById(R.id.weatherInformation);

        findViewById(R.id.weatherButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = "";
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0); //hide the keyboard when the button is tapped
                city = (cityName.getText().toString()).replace(" ", "%20");
                DownloadTask task = new DownloadTask(weatherInformation,getApplicationContext());
                task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=2a72b61ea7a95fbb9d480ce2fdb50c02");

            }
        });


    }
}
