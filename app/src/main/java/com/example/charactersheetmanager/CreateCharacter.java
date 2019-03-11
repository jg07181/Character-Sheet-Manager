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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateCharacter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText characterName, level;
    private EditText inputStrength, inputDexterity, inputConstitution, inputIntelligence, inputWisdom, inputCharisma;
    private int[] abilities = new int[6];
    private int[] abilityMods = new int[6];
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

        //Spinners
        final Spinner chooseRace = findViewById(R.id.spinnerRace);
        final Spinner chooseClass = findViewById(R.id.spinnerClass);
        final Spinner chooseBackground = findViewById(R.id.spinnerBackground);

        ArrayAdapter<CharSequence> adapterRace = ArrayAdapter.createFromResource(this, R.array.possibleRaces, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterClass = ArrayAdapter.createFromResource(this, R.array.possibleClasses, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> adapterBackground = ArrayAdapter.createFromResource(this, R.array.possibleBackgrounds, android.R.layout.simple_spinner_item);

        adapterRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterBackground.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chooseRace.setAdapter(adapterRace);
        chooseClass.setAdapter(adapterClass);
        chooseBackground.setAdapter(adapterBackground);

        chooseRace.setOnItemSelectedListener(this);
        chooseClass.setOnItemSelectedListener(this);
        chooseBackground.setOnItemSelectedListener(this);

        //Next Button
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CreateCharacter.this);
                databaseAccess.open();

                List<String> classInfo = databaseAccess.getSelectedClass(chooseClass.getSelectedItem().toString());
                List<String> raceInfo = databaseAccess.getSelectedRace(chooseRace.getSelectedItem().toString());
                List<String> backgroundInfo = databaseAccess.getSelectedBackground(chooseBackground.getSelectedItem().toString());

                Intent i = new Intent(CreateCharacter.this, ExpandCharacter.class);
                i.putStringArrayListExtra("classInfo", (ArrayList<String>) classInfo);
                i.putStringArrayListExtra("raceInfo", (ArrayList<String>) raceInfo);
                i.putStringArrayListExtra("backgroundInfo", (ArrayList<String>) backgroundInfo);
                startActivity(i);
            }
        });

        //Enter character name via EditText
        characterName = findViewById(R.id.characterName);
        characterName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Save name here
                String characterName = v.getText().toString();
                return true;
            }
        });

        //Enter character level via EditText
        level = findViewById(R.id.level);
        level.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Save level here
                int level = Integer.parseInt(v.getText().toString());
                return true;
            }
        });

        //Save stat and create modifier
        inputStrength = findViewById(R.id.inputStrength);
        inputStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[0] = validInput(v);
                    abilityMods[0] = getModifier(abilities[0]);
                }
                return true;
            }
        });

        inputDexterity = findViewById(R.id.inputDexterity);
        inputDexterity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[1] = validInput(v);
                    abilityMods[1] = getModifier(abilities[1]);

                }
                return true;
            }
        });

        inputConstitution = findViewById(R.id.inputConstitution);
        inputConstitution.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[2] = validInput(v);
                    abilityMods[2] = getModifier(abilities[2]);

                }
                return true;
            }
        });

        inputIntelligence = findViewById(R.id.inputIntelligence);
        inputIntelligence.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[3] = validInput(v);
                    abilityMods[3] = getModifier(abilities[3]);

                }
                return true;
            }
        });

        inputWisdom = findViewById(R.id.inputWisdom);
        inputWisdom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[4] = validInput(v);
                    abilityMods[4] = getModifier(abilities[4]);

                }
                return true;
            }
        });

        inputCharisma = findViewById(R.id.inputCharisma);
        inputCharisma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[5] = validInput(v);
                    abilityMods[5] = getModifier(abilities[5]);
                }
                return true;
            }
        });
    }

    //Check for valid input and within bounds
    public int validInput(TextView input) {

        //Check that the input field isn't empty
        if (!input.getText().toString().equals("")) {
            String statString = input.getText().toString();
            int statInt = Integer.parseInt(statString);

            //Check that the input is within range
            if (statInt > 30 || statInt < 0) {
                input.setError("Please enter a number between 0 and 30");
                return -1;
            }
            else return statInt;
        }
        return -1;
    }

    //Gets the modifier of an ability
    public int getModifier(Integer stat) {

        //Ability score modifier equation
        return (stat - 10) / 2;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String choice = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}