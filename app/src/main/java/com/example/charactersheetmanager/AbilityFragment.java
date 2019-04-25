package com.example.charactersheetmanager;

public class AbilityFragment {

    private String mTitle;
    private String mDescription;
    private int mLevel;

    public AbilityFragment(String title, String description, int level) {
        mTitle = title;
        mDescription = description;
        mLevel = level;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getLevel() {
        return mLevel;
    }

}
