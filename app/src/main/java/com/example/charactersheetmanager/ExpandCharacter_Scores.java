package com.example.charactersheetmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpandCharacter_Scores extends AppCompatActivity {

    private List ClassInfo, RaceInfo, BackgroundInfo, ArmorInfo, WeaponInfo;
    private int level, UserHP, UserAC;
    private int[] abilities = new int[6];
    private int[] abilityMods = new int[6];
    private EditText inputStrength, inputDexterity, inputConstitution, inputIntelligence, inputWisdom, inputCharisma, textInitiative, textHP, textAC;
    private TextView strMod, dexMod, conMod, intMod, wisMod, chaMod, textArmorType, textWeaponType, textWeaponDamage, textWeaponProperties;
    private Spinner chooseArmor, chooseWeapon;
    private String Table, preArmor, preWeapon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character_scores);

        setTitle("Ability Scores");

        ClassInfo = getIntent().getStringArrayListExtra("ClassInfo");
        RaceInfo = getIntent().getStringArrayListExtra("RaceInfo");
        BackgroundInfo = getIntent().getStringArrayListExtra("BackgroundInfo");
        level = getIntent().getIntExtra("level", 1);

        findViewForFields();

        // Gets the hit die of class
        final int hitDie = Integer.valueOf(ClassInfo.get(2).toString());

        // Checks if the user clicked on a pre-existing character
        if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
            ArrayList<String> CharacterInfo = getIntent().getStringArrayListExtra("CharacterInfo");

            int[] preAbilities = new int[6];
            int[] preModifiers = new int[6];

            // Parses string by removing brackets and splitting on ", "
            String removeBracketsAbility = CharacterInfo.get(7).replaceAll("\\[", "").replaceAll("]", "");
            String removeBracketsMods = CharacterInfo.get(8).replaceAll("\\[", "").replaceAll("]", "");

            String[] parseStringAbility = removeBracketsAbility.split(", ");
            String[] parseStringMod = removeBracketsMods.split(", ");

            // Gathers ability scores and modifiers
            for (int i = 0; i < abilities.length; i++) {
                preAbilities[i] = Integer.parseInt(parseStringAbility[i]);
                preModifiers[i] = Integer.parseInt(parseStringMod[i]);
            }

            updateValues(preAbilities, preModifiers);

            // Fetches the previously selected armor and weapon
            preArmor = CharacterInfo.get(15);
            preWeapon = CharacterInfo.get(16);

            // Fetches created characters HP and AC
            UserHP = Integer.valueOf(CharacterInfo.get(13));
            UserAC = Integer.valueOf(CharacterInfo.get(14));

            textHP.setText(String.valueOf(UserHP));
            textAC.setText(String.valueOf(UserAC));

        }

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        spinnerArmorSetUp(databaseAccess);
        spinnerWeaponSetUp(databaseAccess);

        // nextButton click handler
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pass intent to ExpandCharacter_Proficiencies
                Intent intent = new Intent(ExpandCharacter_Scores.this, ExpandCharacter_Proficiencies.class);

                intent.putStringArrayListExtra("ClassInfo", (ArrayList<String>) ClassInfo);
                intent.putStringArrayListExtra("RaceInfo", (ArrayList<String>) RaceInfo);
                intent.putStringArrayListExtra("BackgroundInfo", (ArrayList<String>) BackgroundInfo);
                intent.putExtra("level", level);

                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                intent.putExtra("UserClass", getIntent().getStringExtra("UserClass"));
                intent.putExtra("UserArchetype", getIntent().getStringExtra("UserArchetype"));
                intent.putExtra("UserRace", getIntent().getStringExtra("UserRace"));
                intent.putExtra("UserSubRace", getIntent().getStringExtra("UserSubRace"));
                intent.putExtra("UserBackground", getIntent().getStringExtra("UserBackground"));
                intent.putExtra("UserSkills", getIntent().getBooleanArrayExtra("UserSkills"));
                intent.putExtra("UserScore", abilities);
                intent.putExtra("UserMod", abilityMods);

                // Pass HP and AC as an int
                intent.putExtra("UserHP", UserHP);
                intent.putExtra("UserAC", UserAC);

                // Pass selected armor and weapon
                intent.putExtra("UserArmor", chooseArmor.getSelectedItem().toString());
                intent.putExtra("UserWeapon", chooseWeapon.getSelectedItem().toString());

                // Passes created character info if it exists
                if (getIntent().getStringArrayListExtra("CharacterInfo") != null) {
                    intent.putExtra("CharacterInfo", getIntent().getStringArrayListExtra("CharacterInfo"));
                }

                startActivity(intent);
            }
        });

        // Save stat and create modifier
        inputStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[0] = validInput(v);
                    abilityMods[0] = getModifier(abilities[0]);
                    strMod.setText(String.valueOf(abilityMods[0]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputDexterity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[1] = validInput(v);
                    abilityMods[1] = getModifier(abilities[1]);
                    dexMod.setText(String.valueOf(abilityMods[1]));

                    // Sets initiative bonus
                    textInitiative.setText(String.valueOf(abilityMods[1]));

                    // Set AC based on new dexterity modifier
                    textAC.setText(calculateAC(ArmorInfo, abilityMods[1]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputConstitution.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[2] = validInput(v);
                    abilityMods[2] = getModifier(abilities[2]);
                    conMod.setText(String.valueOf(abilityMods[2]));

                    textHP.setText(rollHitPoints(hitDie));
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputIntelligence.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[3] = validInput(v);
                    abilityMods[3] = getModifier(abilities[3]);
                    intMod.setText(String.valueOf(abilityMods[3]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputWisdom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[4] = validInput(v);
                    abilityMods[4] = getModifier(abilities[4]);
                    wisMod.setText(String.valueOf(abilityMods[4]));
                }
                return true;
            }
        });

        // Save stat and create modifier
        inputCharisma.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check bounds and get modifier
                if (validInput(v) != -1) {
                    abilities[5] = validInput(v);
                    abilityMods[5] = getModifier(abilities[5]);
                    chaMod.setText(String.valueOf(abilityMods[5]));
                }
                return true;
            }
        });

        chooseArmor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String armor = chooseArmor.getSelectedItem().toString();
                ArmorInfo = databaseAccess.getArmorInfo(armor);

                textAC.setText(calculateAC(ArmorInfo, abilityMods[1]));
                textArmorType.setText(ArmorInfo.get(1).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chooseWeapon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String weapon = chooseWeapon.getSelectedItem().toString();
                WeaponInfo = databaseAccess.getWeaponInfo(weapon);

                String weaponDamage = WeaponInfo.get(2).toString() + "d" + WeaponInfo.get(3).toString();
                String weaponProperties;

                if (WeaponInfo.get(6) != null) {
                    weaponProperties = WeaponInfo.get(6).toString();
                    textWeaponProperties.setText(weaponProperties);
                }

                else {
                    String none = "None";
                    textWeaponProperties.setText(none);
                }

                textWeaponType.setText(WeaponInfo.get(1).toString());
                textWeaponDamage.setText(weaponDamage);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // Find the view ID for all fields
    private void findViewForFields() {
        // Views for editText fields
        inputStrength = findViewById(R.id.inputStrength);
        inputDexterity = findViewById(R.id.inputDexterity);
        inputConstitution = findViewById(R.id.inputConstitution);
        inputIntelligence = findViewById(R.id.inputIntelligence);
        inputWisdom = findViewById(R.id.inputWisdom);
        inputCharisma = findViewById(R.id.inputCharisma);
        textInitiative = findViewById(R.id.textInitiative);
        textHP = findViewById(R.id.textHP);
        textAC = findViewById(R.id.textAC);

        // Views for textView fields
        strMod = findViewById(R.id.textStrengthMod);
        dexMod = findViewById(R.id.textDexterityMod);
        conMod = findViewById(R.id.textConstitutionMod);
        intMod = findViewById(R.id.textIntelligenceMod);
        wisMod = findViewById(R.id.textWisdomMod);
        chaMod = findViewById(R.id.textCharismaMod);
        textArmorType = findViewById(R.id.textArmorType);
        textWeaponType = findViewById(R.id.textWeaponType);
        textWeaponDamage = findViewById(R.id.textWeaponDamage);
        textWeaponProperties = findViewById(R.id.textWeaponProperties);
    }

    // Updates all values if opening an existing character
    private void updateValues(int[] preAbilities, int[] preModifiers) {
        // Set editText text and populates abilities array and modifiers array with pre-existing values
        inputStrength.setText(Integer.toString(preAbilities[0]));
        abilities[0] = preAbilities[0];
        strMod.setText(Integer.toString(preModifiers[0]));
        abilityMods[0] = preModifiers[0];

        inputDexterity.setText(Integer.toString(preAbilities[1]));
        abilities[1] = preAbilities[1];
        dexMod.setText(Integer.toString(preModifiers[1]));
        abilityMods[1] = preModifiers[1];

        inputConstitution.setText(Integer.toString(preAbilities[2]));
        abilities[2] = preAbilities[2];
        conMod.setText(Integer.toString(preModifiers[2]));
        abilityMods[2] = preModifiers[2];

        inputIntelligence.setText(Integer.toString(preAbilities[3]));
        abilities[3] = preAbilities[3];
        intMod.setText(Integer.toString(preModifiers[3]));
        abilityMods[3] = preModifiers[3];

        inputWisdom.setText(Integer.toString(preAbilities[4]));
        abilities[4] = preAbilities[4];
        wisMod.setText(Integer.toString(preModifiers[4]));
        abilityMods[4] = preModifiers[4];

        inputCharisma.setText(Integer.toString(preAbilities[5]));
        abilities[5] = preAbilities[5];
        chaMod.setText(Integer.toString(preModifiers[5]));
        abilityMods[5] = preModifiers[5];

        textInitiative.setText(Integer.toString(preModifiers[1]));
    }

    // Check for valid input and within bounds
    private int validInput(TextView input) {
        // Check that the input field isn't empty
        if (!input.getText().toString().equals("")) {
            String statString = input.getText().toString();
            int statInt = Integer.parseInt(statString);

            // Check that the input is within range
            if (statInt > 30 || statInt < 0) {
                input.setError("Between 0 and 30");
                return -1;
            }
            else return statInt;
        }
        return -1;
    }

    // Gets the modifier of an ability
    private int getModifier(Integer stat) { return (stat - 10) / 2; }

    // Rolls the health points based on class and constitution modifier
    private String rollHitPoints(int hitDie) {

        int total = 0;

        // If level 1, the HP is simply the class's hit die + constitution modifier
        if (level == 1) {
            total = (hitDie + abilityMods[2]);
            UserHP = total;

            return Integer.toString(hitDie + abilityMods[2]);
        }


        // If any level greater than 1, the HP is the random hit die roll + constitution modifier
        for (int i = 0; i < level; i++) {
            if (i == 0) {
                total = hitDie + abilityMods[2];
            }

            else {
                total += (1 + (int)(Math.random() * ((hitDie - 1) + 1))) + abilityMods[2];

            }

        }

        UserHP = total;
        return Integer.toString(total);

    }

    private String calculateAC(List ArmorInfo, int modifier) {
        if (ArmorInfo.get(3) != null) {
            if (modifier > Integer.valueOf(ArmorInfo.get(3).toString())) {
                modifier = Integer.valueOf(ArmorInfo.get(3).toString());
            }

        }

        int total = Integer.valueOf(ArmorInfo.get(2).toString()) + modifier;
        UserAC = total;
        return Integer.toString(total);
    }

    private void spinnerArmorSetUp(DatabaseAccess databaseAccess) {
        chooseArmor = findViewById(R.id.spinnerArmor);
        Table = "Armor";
        String[] armorList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterArmor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, armorList);
        adapterArmor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseArmor.setAdapter(adapterArmor);

        // Sets spinner to selected armor if opening an existing character
        if (preArmor != null) {
            int position = adapterArmor.getPosition(preArmor);
            chooseArmor.setSelection(position);
        }

    }

    private void spinnerWeaponSetUp(DatabaseAccess databaseAccess) {
        chooseWeapon = findViewById(R.id.spinnerWeapon);
        Table = "Weapon";
        String[] weaponList = databaseAccess.getList(Table);

        ArrayAdapter<String> adapterWeapon = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weaponList);
        adapterWeapon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseWeapon.setAdapter(adapterWeapon);

        // Sets spinner to selected weapon if opening an existing character
        if (preWeapon != null) {
            int position = adapterWeapon.getPosition(preWeapon);
            chooseWeapon.setSelection(position);
        }

    }

}