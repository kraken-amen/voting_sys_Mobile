package com.example.voting_sys;

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
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Candidate");
            String id = ref.push().getKey(); // يعمل ID جديد أوتوماتيك

            // صنع Object جديد
            Candidate newCandidate = new Candidate();
            // ملاحظة: لازم تزيد Setters في كلاس Candidate أو تخليهم public
            // هنا نفترض أنك زدتهم باش تبعث البيانات لـ Firebase

            ref.child(id).setValue(newCandidate).addOnSuccessListener(aVoid -> {
                Toast.makeText(this, "Candidat ajouté !", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}