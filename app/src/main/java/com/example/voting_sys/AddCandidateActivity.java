package com.example.voting_sys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCandidateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        EditText etNom = findViewById(R.id.etNomCand);
        EditText etPres = findViewById(R.id.etPresentation);
        EditText etProg = findViewById(R.id.etProgram);
        Button btnSave = findViewById(R.id.btnRegisterCandidate);

        btnSave.setOnClickListener(v -> {
            // 1. جلب النصوص من الحقول وتخزينها في متغيرات
            String nom = etNom.getText().toString().trim();
            String pres = etPres.getText().toString().trim();
            String prog = etProg.getText().toString().trim();

            // التأكد من أن الحقول ليست فارغة قبل الإرسال
            if (nom.isEmpty() || pres.isEmpty() || prog.isEmpty()) {
                Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Candidate");
            String id = ref.push().getKey();

            // 2. تمرير البيانات للكائن (Object)
            // ملاحظة: افترضنا هنا أن لديك Constructor في كلاس Candidate يأخذ هذه القيم
            Candidate newCandidate = new Candidate(id, nom, pres, prog, 0);

            // 3. إرسال الكائن كاملاً إلى Firebase
            if (id != null) {
                ref.child(id).setValue(newCandidate).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Candidat ajouté !", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
        // 1. تعريف الزر
        Button btnHome = findViewById(R.id.btBackToHome);

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
}