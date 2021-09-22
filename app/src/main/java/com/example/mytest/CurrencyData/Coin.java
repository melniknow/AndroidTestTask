package com.example.mytest.CurrencyData;

public class Coin {
    private final String charCode;
    private final String name;
    private final double value;

    public Coin(String charCode, String name, double value) {
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

    public double getValue() {
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
