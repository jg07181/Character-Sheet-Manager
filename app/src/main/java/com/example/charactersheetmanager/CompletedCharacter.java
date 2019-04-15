package com.example.charactersheetmanager;

public class CompletedCharacter {

    private String mName, mClass, mArchetype, mRace, mSubRace, mBackground;
    private int mLevel;
    private int[] mScore, mModifier;

    public CompletedCharacter(String Name, String Class, String Archetype, String Race, String subRace, String Background, int Level, int[] Score, int[] Modifier) {
        mName = Name;
        mClass = Class;
        mArchetype = Archetype;
        mRace = Race;
        mSubRace = subRace;
        mBackground = Background;
        mLevel = Level;
        mScore = Score;
        mModifier = Modifier;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getClassName() {
        return mClass;
    }

    public void setClassName(String mClass) {
        this.mClass = mClass;
    }

    public String getArchetype() {
        return mArchetype;
    }

    public void setArchetype(String mArchetype) {
        this.mArchetype = mArchetype;
    }

    public String getRace() {
        return mRace;
    }

    public void setRace(String mRace) {
        this.mRace = mRace;
    }

    public String getSubRace() {
        return mSubRace;
    }

    public void setSubRace(String mSubRace) {
        this.mSubRace = mSubRace;
    }

    public String getBackground() {
        return mBackground;
    }

    public void setBackground(String mBackground) {
        this.mBackground = mBackground;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public int[] getScore() {
        return mScore;
    }

    public void setScore(int[] mScore) {
        this.mScore = mScore;
    }

    public int[] getModifier() {
        return mModifier;
    }

    public void setModifier(int[] mModifier) {
        this.mModifier = mModifier;
    }

}
