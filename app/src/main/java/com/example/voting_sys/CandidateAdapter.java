package com.example.voting_sys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voting_sys.Candidate;
import com.example.voting_sys.R;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {

    private List<Candidate> list;
    private OnCandidateClickListener listener;

    public interface OnCandidateClickListener {
        void onCandidateClick(Candidate candidate);
    }

    public CandidateAdapter(List<Candidate> list, OnCandidateClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_candidate, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Candidate c = list.get(position);
        h.txtNom.setText(c.getName());
        h.txtDesc.setText(c.getPresentation());

        // ✅ الزر هو اللي يفتح التفاصيل
        h.btnDetails.setOnClickListener(v -> listener.onCandidateClick(c));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNom, txtDesc;
        Button btnDetails;

        ViewHolder(View v) {
            super(v);
            txtNom = v.findViewById(R.id.txtNomCandidat);
            txtDesc = v.findViewById(R.id.txtDescriptionCourte);
            btnDetails = v.findViewById(R.id.btnVoirDetails);
        }
    }
}
