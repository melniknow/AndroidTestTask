package com.example.mytest.CurrencyData;

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
}
