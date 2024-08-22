package com.example.aconverterapp;

import java.util.Map;

public class ExchangeRatesResponse {
    private String source;
    private Map<String, Double> quotes;

    public String getSource() {
        return source;
    }

    public Map<String, Double> getRates() {
        return quotes;
    }
}
