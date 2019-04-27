package com.example.charactersheetmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    // Constructor
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    // Returns singleton instance of DatabaseAccess
    static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    // Open database connection
    void open() {
        this.db = openHelper.getWritableDatabase();
    }

    String[] getList(String table) {
        Cursor c;

        if (table.equals("Armor") || table.equals("Weapon")) {
            c = db.rawQuery("SELECT DISTINCT name FROM " + table + " ORDER BY ROWID ASC", null);
        }

        else {
            c = db.rawQuery("SELECT DISTINCT name FROM " + table + " ORDER BY name ASC", null);
        }

        ArrayList<String> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String item = c.getString(c.getColumnIndexOrThrow("name"));
                list.add(item);
            }
            while (c.moveToNext());
        }

        c.close();

        String[] completeList = new String[list.size()];
        completeList = list.toArray(completeList);
        return completeList;
    }

    String[] getSubRace(String race) {
        Cursor c = db.rawQuery("SELECT sub_name FROM Race WHERE name = '" + race + "' ORDER BY sub_name ASC", null);
        ArrayList<String> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String sub_race = c.getString(c.getColumnIndexOrThrow("sub_name"));

                // If a race doesn't have a sub race, set sub race as "None"
                if (sub_race == null) {
                    sub_race = "None";
                }
                list.add(sub_race);
            }
            while (c.moveToNext());
        }
        c.close();

        String[] completeList = new String[list.size()];
        completeList = list.toArray(completeList);
        return completeList;
    }

    List getClassInfo(String Class) {
        Cursor c = db.rawQuery("SELECT * FROM Class WHERE name = '" + Class + "'", null);

        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add class attributes to values arrayList
        values.add(c.getString(0)); // Class name
        values.add(c.getString(1)); // Archetype
        values.add(c.getString(2)); // Hit die
        values.add(c.getString(3)); // Saving throws
        values.add(c.getString(4)); // Armor proficiencies
        values.add(c.getString(5)); // Weapon proficiencies
        values.add(c.getString(6)); // Max number of skills to choose
        values.add(c.getString(7)); // Skill proficiencies
        values.add(c.getString(8)); // Tool proficiencies

        c.close();
        return values;
    }

    String[] getClassAbilities(String Class, int level) {
        Cursor c = db.rawQuery("SELECT features FROM " + Class + " WHERE level >= 1 AND level <= " + level + " ORDER BY level", null);
        ArrayList<String> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String feature = c.getString(c.getColumnIndexOrThrow("features"));

                list.add(feature);
            }
            while (c.moveToNext());
        }
        c.close();

        String[] featureList = new String[list.size()];
        featureList = list.toArray(featureList);
        return featureList;
    }

    String[] getClassDescriptions(String Class, int level) {
        Cursor c = db.rawQuery("SELECT description FROM " + Class + " WHERE level >= 1 AND level <= " + level + " ORDER BY level", null);
        ArrayList<String> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String desc = c.getString(c.getColumnIndexOrThrow("description"));

                list.add(desc);
            }
            while (c.moveToNext());
        }
        c.close();

        String[] featureList = new String[list.size()];
        featureList = list.toArray(featureList);
        return featureList;
    }

    int[] getClassLevels(String Class, int level) {
        Cursor c = db.rawQuery("SELECT level FROM " + Class + " WHERE level >= 1 AND level <= " + level + " ORDER BY level", null);
        ArrayList<Integer> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                int lvl = c.getInt(c.getColumnIndexOrThrow("level"));
                list.add(lvl);
            }
            while (c.moveToNext());
        }
        c.close();

        int[] levelList = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            levelList[i] = list.get(i);
        }

        return levelList;
    }

    String[] getArchetypeList(String Class) {
        Cursor c = db.rawQuery("SELECT archetype FROM Class WHERE name = '" + Class + "' ORDER BY archetype ASC", null);

        c.moveToFirst();
        String[] values = c.getString(0).replace(", ", ",").split(",");
        c.close();

        return values;
    }

    List getRaceInfo(String Race, boolean isSubRace) {
        Cursor c;

        // Checks is the the selected race has a subrace or not
        if (!isSubRace) {
            c = db.rawQuery("SELECT * FROM Race WHERE name = '" + Race + "'", null);
        }

        else {
            c = db.rawQuery("SELECT * FROM Race WHERE sub_name = '" + Race + "'", null);
        }

        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add race attributes to values arrayList
        values.add(c.getString(0));  // Race name
        values.add(c.getString(1));  // Sub-race name
        values.add(c.getString(2));  // Ability score
        values.add(c.getString(3));  // Size
        values.add(c.getString(4));  // Skill proficiencies
        values.add(c.getString(5));  // Armor proficiencies
        values.add(c.getString(6));  // Weapon proficiencies
        values.add(c.getString(7));  // Tool proficiencies
        values.add(c.getString(8));  // Speed
        values.add(c.getString(9));  // Languages
        values.add(c.getString(10)); // Resistances
        values.add(c.getString(11)); // Features
        values.add(c.getString(12)); // Feature description

        c.close();
        return values;
    }

    List getBackgroundInfo(String Background) {
        Cursor c = db.rawQuery("SELECT * FROM Background WHERE name = '" + Background + "'", null);

        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add background attributes to values arrayList
        values.add(c.getString(0)); // Background name
        values.add(c.getString(1)); // Skill proficiency #1
        values.add(c.getString(2)); // Skill proficiency #2
        values.add(c.getString(3)); // Number of extra languages
        values.add(c.getString(4)); // Tool proficiency #1
        values.add(c.getString(5)); // Tool proficiency #2

        c.close();
        return values;
    }

    List getArmorInfo(String Armor) {
        Cursor c = db.rawQuery("SELECT * FROM Armor WHERE name = '" + Armor + "'", null);

        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add armor attributes to values arrayList
        values.add(c.getString(0)); // Armor name
        values.add(c.getString(1)); // Armor type
        values.add(c.getString(2)); // Base AC
        values.add(c.getString(3)); // Max modifier
        values.add(c.getString(4)); // Required strength
        values.add(c.getString(5)); // Stealth disadvantage
        values.add(c.getString(6)); // Weight

        c.close();
        return values;
    }

    List getWeaponInfo(String Weapon) {
        Cursor c = db.rawQuery("SELECT * FROM Weapon WHERE name = '" + Weapon + "'", null);

        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add weapon attributes to values arrayList
        values.add(c.getString(0)); // Weapon name
        values.add(c.getString(1)); // Weapon type
        values.add(c.getString(2)); // Number of damage dice
        values.add(c.getString(3)); // Damaged die type
        values.add(c.getString(4)); // Damage type
        values.add(c.getString(5)); // Weight
        values.add(c.getString(6)); // Properties

        c.close();
        return values;
    }

    // Save a newly created character
    void saveCharacter(CompletedCharacter character) {
        String  Name = character.getName(),
                Class = character.getClassName(),
                Archetype = character.getArchetype(),
                Race = character.getRace(),
                SubRace = character.getSubRace(),
                Background = character.getBackground(),
                Language = character.getLanguage(),
                Tool = character.getTool(),
                OtherWeapon = character.getOtherWeapon(),
                Armor = character.getArmor(),
                Weapon = character.getWeapon();

        int Level = character.getLevel(),
            HP = character.getHP(),
            AC = character.getAC();

        int[] Score = character.getScore(),
                Modifier = character.getModifier();

        boolean[] skills = character.getSkills(),
                  saves = character.getSaves(),
                  weaponArmor = character.getWeaponArmor();

        Cursor c = db.rawQuery("INSERT INTO completedCharacter (name, level, class, archetype, race, subrace, background, abilities, modifiers, skills, language, tool, weapon, hp, ac, current_armor, current_weapon, saving_throws, weapon_armor_prof) VALUES " +
                "('" + Name + "','" + Level + "','" + Class + "','" + Archetype + "','" + Race + "','" + SubRace + "','" + Background + "','" + Arrays.toString(Score) + "','" + Arrays.toString(Modifier) + "','" + Arrays.toString(skills) + "','" + Language + "','" + Tool + "','" + OtherWeapon + "','" + HP + "','" + AC + "','" + Armor + "','" + Weapon + "','" + Arrays.toString(saves) + "','" + Arrays.toString(weaponArmor) + "')", null);

        c.moveToNext();
        c.close();
    }

    // Update an existing character
    void updateCharacter(CompletedCharacter character, String id) {
        String  Name = character.getName(),
                Class = character.getClassName(),
                Archetype = character.getArchetype(),
                Race = character.getRace(),
                SubRace = character.getSubRace(),
                Background = character.getBackground(),
                Language = character.getLanguage(),
                Tool = character.getTool(),
                OtherWeapon = character.getOtherWeapon(),
                Armor = character.getArmor(),
                Weapon = character.getWeapon();

        int Level = character.getLevel(),
                HP = character.getHP(),
                AC = character.getAC();

        int[] Score = character.getScore(),
                Modifier = character.getModifier();

        boolean[] skills = character.getSkills(),
                    saves = character.getSaves(),
                    weaponArmor = character.getWeaponArmor();

        Cursor c = db.rawQuery("UPDATE completedCharacter SET name = '" + Name + "', level = '" + Level + "', class = '" + Class + "', archetype = '" + Archetype + "', race = '" + Race + "', subrace = '"  + SubRace + "', background = '" + Background + "', abilities = '" + Arrays.toString(Score) + "', modifiers = '" + Arrays.toString(Modifier) + "', skills = '" + Arrays.toString(skills) + "', language = '" + Language + "', tool = '" + Tool + "', weapon = '" + OtherWeapon + "', hp = '" + HP + "', ac = '" + AC + "', current_armor = '" + Armor + "', current_weapon = '" + Weapon + "', saving_throws = '" + Arrays.toString(saves) + "', weapon_armor_prof = '" + Arrays.toString(weaponArmor) + "'" +
                "WHERE id = '" + id + "'", null);

        c.moveToNext();
        c.close();
    }

    List<String> checkDatabase(String column) {
        Cursor c = db.rawQuery("SELECT " + column + " FROM completedCharacter", null);
        List<String> value = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                String item = c.getString(c.getColumnIndexOrThrow(column));
                value.add(item);
            }
            while (c.moveToNext());
        }
        c.close();
        return value;
    }

    // Remove an existing character
    void removeCharacter(String id) {
        Cursor c = db.rawQuery("DELETE FROM completedCharacter WHERE id = '" + id + "'", null);
        c.moveToFirst();
        c.close();
    }

    // Get all information on a saved character
    List getCharacter(String id) {
        Cursor c = db.rawQuery("SELECT * FROM completedCharacter WHERE id = '" + id + "'", null);
        List<String> values = new ArrayList<>();
        c.moveToFirst();

        // Add character attributes to values arrayList
        values.add(c.getString(0));  // Name
        values.add(c.getString(1));  // Level
        values.add(c.getString(2));  // Class
        values.add(c.getString(3));  // Archetype
        values.add(c.getString(4));  // Race
        values.add(c.getString(5));  // Sub-race
        values.add(c.getString(6));  // Background
        values.add(c.getString(7));  // Score
        values.add(c.getString(8));  // Modifier
        values.add(c.getString(9));  // Skills
        values.add(c.getString(10)); // Languages
        values.add(c.getString(11)); // Tools
        values.add(c.getString(12)); // otherWeapons
        values.add(c.getString(13)); // HP
        values.add(c.getString(14)); // AC
        values.add(c.getString(15)); // Current Armor
        values.add(c.getString(16)); // Current Weapon
        values.add(c.getString(17)); // Saving Throws
        values.add(c.getString(18)); // Weapon and Armor prof
        values.add(c.getString(19)); // ID

        c.close();
        return values;
    }

}