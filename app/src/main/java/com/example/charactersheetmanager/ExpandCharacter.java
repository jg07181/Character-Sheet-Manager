package com.example.charactersheetmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import java.util.List;

public class ExpandCharacter extends AppCompatActivity {

    public CheckBox[] skills;
    private List ClassInfo, RaceInfo, BackgroundInfo;
    private String[] classSkills;
    private boolean[] checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character);

        // Get character name and level
        String name = getIntent().getStringExtra("UserName");
        int level = getIntent().getIntExtra("UserLevel", 1);

        // Get user spinner input from CreateCharacter.java
        String UserClass = getIntent().getStringExtra("UserClass");
        String UserRace = getIntent().getStringExtra("UserRace");
        String UserSubRace = getIntent().getStringExtra("UserSubRace");
        String UserBackground = getIntent().getStringExtra("UserBackground");

        //Finds all the IDs for the skill checkboxes
        findViewForSkills();

        // Open connection to database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter.this);

        // Checks the appropriate skills as proficient as determined by the background
        BackgroundInfo =  databaseAccess.getBackgroundInfo(UserBackground);
        setBackgroundProficiencies(BackgroundInfo.get(1).toString(), BackgroundInfo.get(2).toString());

        // Checks the appropriate skills as proficient as determined by the race
        if (UserSubRace.equals("None")) {
            RaceInfo = databaseAccess.getRaceInfo(UserRace, false);
        }

        else {
            RaceInfo = databaseAccess.getRaceInfo(UserSubRace, true);
        }

        if (RaceInfo.get(5) != null) {
            setRaceProficiencies(RaceInfo.get(5).toString());
        }

        // Parse and assign skill proficiencies as determined by the class
        ClassInfo = databaseAccess.getClassInfo(UserClass);
        setSkillProficiencies(ClassInfo.get(6).toString());

        alertDialogClassSkills(ClassInfo.get(5).toString());

     }

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
        for (int i = 0; i < 18; i++) {
            //Compares the background proficiencies with the checkbox array
            if (skill_1.equals(skills[i].getText().toString()) || skill_2.equals(skills[i].getText().toString())) {
                skills[i].setChecked(true);
            }
        }
    }

    // Checks the race skill proficiencies
    private void setRaceProficiencies(String skill) {
        for (int i = 0; i < 18; i++) {
            if (skill.equals(skills[i].getText().toString())) {
                skills[i].setChecked(true);
            }
        }
    }

    // Allows the user to pick their class proficiencies
    private void setSkillProficiencies(String skillColumn) {
        // Divide skill column into string array
        classSkills = skillColumn.replace(", ", ",").split(",");

        // Contains if a class skill is checked or not
        checked = new boolean[classSkills.length];

        // Finds previously checked skills and checks those skills in the alert dialog
        for (int i = 0; i < skills.length; i++) {
            if (skills[i].isChecked()) {
                for (int j = 0; j < classSkills.length; j++) {
                    if (skills[i].getText().toString().equals(classSkills[j])) {
                        checked[j] = true;
                        break;
                    }
                }
            }
        }
    }

    // Creates dialog box for user to assign skill proficiencies
    private void alertDialogClassSkills(String maxSkills) {
        // Maximum number of skills the user can choose
        final int max = Integer.parseInt(maxSkills);

        AlertDialog.Builder builder = new AlertDialog.Builder(ExpandCharacter.this);
        builder.setTitle("Class skill proficiencies");
        builder.setCancelable(true);

        builder.setMultiChoiceItems(classSkills, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        })

        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

}
