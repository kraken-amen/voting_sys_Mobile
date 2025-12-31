package com.example.voting_sys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtName, txtProgram;
    private Button btnVote;
    private DatabaseReference candidateRef;
    private String candidateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtName = findViewById(R.id.detNom);
        txtProgram = findViewById(R.id.detProgramme);
        btnVote = findViewById(R.id.btnVoteNow);

        // Auth anonymous (باش كل user يصوّت مرة)
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInAnonymously();
        }

        candidateId = getIntent().getStringExtra("candidateId");

        candidateRef = FirebaseDatabase
                .getInstance()
                .getReference("Candidate")
                .child(candidateId);

        loadCandidateDetails();

        btnVote.setOnClickListener(v -> vote());
    }

    // أضف هذا المتغير في أعلى الكلاس
    private int currentVotes = 0;

    // داخل onCreate، في الجزء الذي تجلب فيه البيانات، خذ قيمة votesCount
    private void loadCandidateDetails() {
        candidateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Candidate c = snapshot.getValue(Candidate.class);
                    if (c != null) {
                        txtName.setText(c.getName());
                        txtProgram.setText(c.getProgram());
                        currentVotes = c.getVotesCount(); // تخزين القيمة الحالية
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void vote() {
        String userCIN = getIntent().getStringExtra("userCIN");

        // تحديث عدد الأصوات في مسار المترشح
        candidateRef.child("votesCount").setValue(currentVotes + 1)
                .addOnSuccessListener(aVoid -> {
                    // تسجيل الـ CIN في جدول مستقل لمنع تكرار التصويت
                    DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("Votes");
                    votesRef.child(userCIN).setValue(true);

                    Toast.makeText(this, "Vote enregistré avec succès !", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
