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

public class CreateCharacter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText characterName;
    private EditText inputStrength, inputDexterity, inputConstitution, inputIntelligence, inputWisdom, inputCharisma;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

        //Spinners
        Spinner chooseRace = findViewById(R.id.spinnerRace);
        Spinner chooseClass = findViewById(R.id.spinnerClass);
        Spinner chooseBackground = findViewById(R.id.spinnerBackground);

        ArrayAdapter<CharSequence> adapterRace = ArrayAdapter.createFromResource(this, R.array.possibleRaces, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterClass = ArrayAdapter.createFromResource(this, R.array.possibleClasses, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterBackground = ArrayAdapter.createFromResource(this, R.array.possibleBackgrounds, android.R.layout.simple_spinner_item);

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
                Intent i = new Intent(CreateCharacter.this, ExpandCharacter.class);
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
                return false;
            }
        });

        //Save stat and create modifier
        inputStrength = findViewById(R.id.inputStrength);
        inputStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Strength = checkAbilityBounds(v);

                //Check modifier
                int modStrength = getModifier(Strength);
                return false;
            }
        });

        inputDexterity = findViewById(R.id.inputDexterity);
        inputDexterity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Dexterity = checkAbilityBounds(v);

                //Check modifier
                int modDexterity = getModifier(Dexterity);
                return false;
            }
        });

        inputConstitution = findViewById(R.id.inputConstitution);
        inputConstitution.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Constitution = checkAbilityBounds(v);

                //Check modifier
                int modConstitution = getModifier(Constitution);
                return false;
            }
        });

        inputIntelligence = findViewById(R.id.inputIntelligence);
        inputIntelligence.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Intelligence = checkAbilityBounds(v);

                //Check modifier
                int modIntelligence = getModifier(Intelligence);
                return false;
            }
        });

        inputWisdom = findViewById(R.id.inputWisdom);
        inputWisdom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Wisdom = checkAbilityBounds(v);

                //Check modifier
                int modWisdom = getModifier(Wisdom);
                return false;
            }
        });

        inputCharisma = findViewById(R.id.inputCharisma);
        inputCharisma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Check bounds
                int Charisma = checkAbilityBounds(v);

                //Check modifier
                int modCharisma = getModifier(Charisma);
                return false;
            }
        });
    }

    //Checks if the stat is within bounds
    public int checkAbilityBounds(TextView input) {
        String statString = input.getText().toString();
        int statInt = Integer.parseInt(statString);
        input.setError(null);

        //Returns -1 as an invalid result
        if (statInt > 30 || statInt < 0) {
            input.setError("Between 30 and 0.");
            return 0;
        }

        else {
            return statInt;
        }

    }

    //Gets the modifier of an ability
    public int getModifier(Integer stat) {

        //Ability score modifier equation
        return (stat - 10) / 2;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}