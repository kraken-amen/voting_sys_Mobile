package com.example.voting_sys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Button btnStart = findViewById(R.id.btnStart);

        // في الـ EntryActivity.java
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // هذي تخلي صفحة الـ Entry تتسكر وتتحل الـ Main
        });
    }
}