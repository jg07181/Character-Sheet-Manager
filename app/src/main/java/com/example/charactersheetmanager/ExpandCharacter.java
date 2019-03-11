package com.example.charactersheetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandCharacter extends AppCompatActivity {

    public CheckBox[] skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character);

        ArrayList<String> classInfo = getIntent().getStringArrayListExtra("classInfo");
        ArrayList<String> raceInfo = getIntent().getStringArrayListExtra("raceInfo");
        ArrayList<String> backgroundInfo = getIntent().getStringArrayListExtra("backgroundInfo");

        //Finds all the IDs for the skill checkboxes
        findViewForSkills();

        //Checks the appropriate skills as proficient as determined by the background
        setBackgroundProficiencies(backgroundInfo);

        //Parse and assign skill proficiencies as determined by the class
        parseSkillProficiencies(Integer.valueOf(classInfo.get(5)), classInfo.get(6));


        //TextView testBackground = findViewById(R.id.testBackground);
        //TextView testRace = findViewById(R.id.testRace);
        //TextView testClass = findViewById(R.id.testClass);

        //testBackground.setText(TextUtils.join(", ", test1));
        //testRace.setText(TextUtils.join(",", test2));
        //testClass.setText(TextUtils.join(",", test3));
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
        CheckBox Sleight_of_Hand = findViewById(R.id.checkbox_Sleight_of_hand);
        CheckBox Stealth = findViewById(R.id.checkbox_Stealth);
        CheckBox Survival = findViewById(R.id.checkbox_Survival);

        //Adds all checkboxes to skills array
        skills = new CheckBox[] {Acrobatics, Animal_Handling, Arcana, Athletics, Deception, History, Insight, Intimidation, Investigation,
                                 Medicine, Nature, Perception, Performance, Persuasion, Religion, Sleight_of_Hand, Stealth, Survival};

    }

    private  void setBackgroundProficiencies(ArrayList backgroundSkills) {
        for (int i = 0; i < 18; i++) {
            //Compares the background proficiencies with the checkbox array
            if (backgroundSkills.get(1).equals(skills[i].getText().toString()) || backgroundSkills.get(2).equals(skills[i].getText().toString())) {
                skills[i].setChecked(true);
            }
        }
    }

    private void parseSkillProficiencies(Integer maxSkills, String skills) {
        //Maximum number of skills the user can choose
        final int max = maxSkills;

        //Split string based on delimiter ","
        final String[] stringSkills = skills.replace(", ", ",").split(",");

        boolean[] checked = new boolean[stringSkills.length];

        //Creates dialog box for user to assign skill proficiencies
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose your class skill proficiencies");

        alert.setMultiChoiceItems(stringSkills, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                //Need logic here
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();

    }
}
