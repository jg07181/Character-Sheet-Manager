package com.example.charactersheetmanager;

public class AbilityFragment {

    private String mTitle;
    private String mDescription;
    private  boolean mExpanded;

    public AbilityFragment(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean ismExpanded() {
        return mExpanded;
    }

    public void setmExpanded(boolean mExpanded) {
        this.mExpanded = mExpanded;
    }
}
