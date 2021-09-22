package com.example.mytest.Parser;

import android.os.AsyncTask;

import com.example.mytest.Data.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
}

