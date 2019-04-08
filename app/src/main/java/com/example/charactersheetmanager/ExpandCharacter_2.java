package com.example.charactersheetmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpandCharacter_2 extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<AbilityFragment> abilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_2);

        // Obtain raceAbilities and classAbilities from intent
        String raceAbilities = getIntent().getStringExtra("Abilities");
        String[] classAbilities = getIntent().getStringArrayExtra("classAbilities");

        // Obtain feature descriptions
        String[] desc = getIntent().getStringArrayExtra("descriptions");

        // Store all race and class abilities into one ArrayList
        ArrayList<String> allAbilities = new ArrayList<>();

        //if (raceAbilities != null) {
            //String[] splitAbilitiesRace = raceAbilities.replace(", ", ",").split(",");
            //allAbilities.addAll(Arrays.asList(splitAbilitiesRace));
        //}

        allAbilities.addAll(Arrays.asList(classAbilities));

        // Set up recyclerView for abilities
        setUpRecyclerView(allAbilities, desc);
    }

    private void setUpRecyclerView(ArrayList abilities, String[] desc) {
        RecyclerView recyclerView = findViewById(R.id.rvAbilities);
        recyclerView.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        abilityList = new ArrayList<>();

        for (int i = 0; i < abilities.size(); i++){

            // Ignore null abilities
            if (abilities.get(i) != null) {
                AbilityFragment abilityFragment = new AbilityFragment(abilities.get(i).toString(), desc[i]);
                abilityList.add(abilityFragment);
            }

        }

        adapter = new AbilityListAdapter(abilityList,this);
        recyclerView.setAdapter(adapter);
    }

}
