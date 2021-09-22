package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mytest.Data.Data;
import com.example.mytest.Parser.SimpleJson;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL url = SimpleJson.createUrl("https://www.cbr-xml-daily.ru/daily_json.js");
        new SimpleJson().execute(url);

        TextView textView = findViewById(R.id.data);

        while (Data.getJsonData() == null) {

        }

        textView.append(Data.getJsonData());
    }
}