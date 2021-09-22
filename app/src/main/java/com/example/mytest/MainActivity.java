package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
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

    public void updateData(String data) {
        ArrayList<Coin> dataCoin = SimpleJson.getCoinArray(data);
        TableLayout tableLayout = findViewById(R.id.table);

        for (Coin coin : dataCoin) {
            tableLayout.addView(new ); // coin
        }
    }

    public void update() {
        updateData(getData());
    }
}