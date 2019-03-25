package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateCharacter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText characterName, level;
    private EditText inputStrength, inputDexterity, inputConstitution, inputIntelligence, inputWisdom, inputCharisma;
    private Spinner chooseClass, chooseRace, chooseSubRace, chooseBackground;
    private Button nextButton;
    private String Table;

    // Character variables
    private String UserName;
    private int UserLevel;
    private int[] abilities = new int[6];
    private int[] abilityMods = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CreateCharacter.this);
        databaseAccess.open();

        // Set up spinners which read from the database
        spinnerClassSetUp(databaseAccess);
        spinnerRaceSetUp(databaseAccess);
        spinnerSubRaceSetUp(databaseAccess, chooseRace.getSelectedItem().toString());
        spinnerBackground(databaseAccess);

        // Sub race spinner
        chooseRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String race = chooseRace.getSelectedItem().toString();
                spinnerSubRaceSetUp(DatabaseAccess.getInstance(CreateCharacter.this), race);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Next Button
        // Passes all inputted values to ExpandCharacter.java
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateCharacter.this, ExpandCharacter.class);

                // Passes the entered charcter name and level to ExpandCharacter.java
                i.putExtra("UserName", UserName);
                i.putExtra("UserLevel", UserLevel);

                // Passes the chosen class to ExpandCharacter.java
                i.putExtra("UserClass", chooseClass.getSelectedItem().toString());

                // Passes the chosen race and subrace to ExpandCharacter.java
                i.putExtra("UserRace", chooseRace.getSelectedItem().toString());
                i.putExtra("UserSubRace", chooseSubRace.getSelectedItem().toString());

                // Passes the chosen background to ExpandCharacter.java
                i.putExtra("UserBackground", chooseBackground.getSelectedItem().toString());
                startActivity(i);
            }
        });

        // Enter character name via EditText
        characterName = findViewById(R.id.characterName);
        characterName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Save name here
                UserName = v.getText().toString();
                return true;
            }
        });

        // Enter character level via EditText
        level = findViewById(R.id.level);
        level.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Save level here
                UserLevel = Integer.parseInt(v.getText().toString());
                return true;
            }
        });

        // Save stat and create modifier
        inputStrength = findViewById(R.id.inputStrength);
        inputStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[0] = validInput(v);
                    abilityMods[0] = getModifier(abilities[0]);
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputDexterity = findViewById(R.id.inputDexterity);
        inputDexterity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[1] = validInput(v);
                    abilityMods[1] = getModifier(abilities[1]);

                }
                return true;
            }
        });

        // Save stat and create modifier
        inputConstitution = findViewById(R.id.inputConstitution);
        inputConstitution.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[2] = validInput(v);
                    abilityMods[2] = getModifier(abilities[2]);

                }
                return true;
            }
        });

        // Save stat and create modifier
        inputIntelligence = findViewById(R.id.inputIntelligence);
        inputIntelligence.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[3] = validInput(v);
                    abilityMods[3] = getModifier(abilities[3]);

                }
                return true;
            }
        });

        // Save stat and create modifier
        inputWisdom = findViewById(R.id.inputWisdom);
        inputWisdom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[4] = validInput(v);
                    abilityMods[4] = getModifier(abilities[4]);

                }
                return true;
            }
        });

        // Save stat and create modifier
        inputCharisma = findViewById(R.id.inputCharisma);
        inputCharisma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[5] = validInput(v);
                    abilityMods[5] = getModifier(abilities[5]);
                }
                return true;
            }
        });
    }

    private void spinnerClassSetUp(DatabaseAccess databaseAccess) {
        chooseClass = findViewById(R.id.spinnerClass);
        Table = "Class";
        String[] classList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterClass = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        adapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseClass.setAdapter(adapterClass);
    }

    private void spinnerRaceSetUp(DatabaseAccess databaseAccess) {
        chooseRace = findViewById(R.id.spinnerRace);
        Table = "Race";
        String[] raceList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterRace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raceList);
        adapterRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRace.setAdapter(adapterRace);
    }

    private void spinnerSubRaceSetUp(DatabaseAccess databaseAccess, String race) {
        chooseSubRace = findViewById(R.id.spinnerSubRace);
        String[] subRaceList = databaseAccess.getSubRace(race);

        ArrayAdapter<String> adapterSubRace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subRaceList);
        adapterSubRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseSubRace.setAdapter(adapterSubRace);
    }

    private void spinnerBackground(DatabaseAccess databaseAccess) {
        chooseBackground = findViewById(R.id.spinnerBackground);
        Table = "Background";
        String[] backgroundList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterBackground = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, backgroundList);
        adapterBackground.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseBackground.setAdapter(adapterBackground);
    }

    // Check for valid input and within bounds
    public int validInput(TextView input) {

        // Check that the input field isn't empty
        if (!input.getText().toString().equals("")) {
            String statString = input.getText().toString();
            int statInt = Integer.parseInt(statString);

            // Check that the input is within range
            if (statInt > 30 || statInt < 0) {
                input.setError("Please enter a number between 0 and 30");
                return -1;
            }
            else return statInt;
        }
        return -1;
    }

    // Gets the modifier of an ability
    public int getModifier(Integer stat) {

        // Ability score modifier equation
        return (stat - 10) / 2;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}