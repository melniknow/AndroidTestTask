package com.example.mytest.Parser;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.mytest.Data.Data;
import com.example.mytest.R;

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
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("66666666");
            e.printStackTrace();
        }
        System.out.println("OK");
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s);
        Data.jsonData = s;
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

