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

    private void loadCandidateDetails() {
        candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtName.setText(snapshot.child("name").getValue(String.class));
                    txtProgram.setText(snapshot.child("program").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void vote() {
        candidateRef.child("votesCount")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer votes = snapshot.getValue(Integer.class);
                        if (votes == null) votes = 0;

                        candidateRef.child("votesCount").setValue(votes + 1);
                        Toast.makeText(DetailsActivity.this,
                                "Vote enregistré ✅",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
