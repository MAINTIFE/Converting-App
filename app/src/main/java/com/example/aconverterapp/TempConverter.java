package com.example.aconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class TempConverter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_converter);

        EditText editText = findViewById(R.id.editTextBox);
        RadioButton radioButton1 = findViewById(R.id.FarenheitRadioButton);
        RadioButton radioButton2 = findViewById(R.id.CelciusRadioButton);
        Button button = (Button) findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView3);

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String temperature = editText.getText().toString();
                                          double value = 0.0;

                                          if (!temperature.isEmpty()){
                                              value = Double.parseDouble(temperature);
                                          }else {
                                              Toast.makeText(TempConverter.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                                          }
                                          Toast.makeText(TempConverter.this, String.valueOf(value),Toast.LENGTH_SHORT).show();
                                          double result1;
                                          double result2;

                                          if(radioButton1.isChecked()){
                                              result1 = (((double) 9 /5 * value) + 32);
                                              textView.setText(String.valueOf(result1));
                                          } else if(radioButton2.isChecked()){
                                              result2 = ((double) 5 /9 * (value - 32));
//                                              Toast.makeText(TempConverter.this, String.valueOf(result2),Toast.LENGTH_SHORT).show();
                                              textView.setText(String.valueOf(result2));
                                          }
                                      }
                                  }
        );

    }
}