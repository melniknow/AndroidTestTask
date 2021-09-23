package com.example.mytest.Parser;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



import androidx.annotation.RequiresApi;

import com.example.mytest.CurrencyData.Coin;
import com.example.mytest.Data.Data;
import com.example.mytest.MainActivity;
import com.example.mytest.R;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class SimpleJson extends AsyncTask<URL, Void, String> {

    private static final URL url = SimpleJson.createUrl("https://www.cbr-xml-daily.ru/daily_json.js");

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<Coin> getCoinArray(String data) {
        ArrayList<Coin> dataCoin = new ArrayList<>();


        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        if ((data != null) && !(data.isEmpty())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        JSONObject valute = (JSONObject) jsonObject.get("Valute");
        assert valute != null;

        for (Object object : valute.keySet()) {
            JSONObject localValute = (JSONObject) valute.get((String) object);
            String name = (String) localValute.get("Name");
            String charCode = (String) localValute.get("CharCode");
            double value = ((double) localValute.get("Value"));
            String res = String.format("%.2f", value);

            dataCoin.add(new Coin(name, charCode, res));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataCoin.sort(Comparator.comparing(Coin::getCharCode));
        }
        return dataCoin;
    }

    public static String getData(MainActivity mainActivity) {
        new SimpleJson().execute(url);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Data.getJsonData() == null) throw new NullPointerException("Parse Error");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("data");
        editor.putString("data", Data.getJsonData());
        editor.apply();

        return Data.getJsonData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void updateData(String data, MainActivity mainActivity) {
        ArrayList<Coin> dataCoin = SimpleJson.getCoinArray(data);
        ArrayList<String> coins = new ArrayList<>();

        TableLayout tableLayout = mainActivity.findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();

        TextView textView = mainActivity.findViewById(R.id.choose);
        textView.setTypeface(null, Typeface.BOLD);

        TextView textViewRes = mainActivity.findViewById(R.id.result);
        textViewRes.setTypeface(null, Typeface.BOLD);

        TableRow tableRowOther = new TableRow(mainActivity);
        tableRowOther.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView textView_1 = new TextView(mainActivity);
        TextView textView_2 = new TextView(mainActivity);
        TextView textView_3 = new TextView(mainActivity);

        textView_1.append("Name");
        textView_2.append("Symbol");
        textView_3.append("Value");

        textView_1.setTypeface(null, Typeface.BOLD);
        textView_2.setTypeface(null, Typeface.BOLD);
        textView_3.setTypeface(null, Typeface.BOLD);


        tableRowOther.addView(textView_1);
        tableRowOther.addView(textView_2);
        tableRowOther.addView(textView_3);

        tableRowOther.setPadding(0, 10, 0, 30);

        tableLayout.addView(tableRowOther, 0);

        int k = 2;

        for (int i = 0; i < dataCoin.size(); i++) {
            TableRow tableRow = new TableRow(mainActivity);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView textView1 = new TextView(mainActivity);
            TextView textView2 = new TextView(mainActivity);
            TextView textView3 = new TextView(mainActivity);

            coins.add(dataCoin.get(i).getName());

            textView1.append(dataCoin.get(i).getCharCode());
            textView2.append(dataCoin.get(i).getName() + "             ");
            textView3.append(dataCoin.get(i).getValue());

            textView1.setTypeface(Typeface.SANS_SERIF);
            textView2.setTypeface(Typeface.SANS_SERIF);
            textView3.setTypeface(Typeface.SANS_SERIF);

            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableRow.addView(textView3);

            tableRow.setPadding(0, 10, 0, 10);

            View line = new View(mainActivity);
            line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            line.setBackgroundColor(Color.rgb(0, 0, 0));

            tableLayout.addView(line, k - 1);

            tableLayout.addView(tableRow, k);
            k += 2;
        }

        Data.setCoinData(dataCoin);

        // ---------------------------------
        Spinner spinner = mainActivity.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_spinner_item, coins);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}

