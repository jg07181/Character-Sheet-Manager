package com.example.charactersheetmanager;

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
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void  onItemClick(int position);
    }

    public void setOnItemClickedListener(onItemClickListener listener) {
        mListener = listener;
    }

    public CharacterListAdapter(List<CharacterFragment> listCharacters, Context context) {
        this.listCharacters = listCharacters;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_character_fragment,parent,false);
        return new ViewHolder(view, mListener);
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

        public ViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.CharacterName);
            tvLevel = itemView.findViewById(R.id.LevelText);
            tvRace = itemView.findViewById(R.id.RaceName);
            tvClass = itemView.findViewById(R.id.ClassName);
            tvBackground = itemView.findViewById(R.id.BackgroundName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
