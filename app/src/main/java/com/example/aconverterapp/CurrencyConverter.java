package com.example.aconverterapp;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_KEY = "1p23fpJ0a7dcCSItTv4dCnmKekcneZjF"; // Your actual API key
    private static final String API_URL = "https://api.apilayer.com/currency_data/convert";

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = API_URL + "?from=" + fromCurrency + "&to=" + toCurrency + "&amount=" + amount;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(responseData);
                return jsonObject.getDouble("result");  // "result" holds the converted amount according to the API documentation
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}