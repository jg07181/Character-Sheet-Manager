package com.example.charactersheetmanager;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private List<CharacterFragment> listCharacters;
    private Context context;

    public CharacterListAdapter(List<CharacterFragment> listCharacters, Context context) {
        this.listCharacters = listCharacters;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_character_fragment,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CharacterFragment characterFragment = listCharacters.get(i);

        viewHolder.tvName.setText(characterFragment.getCharacterName());
        viewHolder.tvLevel.setText(characterFragment.getLevel());
        viewHolder.tvRace.setText(characterFragment.getRace());
        viewHolder.tvClass.setText(characterFragment.getCharacterClass());
        viewHolder.tvBackground.setText(characterFragment.getBackground());

    }

    @Override
    public int getItemCount() {
        return listCharacters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvLevel;
        public TextView tvRace;
        public TextView tvClass;
        public TextView tvBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.CharacterName);
            tvLevel = (TextView) itemView.findViewById(R.id.LevelText);
            tvRace = (TextView) itemView.findViewById(R.id.RaceName);
            tvClass = (TextView) itemView.findViewById(R.id.ClassName);
            tvBackground = (TextView) itemView.findViewById(R.id.BackgroundName);
        }
    }
}
