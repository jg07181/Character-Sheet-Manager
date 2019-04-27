package com.example.charactersheetmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private List<CharacterFragment> listCharacters;
    private onItemClickListener mListener;
    private onItemLongClickListener mListenerLong;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(int position);
    }

    void setOnItemClickedListener(onItemClickListener listener) {
        mListener = listener;
    }

    void setOnItemLongClickedListener(onItemLongClickListener listener) {mListenerLong = listener;}

    CharacterListAdapter(List<CharacterFragment> listCharacters) {
        this.listCharacters = listCharacters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_character_fragment, parent,false);
        return new ViewHolder(view, mListener, mListenerLong);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CharacterFragment characterFragment = listCharacters.get(i);

        viewHolder.id = characterFragment.getID();
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

    class ViewHolder extends RecyclerView.ViewHolder{

        String id;
        TextView tvName;
        TextView tvLevel;
        TextView tvRace;
        TextView tvClass;
        TextView tvBackground;

        ViewHolder(@NonNull View itemView, final onItemClickListener listener, final onItemLongClickListener listenerLong) {
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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listenerLong != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listenerLong.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
