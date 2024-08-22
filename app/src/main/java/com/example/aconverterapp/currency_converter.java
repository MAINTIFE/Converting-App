package com.example.aconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class currency_converter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        TextInputEditText textInputEditText = findViewById(R.id.textInput);
        Spinner spinnerFromCurrency = findViewById(R.id.spinner);
        Spinner spinnerToCurrency = findViewById(R.id.spinner2);
        Button converterButton = findViewById(R.id.convertCurrency);
        TextView textViewResult = findViewById(R.id.textView8);

        String[] currencies = {"USD", "EUR", "GBP", "CAD", "PLN", "NGN"}; // currency examples
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromCurrency.setAdapter(adapter);
        spinnerToCurrency.setAdapter(adapter);



        converterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currency = textInputEditText.getText().toString();
                double value = 0.0;

                if (!currency.isEmpty()){
                    value = Double.parseDouble(currency);

                    String fromCurrency = spinnerFromCurrency.getSelectedItem().toString();
                    String toCurrency = spinnerToCurrency.getSelectedItem().toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://apilayer.net/api/")  // Base URL of the API
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    currencyAPI currencyApi = retrofit.create(currencyAPI.class);
                    String apiKey = "5040456495edc7a102c1d0e94414739b";
                    String currencies = "EUR,GBP,CAD,PLN,NGN";
                    String source = "USD";
                    int format = 1;

                    Call<ExchangeRatesResponse> call = currencyApi.getExchangeRates(apiKey, currencies, source, format); // Fetch rates based on USD
                    double finalValue = value;
                    call.enqueue(new Callback<ExchangeRatesResponse>() {
                        @Override
                        public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                            if (response.isSuccessful()) {
                                ExchangeRatesResponse ratesResponse = response.body();
                                if (ratesResponse != null) {
                                    Map<String, Double> rates = ratesResponse.getRates();

                                    if (rates != null) {
                                        Double toRate = rates.get(toCurrency); // Use Double instead of double to allow null checking
                                        Double fromRate = rates.get(fromCurrency);
                                        if (fromRate != null && toRate != null) {
                                            double conversionRate = toRate/fromRate;
                                            double convertedValue = finalValue * conversionRate; // Perform the conversion
                                            textViewResult.setText(String.format("%.2f", convertedValue));
                                        } else {
                                            Toast.makeText(currency_converter.this, "Currency conversion rate not found", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(currency_converter.this, "Failed to retrieve currency rates", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(currency_converter.this, "Failed to get a valid response from the server", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(currency_converter.this, "Failed to get exchange rates", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
                            // Handle the error
                            Toast.makeText(currency_converter.this, "Failed to get exchange rates", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(currency_converter.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }
            }

//            public double getExchangeRate (String fromCurrency, String toCurrency){
////                examples of exchange rates that will be constant
//
//                if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
//                    return 0.91;
//                } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
//                    return 1.10;
//                } else if (fromCurrency.equals("USD") && toCurrency.equals("NGN")) {
//                    return 1590;
//                } else if (fromCurrency.equals("NGN") && toCurrency.equals("USD")) {
//                    return 0.00063;
//                } else if (fromCurrency.equals("EUR") && toCurrency.equals("NGN")) {
//                    return 1752.30;
//                } else if (fromCurrency.equals("NGN") && toCurrency.equals("EUR")) {
//                    return 0.00057;
//                }
////                if fromCurrency and toCurrency are the same it will return 1.0
//
//                return 1.0;
//            }
//
//            public double convertCurrency(double value, String fromCurrency, String toCurrency){
//                double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
//                return value * exchangeRate;
//            }

        });

    }

}