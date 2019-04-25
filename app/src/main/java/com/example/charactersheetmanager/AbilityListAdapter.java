package com.example.charactersheetmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AbilityListAdapter extends RecyclerView.Adapter<AbilityListAdapter.ViewHolder> {

    private List<AbilityFragment> listAbilities;
    private Context context;

    public AbilityListAdapter(List<AbilityFragment> listAbilities, Context context) {
        this.listAbilities = listAbilities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_abilitiy_fragment, parent,false);
        return new AbilityListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final AbilityFragment abilityFragment = listAbilities.get(i);

        if (abilityFragment.getLevel() == 0) {
            String levelText = "Racial Feature";

            viewHolder.tvTitle.setText(abilityFragment.getTitle());
            viewHolder.tvDescription.setText(abilityFragment.getDescription());
            viewHolder.tvLevel.setText(levelText);
        }

        else {
            String levelText = "Level " + abilityFragment.getLevel();

            viewHolder.tvTitle.setText(abilityFragment.getTitle());
            viewHolder.tvDescription.setText(abilityFragment.getDescription());
            viewHolder.tvLevel.setText(levelText);
        }

    }

    @Override
    public int getItemCount() {
        return listAbilities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public TextView tvDescription;
        public TextView tvLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.abilityTitle);
            tvDescription = itemView.findViewById(R.id.abilityDescription);
            tvLevel = itemView.findViewById(R.id.abilityLevel);

        }
    }
}
