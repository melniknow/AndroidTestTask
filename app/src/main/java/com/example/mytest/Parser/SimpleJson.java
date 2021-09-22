package com.example.mytest.Parser;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.mytest.CurrencyData.Coin;
import com.example.mytest.Data.Data;
import com.example.mytest.R;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleJson extends AsyncTask<URL, Void, String> {

    @Override
    public String doInBackground(URL... voids) {
        if (voids == null || voids.length != 1 || voids[0] == null) {
            return "";
        }
        URL url = voids[0];
        StringBuilder stringBuilder = new StringBuilder();
        // открываем соедиение к указанному URL
        // помощью конструкции try-with-resources
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            // построчно считываем результат в объект StringBuilder
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Data.setJsonData(stringBuilder.toString());

        return stringBuilder.toString();
    }

    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Coin> getCoinArray(String data) {
        ArrayList<Coin> dataCoin = new ArrayList<>();


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

        return dataCoin;
    }
}

