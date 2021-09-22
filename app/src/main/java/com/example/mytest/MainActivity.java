package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mytest.Parser.SimpleJson;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String WEATHER_URL =
                "http://api.openweathermap.org/data/2.5/weather?q=London,uk" +
                        "&units=metric&appid=241de9349721df959d8800c12ca4f1f3";

        URL url = SimpleJson.createUrl(WEATHER_URL);

        // загружаем Json в виде Java строки
        String resultJson = SimpleJson.parseUrl(url);
        System.out.println("Полученный JSON:\n" + resultJson);

        // парсим полученный JSON и печатаем его на экран
        SimpleJson.parseCurrentWeatherJson(resultJson);

        // формируем новый JSON объект из нужных нам погодных данных
        String json = SimpleJson.buildWeatherJson();
        System.out.println("Созданный нами JSON:\n" + json);
    }
}