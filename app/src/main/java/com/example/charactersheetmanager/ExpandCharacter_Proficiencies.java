package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ExpandCharacter_Proficiencies extends AppCompatActivity {

    private List ClassInfo, RaceInfo;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_proficiencies);

        setTitle("Proficiencies");

        // Get intent values
        ClassInfo = getIntent().getStringArrayListExtra("ClassInfo");
        RaceInfo = getIntent().getStringArrayListExtra("RaceInfo");
        level = getIntent().getIntExtra("level", 1);

        // Set saving throw proficiencies as determined by the class
        setSavingThrows(ClassInfo.get(3).toString());

        // Set both armor and weapon proficiencies
        setArmorAndWeaponProficiencies(ClassInfo, RaceInfo);

        // Set languages
        setLanguages(RaceInfo.get(9).toString());

        // Set tools
        if (RaceInfo.get(7) != null) {
            setTools(RaceInfo.get(7).toString());
        }

        else {
            setTools("None");
        }

        // nextButton click handler
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpandCharacter_Proficiencies.this, ExpandCharacter_Abilities.class);

                if (RaceInfo.get(11) != null) {
                    intent.putExtra("raceAbilities", RaceInfo.get(11).toString());
                    intent.putExtra("raceDescriptions", RaceInfo.get(12).toString());
                }

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ExpandCharacter_Proficiencies.this);

                // Get array of feature names
                String[] classAbilities = databaseAccess.getClassAbilities(ClassInfo.get(0).toString(), level);
                intent.putExtra("classAbilities", classAbilities);

                // Get array of feature descriptions
                String[] abilityDesc = databaseAccess.getClassDescriptions(ClassInfo.get(0).toString(), level);
                intent.putExtra("descriptions", abilityDesc);

                // Get int array of ability levels
                int[] abilityLevel = databaseAccess.getClassLevels(ClassInfo.get(0).toString(), level);
                intent.putExtra("levels", abilityLevel);

                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                intent.putExtra("UserLevel", level);
                intent.putExtra("UserClass", getIntent().getStringExtra("UserClass"));
                intent.putExtra("UserArchetype", getIntent().getStringExtra("UserArchetype"));
                intent.putExtra("UserRace", getIntent().getStringExtra("UserRace"));
                intent.putExtra("UserSubRace", getIntent().getStringExtra("UserSubRace"));
                intent.putExtra("UserBackground", getIntent().getStringExtra("UserBackground"));
                intent.putExtra("UserScore", getIntent().getIntArrayExtra("UserScore"));
                intent.putExtra("UserMod", getIntent().getIntArrayExtra("UserMod"));

                startActivity(intent);

            }
        });

    }

    // Checks the appropriate saving throw checkbox based on class
    private void setSavingThrows(String Saves) {
        CheckBox saveStr = findViewById(R.id.checkbox_Str_Save);
        CheckBox saveDex = findViewById(R.id.checkbox_Dex_Save);
        CheckBox saveCon = findViewById(R.id.checkbox_Con_Save);
        CheckBox saveInt = findViewById(R.id.checkbox_Int_Save);
        CheckBox saveWis = findViewById(R.id.checkbox_Wis_Save);
        CheckBox saveCha = findViewById(R.id.checkbox_Cha_Save);

        CheckBox[] saves = new CheckBox[]{saveStr, saveDex, saveCon, saveInt, saveWis, saveCha};
        String[] classSaves = Saves.replace(", ", ",").split(",");

        for (CheckBox save : saves) {
            for (String classSave : classSaves) {
                if (classSave.equals(save.getText().toString())) {
                    save.setChecked(true);
                }

            }

        }

    }

    // Set weapon and armor proficiencies as determined by the class and race
    private void setArmorAndWeaponProficiencies(List ClassInfo, List RaceInfo) {
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

    private void setLanguages(String language) {
        TextView textLanguage = findViewById(R.id.textLanguage);
        String[] languages = language.split(",");

        for (String language1 : languages) {
            textLanguage.append(language1 + ", ");
        }

        String text = textLanguage.getText().toString().replaceAll(", $", "");
        textLanguage.setText(text);

    }

    private void setTools(String tool) {
        TextView textTool = findViewById(R.id.textTool);
        String[] tools = tool.split(";");

        for (String tool1 : tools) {
            textTool.append(tool1 + ", ");
        }

        String text = textTool.getText().toString().replaceAll(", $", "");
        textTool.setText(text);

    }

}
