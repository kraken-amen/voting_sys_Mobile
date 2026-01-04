package com.example.voting_sys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtName, txtProgram;
    private Button btnVote;
    private DatabaseReference candidateRef;
    private String candidateId;
    private String userCIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        userCIN = getIntent().getStringExtra("userCIN");
        candidateId = getIntent().getStringExtra("candidateId");

        txtName = findViewById(R.id.detNom);
        txtProgram = findViewById(R.id.detProgramme);
        btnVote = findViewById(R.id.btnVoteNow);
        Button btnBack = findViewById(R.id.btnBackEntr);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInAnonymously();
        }

        if (candidateId != null) {
            candidateRef = FirebaseDatabase.getInstance()
                    .getReference("Candidate")
                    .child(candidateId);
            loadCandidateDetails();
        }

        btnVote.setOnClickListener(v -> vote());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("userCIN", userCIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void loadCandidateDetails() {
        candidateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Candidate c = snapshot.getValue(Candidate.class);
                    if (c != null) {
                        txtName.setText(c.getName());
                        txtProgram.setText(c.getProgram());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailsActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //verf (if sommeone try me)
    private void vote() {
        if (userCIN == null || userCIN.isEmpty()) {
            Toast.makeText(this, "Erreur: CIN introuvable !", Toast.LENGTH_SHORT).show();
            return;
        }

        candidateRef.child("votesCount").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Integer votes = currentData.getValue(Integer.class);
                if (votes == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue(votes + 1);
                }
                return Transaction.success(currentData);
            }
            //if err
            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot snapshot) {
                if (committed) {
                    saveVoterRecord();
                } else {
                    Toast.makeText(DetailsActivity.this, "Échec du vote", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveVoterRecord() {
        DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("Votes");
        votesRef.child(userCIN).setValue(true).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Vote enregistré avec succès !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ResultsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}