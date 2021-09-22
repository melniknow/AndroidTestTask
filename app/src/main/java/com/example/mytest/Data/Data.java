package com.example.mytest.Data;

public class Data {
    private static volatile String jsonData;

    public static synchronized void setJsonData(String value) {
        jsonData = value;
    }

    public static synchronized String getJsonData() {
        return jsonData;
    }
}
