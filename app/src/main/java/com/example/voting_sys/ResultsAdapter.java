package com.example.voting_sys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private List<Candidate> list;
    private int totalVotes;

    public ResultsAdapter(List<Candidate> list, int totalVotes) {
        this.list = list;
        this.totalVotes = totalVotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Candidate c = list.get(position);
        holder.tvName.setText(c.getName());

        // حساب النسبة المئوية بأمان (لتجنب الكراش إذا كانت الأصوات 0)
        String percentage = "0%";
        if (totalVotes > 0) {
            int p = (c.getVotesCount() * 100) / totalVotes;
            percentage = p + "%";
        }

        holder.tvCount.setText(c.getVotesCount() + " Votes (" + percentage + ")");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // تأكد أن هذه الـ IDs تطابق الـ XML في الصورة image_83b26d.png
            tvName = itemView.findViewById(R.id.resNomCandidat);
            tvCount = itemView.findViewById(R.id.resCount);
        }
    }
}