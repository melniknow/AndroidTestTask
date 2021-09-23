package com.example.mytest.CurrencyData;

import android.widget.EditText;
import android.widget.Spinner;

import com.example.mytest.Data.Data;
import com.example.mytest.MainActivity;
import com.example.mytest.R;

import java.util.ArrayList;

public class Coin {
    private final String charCode;
    private final String name;
    private final String value;

    public Coin(String charCode, String name, String value) {
        this.charCode = charCode;
        this.name = name;
        this.value = value;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "charCode='" + charCode + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static String getSum(MainActivity mainActivity) {
        Spinner mySpinner = mainActivity.findViewById(R.id.spinner);
        EditText editText = mainActivity.findViewById(R.id.input);

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

    public static double getValueById(int indexBySymbol) {
        ArrayList<Coin> coinData = Data.getCoinData();
        return Double.parseDouble(coinData.get(indexBySymbol).getValue().replace(',', '.'));
    }

    public static int getIndexBySymbol(String item) {
        int ind = 0;
        ArrayList<Coin> coinData = Data.getCoinData();
        for (Coin coin : coinData) {
            if (coin.getName().equals(item)) return ind;
            ind++;
        }
        return -1;
    }
}
