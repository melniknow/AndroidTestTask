package com.example.mytest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.mytest.CurrencyData.Coin;
import com.example.mytest.Parser.SimpleJson;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        if (sharedPreferences.getString("data", "unknown").equals("unknown")) {
            update();
        } else {
            String temp = sharedPreferences.getString("data", "unknown");
            SimpleJson.updateData(temp, this);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        SimpleJson.updateData(SimpleJson.getData(MainActivity.this), this);
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        TextView textViewRes = findViewById(R.id.result);
        textViewRes.setText("Result: " + Coin.getSum(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickUpdate(View view) {
        update();
    }
}