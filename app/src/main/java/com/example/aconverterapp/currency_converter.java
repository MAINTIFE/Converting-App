package com.example.aconverterapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class currency_converter extends AppCompatActivity {
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        TextInputEditText textInputEditText = findViewById(R.id.textInput);
        Spinner spinnerFromCurrency = findViewById(R.id.spinner);
        Spinner spinnerToCurrency = findViewById(R.id.spinner2);
        Button converterButton = findViewById(R.id.convertCurrency);
        TextView textViewResult = findViewById(R.id.textView8);

        String[] currencies = {"USD", "EUR", "GBP", "CAD", "PLN", "NGN"}; // Currency examples
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromCurrency.setAdapter(adapter);
        spinnerToCurrency.setAdapter(adapter);

        converterButton.setOnClickListener(view -> {
            String fromCurrency = spinnerFromCurrency.getSelectedItem().toString();
            String toCurrency = spinnerToCurrency.getSelectedItem().toString();
            double amount = Double.parseDouble(Objects.requireNonNull(textInputEditText.getText()).toString());

            // Execute the currency conversion in a background thread
            convertCurrencyInBackground(fromCurrency, toCurrency, amount, textViewResult);
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void convertCurrencyInBackground(String fromCurrency, String toCurrency, double amount, TextView textViewResult) {
        executorService.execute(() -> {
            CurrencyConverter converter = new CurrencyConverter();
            double convertedAmount = converter.convertCurrency(fromCurrency, toCurrency, amount);

            mainHandler.post(() -> {
                if (convertedAmount != 0) {
                    textViewResult.setText(String.format("%.2f", convertedAmount));
                } else {
                    textViewResult.setText("Error in conversion");
                }
            });
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
