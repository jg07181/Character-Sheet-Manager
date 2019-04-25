package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CharacterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        // Sets up recyclerView for previously created characters stored in database
        setUpRecyclerView();

        //Add Character Button
        FloatingActionButton addCharacter = findViewById(R.id.fabAddCharacter);
        addCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharacterList.this, CreateCharacter.class);
                startActivity(intent);
            }
        });

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.characterList);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<CharacterFragment> characterList = new ArrayList<>();

        // Get all created character's name, level, class, race, and background
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CharacterList.this);
        databaseAccess.open();

        final List<String> playerName =  databaseAccess.checkDatabase("name");
        final List<String> playerClass = databaseAccess.checkDatabase("class");
        final List<String> playerRace = databaseAccess.checkDatabase("race");
        final List<String> playerSubRace = databaseAccess.checkDatabase("subrace");
        final List<String> playerBackground = databaseAccess.checkDatabase("background");
        List<String> payerLevel = databaseAccess.checkDatabase("level");

        // Iterate through all saved characters
        for (int i = 0; i < playerName.size(); i++){
            if (playerSubRace.get(i).equals("None")) {
                CharacterFragment characterFragment = new CharacterFragment(playerName.get(i),"level " + payerLevel.get(i), playerRace.get(i), playerClass.get(i), playerBackground.get(i));
                characterList.add(characterFragment);
            }

            else {
                CharacterFragment characterFragment = new CharacterFragment(playerName.get(i),"level " + payerLevel.get(i), playerSubRace.get(i), playerClass.get(i), playerBackground.get(i));
                characterList.add(characterFragment);
            }

        }

        final RecyclerView.Adapter adapter = new CharacterListAdapter(characterList, this);
        recyclerView.setAdapter(adapter);

        // Clicking a created character will allow the user to edit the character
        ((CharacterListAdapter) adapter).setOnItemClickedListener(new CharacterListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                Intent intent = new Intent(CharacterList.this, CreateCharacter.class);
                intent.putStringArrayListExtra("CharacterInfo", (ArrayList<String>) databaseAccess.getCharacter(playerName.get(position), playerClass.get(position), playerRace.get(position), playerSubRace.get(position), playerBackground.get(position)));
                startActivity(intent);
            }
        });

        // Holding down on a created character will allow the user to delete the selected character
        ((CharacterListAdapter) adapter).setOnItemLongClickedListener(new CharacterListAdapter.onItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                // Remove character from arrayList, recyclerView, and notify removal
                characterList.remove(position);
                adapter.notifyItemRemoved(position);

                // Removes character from database
                databaseAccess.removeCharacter(playerName.get(position), playerClass.get(position), playerRace.get(position), playerSubRace.get(position), playerBackground.get(position));
                Toast.makeText(CharacterList.this, "Character deleted!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

}
