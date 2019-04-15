package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpandCharacter_Scores extends AppCompatActivity {

    private List ClassInfo, RaceInfo;
    private int level;
    private int[] abilities = new int[6];
    private int[] abilityMods = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_scores);

        setTitle("Ability Scores");

        ClassInfo = getIntent().getStringArrayListExtra("ClassInfo");
        RaceInfo = getIntent().getStringArrayListExtra("RaceInfo");
        level = getIntent().getIntExtra("level", 1);

        // nextButton click handler
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pass intent to ExpandCharacter_Proficiencies
                Intent intent = new Intent(ExpandCharacter_Scores.this, ExpandCharacter_Proficiencies.class);

                intent.putStringArrayListExtra("ClassInfo", (ArrayList<String>) ClassInfo);
                intent.putStringArrayListExtra("RaceInfo", (ArrayList<String>) RaceInfo);
                intent.putExtra("level", level);

                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                intent.putExtra("UserClass", getIntent().getStringExtra("UserClass"));
                intent.putExtra("UserArchetype", getIntent().getStringExtra("UserArchetype"));
                intent.putExtra("UserRace", getIntent().getStringExtra("UserRace"));
                intent.putExtra("UserSubRace", getIntent().getStringExtra("UserSubRace"));
                intent.putExtra("UserBackground", getIntent().getStringExtra("UserBackground"));
                intent.putExtra("UserScore", abilities);
                intent.putExtra("UserMod", abilityMods);

                startActivity(intent);
            }
        });

        // Save stat and create modifier
        EditText inputStrength = findViewById(R.id.inputStrength);
        inputStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[0] = validInput(v);
                    abilityMods[0] = getModifier(abilities[0]);

                    TextView strMod = findViewById(R.id.textStrengthMod);
                    strMod.setText(String.valueOf(abilityMods[0]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        EditText inputDexterity = findViewById(R.id.inputDexterity);
        inputDexterity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[1] = validInput(v);
                    abilityMods[1] = getModifier(abilities[1]);

                    TextView dexMod = findViewById(R.id.textDexterityMod);
                    dexMod.setText(String.valueOf(abilityMods[1]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        EditText inputConstitution = findViewById(R.id.inputConstitution);
        inputConstitution.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[2] = validInput(v);
                    abilityMods[2] = getModifier(abilities[2]);

                    TextView conMod = findViewById(R.id.textConstitutionMod);
                    conMod.setText(String.valueOf(abilityMods[2]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        EditText inputIntelligence = findViewById(R.id.inputIntelligence);
        inputIntelligence.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[3] = validInput(v);
                    abilityMods[3] = getModifier(abilities[3]);

                    TextView intMod = findViewById(R.id.textIntelligenceMod);
                    intMod.setText(String.valueOf(abilityMods[3]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        EditText inputWisdom = findViewById(R.id.inputWisdom);
        inputWisdom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[4] = validInput(v);
                    abilityMods[4] = getModifier(abilities[4]);

                    TextView wisMod = findViewById(R.id.textWisdomMod);
                    wisMod.setText(String.valueOf(abilityMods[4]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        EditText inputCharisma = findViewById(R.id.inputCharisma);
        inputCharisma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[5] = validInput(v);
                    abilityMods[5] = getModifier(abilities[5]);

                    TextView chaMod = findViewById(R.id.textCharismaMod);
                    chaMod.setText(String.valueOf(abilityMods[5]));
                }
                return true;
            }
        });

    }

    // Check for valid input and within bounds
    public int validInput(TextView input) {
        // Check that the input field isn't empty
        if (!input.getText().toString().equals("")) {
            String statString = input.getText().toString();
            int statInt = Integer.parseInt(statString);

            // Check that the input is within range
            if (statInt > 30 || statInt < 0) {
                input.setError("Between 0 and 30");
                return -1;
            }
            else return statInt;
        }
        return -1;
    }

    // Gets the modifier of an ability
    public int getModifier(Integer stat) {
        return (stat - 10) / 2;
    }
}