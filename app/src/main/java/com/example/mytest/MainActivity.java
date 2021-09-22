package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mytest.CurrencyData.Coin;
import com.example.mytest.Data.Data;
import com.example.mytest.Parser.SimpleJson;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final URL url = SimpleJson.createUrl("https://www.cbr-xml-daily.ru/daily_json.js");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();
    }

    public String getData() {
        new SimpleJson().execute(url);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Data.getJsonData() == null) throw new NullPointerException("Parse Error");
        return Data.getJsonData();
    }

    // Code
    public void updateData(String data) {
        ArrayList<Coin> dataCoin = SimpleJson.getCoinArray(data);

        StringBuilder stringBuilder = new StringBuilder();
        for (Coin coin : dataCoin) {
            stringBuilder.append(coin);
            stringBuilder.append('\n');
        }

        TextView textView = findViewById(R.id.data);
        textView.append(stringBuilder.toString());
    }

    public void update() {
        updateData(getData());
    }
}