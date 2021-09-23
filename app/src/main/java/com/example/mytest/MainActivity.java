package com.example.mytest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
            updateData(temp);
        }
    }

    public String getData() {
        new SimpleJson().execute(url);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Data.getJsonData() == null) throw new NullPointerException("Parse Error");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("data");
        editor.putString("data", Data.getJsonData());
        editor.apply();

        return Data.getJsonData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateData(String data) {
        ArrayList<Coin> dataCoin = SimpleJson.getCoinArray(data);
        ArrayList<String> coins = new ArrayList<>();

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();

        TextView textView = findViewById(R.id.choose);
        textView.setTypeface(null, Typeface.BOLD);

        TextView textViewRes = findViewById(R.id.result);
        textViewRes.setTypeface(null, Typeface.BOLD);

        TableRow tableRowOther = new TableRow(this);
        tableRowOther.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView textView_1 = new TextView(this);
        TextView textView_2 = new TextView(this);
        TextView textView_3 = new TextView(this);

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
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView textView1 = new TextView(this);
            TextView textView2 = new TextView(this);
            TextView textView3 = new TextView(this);

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

            View line = new View(this);
            line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            line.setBackgroundColor(Color.rgb(0, 0, 0));

            tableLayout.addView(line, k - 1);

            tableLayout.addView(tableRow, k);
            k += 2;
        }

        Data.setCoinData(dataCoin);

        // ---------------------------------
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coins);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        updateData(getData());
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        TextView textViewRes = findViewById(R.id.result);
        textViewRes.setText("Result: " + getSum());
    }

    public String getSum() {
        Spinner mySpinner = findViewById(R.id.spinner);
        EditText editText = findViewById(R.id.input);

        double coinValue = getValueById(getIndexBySymbol(mySpinner.getSelectedItem().toString()));
        double userValue;

        try {
            if (editText.getText().toString().startsWith("0")) return "";
            userValue = Double.parseDouble(editText.getText().toString());
        } catch (Exception e) {
            return "";
        }

        double res = userValue / coinValue;

        return String.format("%.2f", res);
    }

    private double getValueById(int indexBySymbol) {
        ArrayList<Coin> coinData = Data.getCoinData();
        System.out.println(coinData.get(indexBySymbol).getValue().replace(',', '.')); //
        return Double.parseDouble(coinData.get(indexBySymbol).getValue().replace(',', '.'));
    }

    private int getIndexBySymbol(String item) {
        int ind = 0;
        ArrayList<Coin> coinData = Data.getCoinData();
        for (Coin coin : coinData) {
            if (coin.getName().equals(item)) return ind;
            ind++;
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickUpdate(View view) {
        update();
    }
}