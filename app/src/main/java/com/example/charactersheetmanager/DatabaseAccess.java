package com.example.charactersheetmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    // Constructor
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    // Returns singleton instance of DatabaseAccess
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    // Open database connection
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    // Close database connection
    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public String[] getList (String table) {
        Cursor c = db.rawQuery("SELECT DISTINCT name FROM " + table + " ORDER BY name ASC", null);
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

    public String[] getSubRace (String race) {
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

    public List getClassInfo (String Class) {
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

    public String[] getClassAbilities (String Class, int level) {
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

    public String[] getClassDescriptions (String  Class, int level) {
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

    public String[] getArchetypeList(String Class) {
        Cursor c = db.rawQuery("SELECT archetype FROM Class WHERE name = '" + Class + "' ORDER BY archetype ASC", null);

        c.moveToFirst();
        String[] values = c.getString(0).replace(", ", ",").split(",");
        c.close();

        return values;
    }

    public List getRaceInfo (String Race, boolean isSubRace) {

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
        values.add(c.getString(9));  // Language #1
        values.add(c.getString(10)); // Language #2
        values.add(c.getString(11)); // Language #3
        values.add(c.getString(12)); // Resistances
        values.add(c.getString(13)); // Features

        c.close();
        return values;
    }

    public List getBackgroundInfo (String Background) {
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

}
