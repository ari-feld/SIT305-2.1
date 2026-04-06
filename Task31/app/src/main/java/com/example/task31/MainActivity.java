package com.example.task31;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        startButton = findViewById(R.id.startButton);
        SharedPreferences prefs = getSharedPreferences("QuizApp", MODE_PRIVATE);
        String savedName = prefs.getString("USERNAME", "");
        nameInput.setText(savedName);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            prefs.edit().putString("USERNAME", name).apply();
            Intent intent = new Intent(MainActivity.this, Question1Activity.class);
            intent.putExtra("SCORE", 0);
            startActivity(intent);
        });
    }
}