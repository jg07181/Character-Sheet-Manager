package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class ExpandCharacter_1 extends AppCompatActivity {

    public CheckBox[] skills;
    public CheckBox[] saves;
    private List ClassInfo, RaceInfo, BackgroundInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_1);

        // Get character name and level
        String name = getIntent().getStringExtra("UserName");
        final int level = getIntent().getIntExtra("UserLevel", 1);

        // Get user spinner input from CreateCharacter.java
        String UserClass = getIntent().getStringExtra("UserClass");
        String UserRace = getIntent().getStringExtra("UserRace");
        String UserSubRace = getIntent().getStringExtra("UserSubRace");
        String UserBackground = getIntent().getStringExtra("UserBackground");

        // Finds all the IDs for the skill checkboxes
        findViewForSkills();

        // Open connection to database
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter_1.this);

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

        if (RaceInfo.get(4) != null) {
            setRaceProficiencies(RaceInfo.get(4).toString());
        }

        // Parse and assign skill proficiencies as determined by the class
        ClassInfo = databaseAccess.getClassInfo(UserClass);
        setClassSkills(ClassInfo.get(7).toString());

        // Maximum number of skills the user can choose
        int maximum = Integer.parseInt(ClassInfo.get(6).toString());

        TextView skillMessage = findViewById(R.id.skills_message);
        String message = "Class: " + ClassInfo.get(0).toString() + "\n" + "Choose " + maximum + " skills!";
        skillMessage.setText(message);

        // Set saving throw proficiencies as determined by the class
        setSavingThrows(ClassInfo.get(3).toString());

        // Set both armor and weapon proficiencies
        setArmorAndWeaponProficiencies();

        // Next Button
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpandCharacter_1.this, ExpandCharacter_2.class);

                if (RaceInfo.get(13) != null) {
                    intent.putExtra("Abilities", RaceInfo.get(13).toString());
                }

                // Get array of feature names
                String[] classAbilities = databaseAccess.getClassAbilities(ClassInfo.get(0).toString(), level);
                intent.putExtra("classAbilities", classAbilities);

                // Get array of feature descriptions
                String[] abilityDesc = databaseAccess.getClassDescriptions(ClassInfo.get(0).toString(), level);
                intent.putExtra("descriptions", abilityDesc);

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

        for (CheckBox skill : skills) {
            skill.setEnabled(false);
        }

    }

    // Checks background skill proficiencies
    private  void setBackgroundProficiencies(String skill_1, String skill_2) {
        for (CheckBox skill : skills) {
            // Compares the background proficiencies with the checkbox array
            if (skill_1.equals(skill.getText().toString()) || skill_2.equals(skill.getText().toString())) {
                skill.setChecked(true);
            }

        }

    }

    // Checks the race skill proficiencies
    private void setRaceProficiencies(String skill) {
        // Checks if a skill is specified or simply a number to choose from
        if (!android.text.TextUtils.isDigitsOnly(skill)) {
            for (CheckBox skill1 : skills) {
                if (skill.equals(skill1.getText().toString())) {
                    skill1.setChecked(true);
                }

            }

        }

        else {
            int num = Integer.parseInt(skill);

        }

    }

    // Checks the appropriate saving throw checkbox based on class
    private void setSavingThrows(String Saves) {
        CheckBox saveStr = findViewById(R.id.checkbox_Str_Save);
        CheckBox saveDex = findViewById(R.id.checkbox_Dex_Save);
        CheckBox saveCon = findViewById(R.id.checkbox_Con_Save);
        CheckBox saveInt = findViewById(R.id.checkbox_Int_Save);
        CheckBox saveWis = findViewById(R.id.checkbox_Wis_Save);
        CheckBox saveCha = findViewById(R.id.checkbox_Cha_Save);

        saves = new CheckBox[] {saveStr, saveDex, saveCon, saveInt, saveWis, saveCha};

        String[] classSaves = Saves.replace(", ", ",").split(",");

        for (CheckBox save : saves) {
            for (String classSave : classSaves) {
                if (classSave.equals(save.getText().toString())) {
                    save.setChecked(true);
                }

            }

        }

    }

    // Allows the user to pick their class proficiencies
    private void setClassSkills(String possibleSkills) {
        // Divide skill column into string array
        String[] classSkills = possibleSkills.replace(", ", ",").split(",");

        // Enables checkable skills based on possible class skills
        for (CheckBox skill : skills) {
            for (String classSkill : classSkills) {
                if (classSkill.equals(skill.getText().toString())) {
                    skill.setEnabled(true);
                }

            }

        }

    }

    // Set weapon and armor proficiencies as determined by the class and race
    private void setArmorAndWeaponProficiencies() {
        // if statements for weapons
        if (RaceInfo.get(6) != null) {
            setWeaponProficiencies(ClassInfo.get(5).toString(), RaceInfo.get(6).toString());
        }

        else {
            setWeaponProficiencies(ClassInfo.get(5).toString(), "None");
        }

        // if statements for armor
        if (ClassInfo.get(4) != null && RaceInfo.get(5) != null) {
            setArmorProficiencies(ClassInfo.get(4).toString(), RaceInfo.get(5).toString());
        }

        else if (ClassInfo.get(4) != null && RaceInfo.get(5) == null) {
            setArmorProficiencies(ClassInfo.get(4).toString(), "None");
        }

        else if (ClassInfo.get(4) == null && RaceInfo.get(5) != null) {
            setArmorProficiencies("None", RaceInfo.get(5).toString());
        }

        else {
            setArmorProficiencies("None", "None");
        }
    }

    // Set armor proficiencies by looking at class and race attributes
    private void setArmorProficiencies(String classArmor, String raceArmor) {
        String[] armorSet1 = classArmor.replace(", ", ",").split(",");
        String[] armorSet2 = raceArmor.replace(", ", ",").split(",");

        RadioButton lightArmor = findViewById(R.id.radio_Light_Armor);
        RadioButton mediumArmor = findViewById(R.id.radio_Medium_Armor);
        RadioButton heavyArmor = findViewById(R.id.radio_Heavy_Armor);
        RadioButton shield = findViewById(R.id.radio_Shield);

        // Check class armor proficiencies
        if (!classArmor.equals("None")) {
            for (String anArmorSet1 : armorSet1) {
                switch (anArmorSet1) {
                    case "Light Armor":
                        lightArmor.setChecked(true);
                        break;
                    case "Medium Armor":
                        mediumArmor.setChecked(true);
                        break;
                    case "Heavy Armour":
                        heavyArmor.setChecked(true);
                        break;
                    case "Shield":
                        shield.setChecked(true);
                        break;
                }

            }

        }

        if (!raceArmor.equals("None")) {
            for (String anArmorSet2 : armorSet2) {
                switch (anArmorSet2) {
                    case "Light Armor":
                        lightArmor.setChecked(true);
                        break;
                    case "Medium Armor":
                        mediumArmor.setChecked(true);
                        break;
                    case "Heavy Armour":
                        heavyArmor.setChecked(true);
                        break;
                    case "Shield":
                        shield.setChecked(true);
                        break;
                }

            }

        }

    }

    // Set weapon proficiencies by looking at class and race attributes
    private void setWeaponProficiencies(String classWeapons, String raceWeapons) {
        String[] weaponSet1 = classWeapons.replace("; ", ";").split(";");
        String[] weaponSet2 = raceWeapons.replace("; ", ";").split(";");

        RadioButton simpleWeapons = findViewById(R.id.radio_Simple_Weapon);
        RadioButton martialWeapons = findViewById(R.id.radio_Martial_Weapon);
        RadioButton otherWeapons = findViewById(R.id.radio_Other_Weapon);
        TextView otherWeaponText = findViewById(R.id.textView_Other_weapons);

        // Check class weapon proficiencies
        for (String aWeaponSet1 : weaponSet1) {
            switch (aWeaponSet1) {
                case "Simple Melee Weapon":
                case "Simple Ranged Weapon":
                    simpleWeapons.setChecked(true);
                    break;

                case "Martial Melee Weapon":
                case "Martial Ranged Weapon":
                    martialWeapons.setChecked(true);
                    break;

                default:
                    otherWeapons.setChecked(true);
                    otherWeaponText.append(aWeaponSet1 + "; ");
                    break;
            }

        }

        // Checks if the race has weapon proficiencies
        // If yes, check other weapons and add those weapons to TextView
        if (!raceWeapons.equals("None")) {
            for (String aWeaponSet2 : weaponSet2) {
                otherWeapons.setChecked(true);
                otherWeaponText.append(aWeaponSet2 + "; ");
            }

        }

        if (!otherWeaponText.getText().toString().isEmpty()) {
            // Removes trailing comma from other weapons TextView
            String text = otherWeaponText.getText().toString().replaceAll("; $", "");
            otherWeaponText.setText(text);
            otherWeaponText.setVisibility(View.VISIBLE);
        }

    }

}
