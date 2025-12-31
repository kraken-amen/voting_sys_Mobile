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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. هذي لازم تكون أول سطر
        setContentView(R.layout.activity_main_voter);

        // 2. صلح الـ ID هنا (ثبت أنه موجود في الـ XML)
        if (findViewById(R.id.main) != null) {
            View mainView = findViewById(R.id.main);
            // كود الـ EdgeToEdge إذا موجود
        }

        // 3. ربط الـ RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCandidats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        candidateList = new ArrayList<>();

        // 4. الـ Firebase
        candidateRef = FirebaseDatabase.getInstance().getReference("Candidate");
        loadCandidates();
        // 1. تعريف الزر
        Button btnHome = findViewById(R.id.btnBackToHome);

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
    private void loadCandidates() {
        candidateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                candidateList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    try {
                        // التحقق من أن البيانات ليست نصاً بسيطاً قبل التحويل
                        if (snap.getValue() instanceof String) {
                            continue; // تخطي هذا السجل إذا كان نصاً
                        }

                        Candidate c = snap.getValue(Candidate.class);
                        if (c != null) {
                            candidateList.add(c);
                        }
                    } catch (Exception e) {
                        // تسجيل الخطأ في Logcat دون إغلاق التطبيق
                        Log.e("FirebaseError", "Error parsing candidate: " + e.getMessage());
                    }
                }
                // تحديث الـ Adapter
                if (adapter == null) {
                    adapter = new CandidateAdapter(candidateList, candidate -> {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("candidateId", candidate.getId());
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
