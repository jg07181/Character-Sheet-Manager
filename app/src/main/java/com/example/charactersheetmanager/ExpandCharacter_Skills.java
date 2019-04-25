package com.example.charactersheetmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExpandCharacter_Skills extends AppCompatActivity {

    private CheckBox[] skills;
    private List ClassInfo, RaceInfo, BackgroundInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_skills);

        setTitle("Skill Proficiencies");

        // Get character level
        final String UserName = getIntent().getStringExtra("UserName");
        final int level = getIntent().getIntExtra("UserLevel", 1);

        // Get user spinner input from CreateCharacter.java
        final String UserClass = getIntent().getStringExtra("UserClass");
        final String UserArchetype = getIntent().getStringExtra("UserArchetype");
        final String UserRace = getIntent().getStringExtra("UserRace");
        final String UserSubRace = getIntent().getStringExtra("UserSubRace");
        final String UserBackground = getIntent().getStringExtra("UserBackground");

        // Finds all the IDs for the skill checkboxes
        findViewForSkills();

        // Checks if the user clicked on a pre-existing character
        if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
            ArrayList<String> CharacterInfo = getIntent().getStringArrayListExtra("CharacterInfo");

            // Parses string by removing brackets and splitting on ", "
            String removeBrackets = CharacterInfo.get(9).replaceAll("\\[", "").replaceAll("]", "");
            String[] parseString = removeBrackets.split(", ");

            // Checks all pre-selected skills
            for (int i = 0; i < skills.length; i++) {
                if (parseString[i].equals("true") ) {
                    skills[i].setChecked(true);
                }
            }

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter_Skills.this);
            BackgroundInfo = databaseAccess.getBackgroundInfo(CharacterInfo.get(6));
            ClassInfo = databaseAccess.getClassInfo(CharacterInfo.get(2));

            if (CharacterInfo.get(5).equals("None")) {
                RaceInfo = databaseAccess.getRaceInfo(CharacterInfo.get(4), false);
            }

            else {
                RaceInfo = databaseAccess.getRaceInfo(CharacterInfo.get(5), true);
            }

        }

        // Assumes this character isn't saved to database
        else {
            // Open connection to database
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter_Skills.this);

            // Checks the appropriate skills as proficient as determined by the background
            BackgroundInfo = databaseAccess.getBackgroundInfo(UserBackground);
            setBackgroundProficiencies(BackgroundInfo.get(1).toString(), BackgroundInfo.get(2).toString());

            // Checks the appropriate skills as proficient as determined by the race or sub-race
            if (UserSubRace.equals("None")) {
                RaceInfo = databaseAccess.getRaceInfo(UserRace, false);
            }

            else {
                RaceInfo = databaseAccess.getRaceInfo(UserSubRace, true);
            }

            // Checks race skills if any are present
            if (RaceInfo.get(4) != null) {
                setRaceProficiencies(RaceInfo.get(4).toString());
            }

            // Parse and assign skill proficiencies as determined by the class
            ClassInfo = databaseAccess.getClassInfo(UserClass);
            setClassSkills(ClassInfo.get(7).toString());
        }

        // nextButton click handler
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pass intent to ExpandCharacter_Proficiencies
                Intent intent = new Intent(ExpandCharacter_Skills.this, ExpandCharacter_Scores.class);

                intent.putStringArrayListExtra("ClassInfo", (ArrayList<String>) ClassInfo);
                intent.putStringArrayListExtra("RaceInfo", (ArrayList<String>) RaceInfo);
                intent.putStringArrayListExtra("BackgroundInfo", (ArrayList<String>) BackgroundInfo);

                // Stores all user skills into boolean array
                boolean[] UserSkills = new boolean[skills.length];

                for (int i = 0; i < skills.length; i++) {
                    if (skills[i].isChecked()) {
                        UserSkills[i] = true;
                    }
                }

                intent.putExtra("UserSkills", UserSkills);
                intent.putExtra("level", level);
                intent.putExtra("UserName", UserName);
                intent.putExtra("UserClass", UserClass);
                intent.putExtra("UserArchetype", UserArchetype);
                intent.putExtra("UserRace", UserRace);
                intent.putExtra("UserSubRace", UserSubRace);
                intent.putExtra("UserBackground", UserBackground);

                // Passes created character info if it exists
                if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
                    intent.putExtra("CharacterInfo", getIntent().getStringArrayListExtra("CharacterInfo"));
                }

                startActivity(intent);
            }
        });

    }

    // Finds all the IDs for each skill checkbox and places them in a Checkbox[] array
    private void findViewForSkills() {
        CheckBox Acrobatics = findViewById(R.id.checkbox_Acrobatics);
        CheckBox Animal_Handling = findViewById(R.id.checkbox_Animal_Handling);
        CheckBox Arcana = findViewById(R.id.checkbox_Arcana);
        CheckBox Athletics = findViewById(R.id.checkbox_Athletics);
        CheckBox Deception = findViewById(R.id.checkbox_Deception);
        CheckBox History = findViewById(R.id.checkbox_History);
        CheckBox Insight = findViewById(R.id.checkbox_Insight);
        CheckBox Intimidation = findViewById(R.id.checkbox_Intimidation);
        CheckBox Investigation = findViewById(R.id.checkbox_Investigation);
        CheckBox Medicine = findViewById(R.id.checkbox_Medicine);
        CheckBox Nature = findViewById(R.id.checkbox_Nature);
        CheckBox Perception = findViewById(R.id.checkbox_Perception);
        CheckBox Performance = findViewById(R.id.checkbox_Performance);
        CheckBox Persuasion = findViewById(R.id.checkbox_Persuasion);
        CheckBox Religion = findViewById(R.id.checkbox_Religion);
        CheckBox Sleight_of_Hand = findViewById(R.id.checkbox_Sleight_of_Hand);
        CheckBox Stealth = findViewById(R.id.checkbox_Stealth);
        CheckBox Survival = findViewById(R.id.checkbox_Survival);

        //Adds all checkboxes to skills array
        skills = new CheckBox[] {Acrobatics, Animal_Handling, Arcana, Athletics, Deception, History, Insight, Intimidation, Investigation,
                                 Medicine, Nature, Perception, Performance, Persuasion, Religion, Sleight_of_Hand, Stealth, Survival};


    }

    // Checks background skill proficiencies
    private  void setBackgroundProficiencies(String skill_1, String skill_2) {
        for (CheckBox skill : skills) {
            // Compares the background proficiencies with the checkbox array
            if (skill.getText().toString().contains(skill_1) || skill.getText().toString().contains(skill_2)) {
                skill.setChecked(true);
            }

        }

    }

    // Checks the race skill proficiencies
    private void setRaceProficiencies(String skill) {
        // Checks if a skill is specified or simply a number to choose from
        if (!android.text.TextUtils.isDigitsOnly(skill)) {
            for (CheckBox skill1 : skills) {
                if (skill1.getText().toString().contains(skill)) {
                    skill1.setChecked(true);
                }

            }

        }

        else {
            int num = Integer.parseInt(skill);
            Toast.makeText(this, "Pick any " + num + " extra skills!", Toast.LENGTH_SHORT).show();

        }

    }

    // Allows the user to pick their class proficiencies
    private void setClassSkills(String possibleSkills) {
        // Divide skill column into string array
        final String[] classSkills = possibleSkills.replace(", ", ",").split(",");

        // Maximum number of skills the user can choose
        int maximum = Integer.parseInt(ClassInfo.get(6).toString());

        final boolean[] isChecked = new boolean[classSkills.length];

        for (CheckBox skill : skills) {
            if (skill.isChecked()) {
                for (int j = 0; j < classSkills.length; j++) {
                    if (skill.getText().toString().equals(classSkills[j])) {
                        isChecked[j] = true;
                        break;
                    }
                }
            }
        }

        // Display alertDialog where user can pick class skills
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose " + maximum + " skills!");

        // Display all possible class skills via a checkbox list
        alert.setMultiChoiceItems(classSkills, isChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });

        // Clicking "ok" will dismiss the dialog and check all picked skills
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setPickedSkills(classSkills, isChecked);
            }
        });

        alert.show();

    }

    // Checked picked skills from alertDialog
    private void setPickedSkills(String[] classSkills, boolean[] checked) {
        for (CheckBox skill : skills) {
            for (int i = 0; i < classSkills.length; i++) {
                if (skill.getText().toString().equals(classSkills[i]) && checked[i]) {
                    skill.setChecked(true);
                }

            }

        }

    }

}