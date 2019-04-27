package com.example.charactersheetmanager;

public class AbilityFragment {

    private String mTitle;
    private String mDescription;
    private int mLevel;

    AbilityFragment(String title, String description, int level) {
        mTitle = title;
        mDescription = description;
        mLevel = level;
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mDescription;
    }

    public int getLevel() {
        return mLevel;
    }

}
