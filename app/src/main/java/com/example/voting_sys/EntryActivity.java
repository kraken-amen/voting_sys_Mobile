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

        Button btnElecteur = findViewById(R.id.btnRoleElecteur);
        Button btnCandidat = findViewById(R.id.btnRoleCandidat);
        Button btnResults = findViewById(R.id.btnViewResults);

        btnElecteur.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, ElecteurLoginActivity.class);
            startActivity(intent);
        });

        btnCandidat.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, AddCandidateActivity.class);
            startActivity(intent);
        });

        btnResults.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, ResultsActivity.class);
            startActivity(intent);
        });
    }
}