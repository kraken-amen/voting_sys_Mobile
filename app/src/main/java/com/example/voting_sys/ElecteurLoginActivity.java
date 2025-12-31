package com.example.voting_sys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElecteurLoginActivity extends AppCompatActivity {

    private EditText etCIN;
    private Button btnVerifyCIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electeur_login);

        etCIN = findViewById(R.id.etCIN);
        btnVerifyCIN = findViewById(R.id.btnVerifyCIN);

        btnVerifyCIN.setOnClickListener(v -> verifyAndProceed());
        // 1. تعريف الزر
        Button btnHome = findViewById(R.id.bBackToHome);

// 2. برمجة الضغطة
        btnHome.setOnClickListener(v -> {
            // الانتقال إلى الصفحة الرئيسية
            Intent intent = new Intent(this, EntryActivity.class);

            // هذه الأعلام (Flags) تضمن إغلاق كل الصفحات القديمة وفتح الصفحة الرئيسية كأنها جديدة
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish(); // إغلاق الصفحة الحالية
        });
    }

    private void verifyAndProceed() {
        String cin = etCIN.getText().toString().trim();

        // التثبت من طول الـ CIN (8 أرقام)
        if (cin.length() != 8) {
            etCIN.setError("Le CIN doit contenir 8 chiffres");
            return;
        }

        // البحث في عقدة "Votes" للتأكد من عدم تكرار التصويت
        DatabaseReference voteCheck = FirebaseDatabase.getInstance().getReference("Votes").child(cin);

        voteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // إذا الـ CIN موجود، يعني صوّت قبل
                    Toast.makeText(ElecteurLoginActivity.this,
                            "Désolé, ce numéro CIN a déjà participé au vote !",
                            Toast.LENGTH_LONG).show();
                } else {
                    // إذا موش موجود، يتعدى لصفحة القائمة ويبعث الـ CIN معاه
                    Intent intent = new Intent(ElecteurLoginActivity.this, MainActivity.class);
                    intent.putExtra("userCIN", cin);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ElecteurLoginActivity.this, "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}