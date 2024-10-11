package com.example.dynamiccurrencyconv;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputAmount;
    private Spinner inputCurrencySpinner, outputCurrencySpinner;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the UI elements
        inputAmount = findViewById(R.id.inputAmount);
        inputCurrencySpinner = findViewById(R.id.inputCurrencySpinner);
        outputCurrencySpinner = findViewById(R.id.outputCurrencySpinner);
        result = findViewById(R.id.result);
        Button convertButton = findViewById(R.id.convertButton);

        // Set up the Spinners with the currencies array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputCurrencySpinner.setAdapter(adapter);
        outputCurrencySpinner.setAdapter(adapter);

        // Set up the onClickListener for the Convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        String inputCurrency = inputCurrencySpinner.getSelectedItem().toString();
        String outputCurrency = outputCurrencySpinner.getSelectedItem().toString();
        String amountText = inputAmount.getText().toString();

        if (amountText.isEmpty()) {
            result.setText("Veuillez entrer un montant");
            return;
        }

        double amount = Double.parseDouble(amountText);
        double convertedAmount = 0.0;

        // Fixed conversion rates
        double tndToEur = 0.30;
        double tndToUsd = 0.34;

        if (inputCurrency.equals("Dinar")) {
            if (outputCurrency.equals("Euro")) {
                convertedAmount = amount * tndToEur;
            } else if (outputCurrency.equals("Dollar")) {
                convertedAmount = amount * tndToUsd;
            } else {
                convertedAmount = amount;
            }
        } else if (inputCurrency.equals("Euro")) {
            if (outputCurrency.equals("Dinar")) {
                convertedAmount = amount / tndToEur;
            } else if (outputCurrency.equals("Dollar")) {
                convertedAmount = amount * (tndToUsd / tndToEur);
            } else {
                convertedAmount = amount;
            }
        } else if (inputCurrency.equals("Dollar")) {
            if (outputCurrency.equals("Dinar")) {
                convertedAmount = amount / tndToUsd;
            } else if (outputCurrency.equals("Euro")) {
                convertedAmount = amount * (tndToEur / tndToUsd);
            } else {
                convertedAmount = amount;
            }
        }

        result.setText(String.format("%.2f", convertedAmount));
    }
}
