package com.example.voting_sys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CandidateAdapter adapter;
    private List<Candidate> candidateList;
    private DatabaseReference candidateRef;
    private String userCIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voter);

        userCIN = getIntent().getStringExtra("userCIN");

        // recyclerView
        recyclerView = findViewById(R.id.recyclerViewCandidats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        candidateList = new ArrayList<>();

        // Firebase
        candidateRef = FirebaseDatabase.getInstance().getReference("Candidate");
        loadCandidates();

        Button btnHome = findViewById(R.id.btnBackToHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, EntryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadCandidates() {
        candidateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                candidateList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    try {
                        if (snap.getValue() instanceof String) {
                            continue;
                        }

                        Candidate c = snap.getValue(Candidate.class);
                        if (c != null) {
                            candidateList.add(c);
                        }
                    } catch (Exception e) {
                        Log.e("FirebaseError", "Error parsing candidate: " + e.getMessage());
                    }
                }

                // modification adapter
                if (adapter == null) {
                    adapter = new CandidateAdapter(candidateList, candidate -> {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("candidateId", candidate.getId());
                        // extra cin
                        intent.putExtra("userCIN", userCIN);

                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}