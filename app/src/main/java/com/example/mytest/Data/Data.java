package com.example.mytest.Data;

import com.example.mytest.CurrencyData.Coin;

import java.util.ArrayList;

public class Data {
    private static volatile String jsonData;
    private static volatile ArrayList<Coin> coinData;

    public static synchronized void setJsonData(String value) {
        jsonData = value;
    }

    public static synchronized String getJsonData() {
        return jsonData;
    }

    public static synchronized void setCoinData(ArrayList<Coin> value) {
        coinData = value;
    }

    public static synchronized ArrayList<Coin> getCoinData() {
        return coinData;
    }
}
