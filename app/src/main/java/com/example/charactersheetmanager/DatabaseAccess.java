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

    //Constructor
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    //Returns singleton instance of DatabaseAccess
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    //Open database connection
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    //Close database connection
    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    //Read selected class from database
    public List getSelectedClass(String selectedClass) {

        //Create an arrayList to store all values from Class
        List<String> list = new ArrayList<>();

        //SQLite query
        Cursor c = db.rawQuery("SELECT * FROM Class WHERE name = '" + selectedClass + "'", null);
        c.moveToFirst();

        //Insert code here

        c.close();
        return list;
    }

    //Read selected race from database
    public List getSelectedRace(String selectedRace) {

        //Create an arrayList to store all values from Race
        List<String> values = new ArrayList<>();

        //SQLite query
        Cursor c = db.rawQuery("SELECT * FROM Race WHERE name = '" + selectedRace + "'", null);
        c.moveToFirst();

        //Add race attributes to values arrayList
        values.add(c.getString(0)); //Race name
        values.add(c.getString(1)); //Ability score +/-
        values.add(c.getString(2)); //Size
        values.add(c.getString(3)); //Dark-vision
        values.add(c.getString(4)); //Speed
        values.add(c.getString(5)); //Language #1
        values.add(c.getString(6)); //Language #2
        values.add(c.getString(7)); //Language #3

        c.close();
        return values;
    }

    //Read selected background from database
    public List getSelectedBackground(String selectedBackground) {

        //Create an arrayList to store all values from Background
        List<String> values = new ArrayList<>();

        //SQLite query
        Cursor c = db.rawQuery("SELECT * FROM Background WHERE name = '" + selectedBackground + "'", null);
        c.moveToFirst();

        //Add background attributes to values arrayList
        values.add(c.getString(0)); //Background name
        values.add(c.getString(1)); //Skill proficiency #1
        values.add(c.getString(2)); //Skill proficiency #2
        values.add(c.getString(3)); //Number of extra languages
        values.add(c.getString(4)); //Tool proficiency #1
        values.add(c.getString(5)); //Tool proficiency #2

        c.close();
        return values;
    }
}
