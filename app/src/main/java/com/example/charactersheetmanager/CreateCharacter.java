package com.example.charactersheetmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.renderscript.Sampler;
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

public class CreateCharacter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText characterName, level;
    private Spinner chooseClass, chooseArchetype, chooseRace, chooseSubRace, chooseBackground;
    private String Table;

    // Character variables
    private String UserName;
    private int UserLevel;
    private int experience, proficiency;
    private String preName, preLevel, preClass, preArchetype, preRace, preSubRace, preBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        setTitle("Fundamental Information");

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CreateCharacter.this);
        databaseAccess.open();

        characterName = findViewById(R.id.characterName);
        level = findViewById(R.id.level);

        // Checks if the user clicked on a pre-existing character
        if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
            ArrayList<String> CharacterInfo = getIntent().getStringArrayListExtra("CharacterInfo");

            // Gathers all character information relevant to this activity
            preName = CharacterInfo.get(0);
            preLevel = CharacterInfo.get(1);
            preClass = CharacterInfo.get(2);
            preArchetype = CharacterInfo.get(3);
            preRace = CharacterInfo.get(4);
            preSubRace = CharacterInfo.get(5);
            preBackground = CharacterInfo.get(6);

            // Sets existing character name and level
            characterName.setText(preName);
            level.setText(preLevel);

            UserLevel = Integer.parseInt(preLevel);
            setLevel();

        }

        // Set up spinners which read from the database
        spinnerClassSetUp(databaseAccess);
        spinnerArchetypeSetUp(databaseAccess, chooseClass.getSelectedItem().toString());
        spinnerRaceSetUp(databaseAccess);
        spinnerSubRaceSetUp(databaseAccess, chooseRace.getSelectedItem().toString());
        spinnerBackground(databaseAccess);

        // Archetype spinner
        chooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Class = chooseClass.getSelectedItem().toString();
                spinnerArchetypeSetUp(DatabaseAccess.getInstance(CreateCharacter.this), Class);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Sub-race spinner
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

        // Passes all inputted values to ExpandCharacter_Skills.java
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean completed = checkCompletedForm();

                if (completed) {
                    Intent intent = new Intent(CreateCharacter.this, ExpandCharacter_Skills.class);

                    // Passes the entered character name, level, and proficiency to ExpandCharacter_Skills.java
                    intent.putExtra("UserName", UserName);
                    intent.putExtra("UserLevel", UserLevel);
                    intent.putExtra("proficiency", proficiency);

                    // Passes the chosen class to ExpandCharacter_Skills.java
                    intent.putExtra("UserClass", chooseClass.getSelectedItem().toString());
                    intent.putExtra("UserArchetype", chooseArchetype.getSelectedItem().toString());

                    // Passes the chosen race and sub-race to ExpandCharacter_Skills.java
                    intent.putExtra("UserRace", chooseRace.getSelectedItem().toString());
                    intent.putExtra("UserSubRace", chooseSubRace.getSelectedItem().toString());

                    // Passes the chosen background to ExpandCharacter_Skills.java
                    intent.putExtra("UserBackground", chooseBackground.getSelectedItem().toString());

                    // Passes created character info if it exists
                    if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
                        intent.putExtra("CharacterInfo", getIntent().getStringArrayListExtra("CharacterInfo"));
                    }

                    startActivity(intent);
                }

                else {
                    Toast.makeText(CreateCharacter.this, "Please complete the form", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        // Enter character name via EditText
        characterName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Save name here
                if (!v.getText().toString().isEmpty()) {
                    UserName = v.getText().toString();
                }
                
                else {
                    Toast.makeText(CreateCharacter.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // Enter character level via EditText
        level.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                try {
                    UserLevel = Integer.parseInt(v.getText().toString());
                    setLevel();
                } catch (Exception ex) {
                    Toast.makeText(CreateCharacter.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // Save experience and calculate proficiency
        EditText inputExperience = findViewById(R.id.experience);
        inputExperience.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                try {
                    experience = Integer.parseInt(v.getText().toString());
                    setExp();
                } catch (Exception ex) {
                    Toast.makeText(CreateCharacter.this, "Please enter a number", Toast.LENGTH_SHORT).show();
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

        // Sets spinner to selected class if opening an existing character
        if (preClass != null) {
            int position = adapterClass.getPosition(preClass);
            chooseClass.setSelection(position);
        }
    }

    private void spinnerArchetypeSetUp(DatabaseAccess databaseAccess, String Class) {
        chooseArchetype = findViewById(R.id.spinnerArchetype);
        String[] archetypeList = databaseAccess.getArchetypeList(Class);

        ArrayAdapter<String> adapterArchetype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, archetypeList);
        adapterArchetype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseArchetype.setAdapter(adapterArchetype);

        // Sets spinner to selected archetype if opening an existing character
        if (preArchetype != null) {
            int position = adapterArchetype.getPosition(preArchetype);
            chooseArchetype.setSelection(position);
        }
    }

    private void spinnerRaceSetUp(DatabaseAccess databaseAccess) {
        chooseRace = findViewById(R.id.spinnerRace);
        Table = "Race";
        String[] raceList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterRace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raceList);
        adapterRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRace.setAdapter(adapterRace);

        // Sets spinner to selected race if opening an existing character
        if (preRace != null) {
            int position = adapterRace.getPosition(preRace);
            chooseRace.setSelection(position);
        }
    }

    private void spinnerSubRaceSetUp(DatabaseAccess databaseAccess, String race) {
        chooseSubRace = findViewById(R.id.spinnerSubRace);
        String[] subRaceList = databaseAccess.getSubRace(race);

        ArrayAdapter<String> adapterSubRace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subRaceList);
        adapterSubRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseSubRace.setAdapter(adapterSubRace);

        // Sets spinner to selected sub-race if opening an existing character
        if (preSubRace != null) {
            int position = adapterSubRace.getPosition(preSubRace);
            chooseSubRace.setSelection(position);
        }
    }

    private void spinnerBackground(DatabaseAccess databaseAccess) {
        chooseBackground = findViewById(R.id.spinnerBackground);
        Table = "Background";
        String[] backgroundList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterBackground = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, backgroundList);
        adapterBackground.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseBackground.setAdapter(adapterBackground);

        // Sets spinner to selected class if opening an existing character
        if (preBackground != null) {
            int position = adapterBackground.getPosition(preBackground);
            chooseBackground.setSelection(position);
        }
    }

    // Sets level and proficiency based on inputted experience
    @SuppressLint("SetTextI18n")
    private void setExp() {
            TextView textProficiency = findViewById(R.id.proficiency);

            if (experience >= 0 && experience <= 300) {
                textProficiency.setText("+ 2");
                proficiency = 2;

                level.setText("1");
                UserLevel = 1;
            }

            else if (experience > 300 && experience <= 900) {
                textProficiency.setText("+ 2");
                proficiency = 2;

                level.setText("2");
                UserLevel = 2;
            }

            else if (experience > 900 && experience <= 2700) {
                textProficiency.setText("+ 2");
                proficiency = 2;

                level.setText("3");
                UserLevel = 3;
            }

            else if (experience > 2700 && experience <= 6500) {
                textProficiency.setText("+ 2");
                proficiency = 2;

                level.setText("4");
                UserLevel = 4;
            }

            else if (experience > 6500 && experience <= 14000) {
                textProficiency.setText("+ 3");
                proficiency = 3;

                level.setText("5");
                UserLevel = 5;
            }

            else if (experience > 14000 && experience <= 23000) {
                textProficiency.setText("+ 3");
                proficiency = 3;

                level.setText("6");
                UserLevel = 6;
            }

            else if (experience > 23000 && experience <= 34000) {
                textProficiency.setText("+ 3");
                proficiency = 3;

                level.setText("7");
                UserLevel = 7;
            }

            else if (experience > 34000 && experience <= 48000) {
                textProficiency.setText("+ 3");
                proficiency = 3;

                level.setText("8");
                UserLevel = 8;
            }

            else if (experience > 48000 && experience <= 64000) {
                textProficiency.setText("+ 4");
                proficiency = 4;

                level.setText("9");
                UserLevel = 9;
            }

            else if (experience > 64000 && experience <= 85000) {
                textProficiency.setText("+ 4");
                proficiency = 4;

                level.setText("10");
                UserLevel = 10;
            }

            else if (experience > 85000 && experience <= 100000) {
                textProficiency.setText("+ 4");
                proficiency = 4;

                level.setText("11");
                UserLevel = 11;
            }

            else if (experience > 100000 && experience <= 120000) {
                textProficiency.setText("+ 4");
                proficiency = 2;

                level.setText("12");
                UserLevel = 12;
            }

            else if (experience > 120000 && experience <= 140000) {
                textProficiency.setText("+ 5");
                proficiency = 5;

                level.setText("13");
                UserLevel = 13;
            }

            else if (experience > 140000 && experience <= 165000) {
                textProficiency.setText("+ 5");
                proficiency = 5;

                level.setText("14");
                UserLevel = 14;
            }

            else if (experience > 165000 && experience <= 195000) {
                textProficiency.setText("+ 5");
                proficiency = 5;

                level.setText("15");
                UserLevel = 15;
            }

            else if (experience > 195000 && experience <= 225000) {
                textProficiency.setText("+ 5");
                proficiency = 5;

                level.setText("16");
                UserLevel = 16;
            }

            else if (experience > 225000 && experience <= 265000) {
                textProficiency.setText("+ 6");
                proficiency = 6;

                level.setText("17");
                UserLevel = 17;
            }

            else if (experience > 265000 && experience <= 305000) {
                textProficiency.setText("+ 6");
                proficiency = 6;

                level.setText("18");
                UserLevel = 18;
            }

            else if (experience > 305000 && experience <= 355000) {
                textProficiency.setText("+ 6");
                proficiency = 6;

                level.setText("19");
                UserLevel = 19;
            }

            else {
                textProficiency.setText("+ 6");
                proficiency = 6;

                level.setText("20");
                UserLevel = 20;
            }

        }

    // Sets exp and proficiency based on inputted level
    @SuppressLint("SetTextI18n")
    private void setLevel() {
        TextView textProficiency = findViewById(R.id.proficiency);
        EditText inputExperience = findViewById(R.id.experience);

        if (UserLevel == 1) {
            textProficiency.setText("+ 2");
            proficiency = 2;

            inputExperience.setText("0");
            experience = 0;
        }

        else if (UserLevel == 2) {
            textProficiency.setText("+ 2");
            proficiency = 2;

            inputExperience.setText("300");
            experience = 300;
        }

        else if (UserLevel == 3) {
            textProficiency.setText("+ 2");
            proficiency = 2;

            inputExperience.setText("900");
            experience = 900;
        }

        else if (UserLevel == 4) {
            textProficiency.setText("+ 2");
            proficiency = 2;

            inputExperience.setText("2700");
            experience = 2700;
        }

        else if (UserLevel == 5) {
            textProficiency.setText("+ 3");
            proficiency = 3;

            inputExperience.setText("6500");
            experience = 6500;
        }

        else if (UserLevel == 6) {
            textProficiency.setText("+ 3");
            proficiency = 3;

            inputExperience.setText("14000");
            experience = 14000;
        }

        else if (UserLevel == 7) {
            textProficiency.setText("+ 3");
            proficiency = 3;

            inputExperience.setText("23000");
            experience = 23000;
        }

        else if (UserLevel == 8) {
            textProficiency.setText("+ 3");
            proficiency = 3;

            inputExperience.setText("34000");
            experience = 34000;
        }

        else if (UserLevel == 9) {
            textProficiency.setText("+ 4");
            proficiency = 4;

            inputExperience.setText("48000");
            experience = 48000;
        }

        else if (UserLevel == 10) {
            textProficiency.setText("+ 4");
            proficiency = 4;

            inputExperience.setText("64000");
            experience = 64000;
        }

        else if (UserLevel == 11) {
            textProficiency.setText("+ 4");
            proficiency = 4;

            inputExperience.setText("85000");
            experience = 85000;
        }

        else if (UserLevel == 12) {
            textProficiency.setText("+ 4");
            proficiency = 2;

            inputExperience.setText("100000");
            experience = 100000;
        }

        else if (UserLevel == 13) {
            textProficiency.setText("+ 5");
            proficiency = 5;

            inputExperience.setText("120000");
            experience = 120000;
        }

        else if (UserLevel == 14) {
            textProficiency.setText("+ 5");
            proficiency = 5;

            inputExperience.setText("140000");
            experience = 140000;
        }

        else if (UserLevel == 15) {
            textProficiency.setText("+ 5");
            proficiency = 5;

            inputExperience.setText("165000");
            experience = 165000;
        }

        else if (UserLevel == 16) {
            textProficiency.setText("+ 5");
            proficiency = 5;

            inputExperience.setText("195000");
            experience = 195000;
        }

        else if (UserLevel == 17) {
            textProficiency.setText("+ 6");
            proficiency = 6;

            inputExperience.setText("225000");
            experience = 225000;
        }

        else if (UserLevel == 18) {
            textProficiency.setText("+ 6");
            proficiency = 6;

            inputExperience.setText("265000");
            experience = 265000;
        }

        else if (UserLevel == 19) {
            textProficiency.setText("+ 6");
            proficiency = 6;

            inputExperience.setText("305000");
            experience = 305000;
        }

        else {
            textProficiency.setText("+ 6");
            proficiency = 6;

            inputExperience.setText("355000");
            experience = 355000;
        }

    }

    // Checks if the entire form was filled
    private boolean checkCompletedForm() {
        boolean completed = false;

        if (UserName != null && !UserName.isEmpty() && UserLevel != 0) {
            completed = true;

        }

        return completed;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}