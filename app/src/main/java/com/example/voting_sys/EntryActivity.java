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

        // تعريف الأزرار حسب الـ IDs الجديدة في الـ Layout
        Button btnElecteur = findViewById(R.id.btnRoleElecteur);
        Button btnCandidat = findViewById(R.id.btnRoleCandidat);
        Button btnResults = findViewById(R.id.btnViewResults);

        // 1. مسار الناخب (يطلب الـ CIN أولاً)
        btnElecteur.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, ElecteurLoginActivity.class);
            startActivity(intent);
        });

        // 2. مسار المترشح (يذهب لصفحة إضافة البيانات)
        btnCandidat.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, AddCandidateActivity.class);
            startActivity(intent);
        });

        // 3. مسار النتائج (يذهب لصفحة الإحصائيات)
        btnResults.setOnClickListener(v -> {
            Intent intent = new Intent(EntryActivity.this, ResultsActivity.class);
            startActivity(intent);
        });
    }
}