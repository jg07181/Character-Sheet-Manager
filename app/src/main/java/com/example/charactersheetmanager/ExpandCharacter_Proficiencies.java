package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpandCharacter_Proficiencies extends AppCompatActivity {

    private List ClassInfo, RaceInfo, BackgroundInfo;
    private String allLanguages, allTools, allOtherWeapons;
    private int level;

    private EditText textLanguage, textTool, otherWeaponText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_proficiencies);

        setTitle("Proficiencies");

        // Get intent values
        ClassInfo = getIntent().getStringArrayListExtra("ClassInfo");
        RaceInfo = getIntent().getStringArrayListExtra("RaceInfo");
        BackgroundInfo = getIntent().getStringArrayListExtra("BackgroundInfo");
        level = getIntent().getIntExtra("level", 1);

        textLanguage = findViewById(R.id.textLanguage);
        textLanguage.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textLanguage.setRawInputType(InputType.TYPE_CLASS_TEXT);

        textTool = findViewById(R.id.textTool);
        textTool.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textTool.setRawInputType(InputType.TYPE_CLASS_TEXT);

        otherWeaponText = findViewById(R.id.editText_Other_weapons);
        otherWeaponText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        otherWeaponText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Checks if the user clicked on a pre-existing character
        if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
            ArrayList<String> CharacterInfo = getIntent().getStringArrayListExtra("CharacterInfo");

            String preLanguage = CharacterInfo.get(10);
            String preTool = CharacterInfo.get(11);
            String preOtherWeapons = CharacterInfo.get(12);

            textLanguage.setText(preLanguage);
            textTool.setText(preTool);
            otherWeaponText.setText(preOtherWeapons);
        }

        else {
            // Set saving throw proficiencies as determined by the class
            setSavingThrows(ClassInfo.get(3).toString());

            // Set both armor and weapon proficiencies
            setArmorAndWeaponProficiencies(ClassInfo, RaceInfo);

            // Set languages
            if (BackgroundInfo.get(3) != null) {
                setLanguages(RaceInfo.get(9).toString(), BackgroundInfo.get(3).toString());
            }

            else {
                setLanguages(RaceInfo.get(9).toString(), "");
            }

            // Set tools
            findTools();
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
                intent.putExtra("UserSkills", getIntent().getBooleanArrayExtra("UserSkills"));
                intent.putExtra("UserLanguage", allLanguages);
                intent.putExtra("UserTool", allTools);
                intent.putExtra("UserOtherWeapon", allOtherWeapons);
                intent.putExtra("UserHP", getIntent().getIntExtra("UserHP", 0));
                intent.putExtra("UserAC", getIntent().getIntExtra("UserAC", 0));
                intent.putExtra("UserArmor", getIntent().getStringExtra("UserArmor"));
                intent.putExtra("UserWeapon", getIntent().getStringExtra("UserWeapon"));

                // Passes created character info if it exists
                if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
                    intent.putExtra("CharacterInfo", getIntent().getStringArrayListExtra("CharacterInfo"));
                }

                startActivity(intent);

            }
        });

        textLanguage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                allLanguages = textLanguage.getText().toString();
                return false;
            }
        });

        textTool.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                allTools = textTool.getText().toString();
                return false;
            }
        });

        otherWeaponText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                allOtherWeapons = otherWeaponText.getText().toString();
                return false;
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

            allOtherWeapons = text;
        }

        else {
            String none = "None";
            otherWeaponText.setText(none);

            allOtherWeapons = none;
        }

    }

    // Sets language proficiencies based on race and background
    private void setLanguages(String raceLanguage, String backgroundLanguage) {
        String[] languages = raceLanguage.split(",");

        // Append race languages
        for (String language1 : languages) {
            textLanguage.append(language1 + ", ");
        }

        // Append background languages
        if (backgroundLanguage != null) {
            textLanguage.append(backgroundLanguage);
        }

        // Delete trailing comma
        String text = textLanguage.getText().toString().replaceAll(", $", "");
        textLanguage.setText(text);

        allLanguages = text;

    }

    private void findTools() {
        if (RaceInfo.get(7) != null && BackgroundInfo.get(4) != null && BackgroundInfo.get(5) != null) {
            setTools(RaceInfo.get(7).toString(), BackgroundInfo.get(4).toString(), BackgroundInfo.get(5).toString());
        }

        else if (RaceInfo.get(7) != null && BackgroundInfo.get(4) != null) {
            setTools(RaceInfo.get(7).toString(), BackgroundInfo.get(4).toString(), "");
        }

        else if (RaceInfo.get(7) == null && BackgroundInfo.get(4) != null && BackgroundInfo.get(5) != null) {
            setTools("", BackgroundInfo.get(4).toString(), BackgroundInfo.get(5).toString());
        }

        else if (BackgroundInfo.get(4) != null) {
            setTools("", BackgroundInfo.get(4).toString(), "");
        }

        else if (RaceInfo.get(7) != null) {
            setTools(RaceInfo.get(7).toString(), "", "");
        }

        else {
            setTools("", "", "");
        }
    }

    // Sets tool proficiencies based on race, background, and class
    private void setTools(String tool, String bgTool_1, String bgTool_2) {
        String[] tools = tool.split(";");

        // Append racial tools
        for (String tool1 : tools) {
            if (!tool1.equals("")) {
                textTool.append(tool1 + ", ");
            }

        }

        // Append first background tool
        if (!bgTool_1.equals("")) {
            textTool.append(bgTool_1);
        }

        // Append second background tool if any
        if (!bgTool_2.equals("")) {
            textTool.append(", " + bgTool_2);
        }

        // Append class tools if any
        if (ClassInfo.get(8) != null) {
            textTool.append(", " + ClassInfo.get(8).toString());
        }

        // If no tool proficiencies, then say "None"
        if (textTool.getText().toString().equals("")) {
            String none = "None";
            textTool.setText(none);
        }

        // Escape apostrophes for sql statement
        if (textTool.getText().toString().contains("'")) {
            String escape = textTool.getText().toString().replace("'", "''");

            // Delete trailing comma
            allTools = escape.replaceAll(", $", "");
        }

        else {
            // Delete trailing comma
            String text = textTool.getText().toString().replaceAll(", $", "");
            textTool.setText(text);

            allTools = text;
        }

    }

}