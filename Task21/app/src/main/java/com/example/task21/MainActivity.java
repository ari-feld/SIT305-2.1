package com.example.task21;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinnerType = findViewById(R.id.spinnerType);
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);

        //Set Up Type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.conversion_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();

                int arrayResourceTo;
                int arrayResourceFrom;

                switch (selectedType) {
                    case "Currency":
                        arrayResourceTo = R.array.currency;
                        arrayResourceFrom = R.array.currency;
                        break;
                    case "Fuel Efficiency":
                        arrayResourceTo = R.array.fuel;
                        arrayResourceFrom = R.array.fuel;
                        break;
                    case "Volume":
                        arrayResourceTo = R.array.volume;
                        arrayResourceFrom = R.array.volume;
                        break;
                    case "Distance":
                        arrayResourceTo = R.array.distance;
                        arrayResourceFrom = R.array.distance;
                        break;
                    case "Temperature":
                        arrayResourceTo = R.array.temperature_units;
                        arrayResourceFrom = R.array.temperature_units;
                        break;
                    default:
                        arrayResourceTo = R.array.no_options;
                        arrayResourceFrom = R.array.no_options;
                }

                ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(
                        MainActivity.this,
                        arrayResourceTo,
                        android.R.layout.simple_spinner_item);
                adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFrom.setAdapter(adapterFrom);

                ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(
                        MainActivity.this,
                        arrayResourceFrom,
                        android.R.layout.simple_spinner_item);
                adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTo.setAdapter(adapterTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ;

        //Data Conversions
        EditText inputValue = findViewById(R.id.inputValue);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        convertButton.setOnClickListener(v -> {
            String valueStr = inputValue.getText().toString();

            if (valueStr.isBlank()) {
                resultText.setText("Please enter a value");
                return;
            }

            double value = Double.parseDouble(valueStr);

            String type = spinnerType.getSelectedItem().toString();
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            double result = convert(type, from, to, value);

            resultText.setText(String.valueOf(result));

        });
    }

    private double convert(String type, String from, String to, Double value) {
        switch (type) {
            case "Currency":
                //Convert to USD for simplicity
                double USDollars = value;
                if (from.equals("USD")) USDollars = value;
                if (from.equals("AUD")) USDollars = value / 1.55;
                if (from.equals("EUR")) USDollars = value / 0.92;
                if (from.equals("JPY")) USDollars = value / 148.50;
                if (from.equals("GBP")) USDollars = value / 0.78;

                if (to.equals("USD")) return USDollars;
                if (to.equals("AUD")) return USDollars * 1.55;
                if (to.equals("EUR")) return USDollars * 0.92;
                if (to.equals("JPY")) return USDollars * 148.50;
                if (to.equals("GBP")) return USDollars * 0.78;

            case "Fuel Efficiency":
                if (from.equals("MPG") && to.equals("km/L")) return value * 0.425;
                if (from.equals("km/L") && to.equals("MPG")) return value / 0.425;
                return value;

            case "Volume":
                if (from.equals("Gallon (US)") && to.equals("Liters")) return value * 3.785;
                if (from.equals("Liters") && to.equals("Gallon (US)")) return value / 3.785;
                return value;

            case "Distance":
                if (from.equals("Nautical Miles") && to.equals("Kilometers")) return value * 1.852;
                if (from.equals("Kilometers") && to.equals("Nautical Miles")) return value / 1.852;
                return value;

            case "Temperature":
                //Conver to celcius for simplicity
                double celsius = value;

                if (from.equals("Celsius")) celsius = value;
                if (from.equals("Fahrenheit")) celsius = (value - 32) * (5/9);
                if (from.equals("Kelvin")) celsius = value - 273.15;

                if (to.equals("Celsius")) return celsius;
                if (to.equals("Fahrenheit")) return (celsius * 9/5) +32;
                if (to.equals("Kelvin")) return celsius + 273.15;
                return value;
        }
        return value;
    }
}