package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mytest.CurrencyData.Coin;
import com.example.mytest.Data.Data;
import com.example.mytest.Parser.SimpleJson;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        List<Coin> dataCoin = new ArrayList<>();

        TextView textView = findViewById(R.id.data);

        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        if ((data != null) && !(data.isEmpty())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(data);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }

        JSONObject valute = (JSONObject) jsonObject.get("Valute");
        assert valute != null;

        for (Object object : valute.keySet()) {
            JSONObject localValute = (JSONObject) valute.get((String) object);
            String name = (String) localValute.get("Name");
            String charCode = (String) localValute.get("CharCode");
            double value = (double) localValute.get("Value");

            dataCoin.add(new Coin(name, charCode, value));
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Coin coin : dataCoin) {
            stringBuilder.append(coin);
            stringBuilder.append('\n');
        }

        textView.append(stringBuilder.toString());
    }

    public void update() {
        updateData(getData());
    }
}