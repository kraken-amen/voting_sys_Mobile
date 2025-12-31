package com.example.voting_sys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private List<Candidate> candidateList;
    private int totalVotes;

    // Constructor يستقبل القائمة وإجمالي الأصوات للحساب
    public ResultsAdapter(List<Candidate> candidateList, int totalVotes) {
        this.candidateList = candidateList;
        this.totalVotes = totalVotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Candidate candidate = candidateList.get(position);

        // عرض الاسم وعدد الأصوات
        holder.tvName.setText(candidate.getName());
        holder.tvCount.setText(candidate.getVotesCount() + " Votes");

        // حساب النسبة المئوية (Data Analysis)
        if (totalVotes > 0) {
            int percentage = (candidate.getVotesCount() * 100) / totalVotes;
            holder.progressBar.setProgress(percentage);
        } else {
            holder.progressBar.setProgress(0);
        }
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.resNomCandidat);
            tvCount = itemView.findViewById(R.id.resCount);
        }
    }
}