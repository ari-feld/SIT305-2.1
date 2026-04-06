package com.example.task31;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    TextView resultText;
    Button restartButton;
    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultText = findViewById(R.id.resultsText);
        restartButton = findViewById(R.id.restartButton);
        finishButton = findViewById(R.id.finishButton);

        int score = getIntent().getIntExtra("SCORE", 0);

        SharedPreferences prefs = getSharedPreferences("QuizApp", MODE_PRIVATE);
        String username = prefs.getString("USERNAME", "Player");

        resultText.setText("Congratulations " + username + "\nYour Score is: " + score + "/5!");

        restartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        finishButton.setOnClickListener(v -> {
            finishAffinity(); // Closes App
        });

    }
}