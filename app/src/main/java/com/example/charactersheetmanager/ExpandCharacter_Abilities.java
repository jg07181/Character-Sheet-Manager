package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpandCharacter_Abilities extends AppCompatActivity {

    private CompletedCharacter finishedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_abilities);

        setTitle("Features");

        // Obtain raceAbilities, raceDescriptions, classAbilities, feature description, and feature level from intent
        String raceAbilities = getIntent().getStringExtra("raceAbilities");
        String raceDescriptions = getIntent().getStringExtra("raceDescriptions");
        String[] classAbilities = getIntent().getStringArrayExtra("classAbilities");
        String[] desc = getIntent().getStringArrayExtra("descriptions");
        int[] level = getIntent().getIntArrayExtra("levels");

        // Checks if a race has racial abilities
        if (raceAbilities != null) {
            String[] parseRaceAbilities = raceAbilities.split(",");
            String[] parseRaceDescriptions = raceDescriptions.split(";");

            int[] bufferLevel = new int[parseRaceAbilities.length];

            // Combine the race abilities and class abilities into a single string array
            String[] allAbilities = concat(parseRaceAbilities, classAbilities);

            // Combine the racial descriptions and class descriptions into a single string array
            String[] allDescriptions = concat(parseRaceDescriptions, desc);

            // Combine the racial levels and class level into a single int array
            int[] allLevels = new int[bufferLevel.length + level.length];
            System.arraycopy(bufferLevel, 0, allLevels, 0, bufferLevel.length);
            System.arraycopy(level, 0, allLevels, bufferLevel.length, level.length);

            // Set up recyclerView for both racial and class abilities
            setUpRecyclerView(allAbilities, allDescriptions, allLevels);

        }

        else {
            // Set up recyclerView for just class abilities
            setUpRecyclerView(classAbilities, desc, level);
        }

        getAllIntents();

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter_Abilities.this);

                // Updates existing character
                if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {

                    String id = getIntent().getStringArrayListExtra("CharacterInfo").get(19);
                    databaseAccess.updateCharacter(finishedCharacter, id);
                    Toast.makeText(ExpandCharacter_Abilities.this, "Character updated!", Toast.LENGTH_SHORT).show();
                }

                // Saves new character
                else {
                    databaseAccess.saveCharacter(finishedCharacter);
                    Toast.makeText(ExpandCharacter_Abilities.this, "Character saved!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ExpandCharacter_Abilities.this, CharacterList.class);
                startActivity(intent);
            }
        });

    }

    // Combines two string arrays into one
    private static <String> String[] concat(String[] first, String[] second) {
        String[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    // Sets up the recyclerView for abilities
    private void setUpRecyclerView(String[] abilities, String[] desc, int[] level) {
        RecyclerView recyclerView = findViewById(R.id.rvAbilities);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<AbilityFragment> abilityList = new ArrayList<>();

        for (int i = 0; i < abilities.length; i++){
            // Ignore null abilities
            if (abilities[i] != null) {
                AbilityFragment abilityFragment = new AbilityFragment(abilities[i], desc[i], level[i]);
                abilityList.add(abilityFragment);
            }

        }

        RecyclerView.Adapter adapter = new AbilityListAdapter(abilityList);
        recyclerView.setAdapter(adapter);
    }

    private void getAllIntents() {
        // Get character name and level
        String UserName = getIntent().getStringExtra("UserName");
        int UserLevel = getIntent().getIntExtra("UserLevel", 1);

        // Get user spinner input from CreateCharacter.java
        String UserClass = getIntent().getStringExtra("UserClass");
        String UserArchetype = getIntent().getStringExtra("UserArchetype");
        String UserRace = getIntent().getStringExtra("UserRace");
        String UserSubRace = getIntent().getStringExtra("UserSubRace");
        String UserBackground = getIntent().getStringExtra("UserBackground");

        int UserHP = getIntent().getIntExtra("UserHP", 0);
        int UserAC = getIntent().getIntExtra("UserAC", 0);

        // Get inputted ability scores
        int[] UserScore = getIntent().getIntArrayExtra("UserScore");
        int[] UserMod = getIntent().getIntArrayExtra("UserMod");

        String UserArmor = getIntent().getStringExtra("UserArmor");
        String UserWeapon = getIntent().getStringExtra("UserWeapon");

        boolean[] UserSkills = getIntent().getBooleanArrayExtra("UserSkills");
        String UserLanguage = getIntent().getStringExtra("UserLanguage");
        String UserTool = getIntent().getStringExtra("UserTool");
        String UserOtherWeapon = getIntent().getStringExtra("UserOtherWeapon");
        boolean[] UserSaves = getIntent().getBooleanArrayExtra("UserSaves");
        boolean[] UserWeaponArmor = getIntent().getBooleanArrayExtra("UserWeaponArmor");

        // Create completed character object
        finishedCharacter = new CompletedCharacter(UserName, UserClass, UserArchetype, UserRace, UserSubRace, UserBackground, UserLevel, UserScore, UserMod, UserSkills,
                                UserLanguage, UserTool, UserOtherWeapon, UserHP, UserAC, UserArmor, UserWeapon, UserSaves, UserWeaponArmor);

    }

}