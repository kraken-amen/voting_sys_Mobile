package com.example.voting_sys;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<Candidate> list;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rv = findViewById(R.id.rvResults);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Candidate");
        loadResults();

        findViewById(R.id.btnBackEntry).setOnClickListener(v -> finish());
    }

    private void loadResults() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                int totalVotes = 0;
                for (DataSnapshot s : snapshot.getChildren()) {
                    Candidate c = s.getValue(Candidate.class);
                    if (c != null) {
                        list.add(c);
                        totalVotes += c.getVotesCount();
                    }
                }
                // سنقوم بتمرير إجمالي الأصوات للـ Adapter لحساب النسبة
                ResultsAdapter adapter = new ResultsAdapter(list, totalVotes);
                rv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}