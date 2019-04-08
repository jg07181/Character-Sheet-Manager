package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CharacterList extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<CharacterFragment> characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        // Sets up recyclerView for previously created characters stored in database
        setUpRecyclerView();

        //Add Character Button
        Button addCharacter = findViewById(R.id.addCharacterButton);
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

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        characterList = new ArrayList<>();

        for (int i = 0; i <= 10; i++){
            CharacterFragment characterFragment = new CharacterFragment("Jim","" + i,"Dragonborn","Thief","Sailor");
            characterList.add(characterFragment);
        }

        adapter = new CharacterListAdapter(characterList,this);
        recyclerView.setAdapter(adapter);
    }

}
