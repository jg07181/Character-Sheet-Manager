package com.example.charactersheetmanager;

public class CompletedCharacter {

    private String mName, mClass, mArchetype, mRace, mSubRace, mBackground, mLanguage, mTool, mOtherWeapon, mArmor, mWeapon;
    private int mLevel, mHP, mAC;
    private int[] mScore, mModifier;
    private boolean[] mSkills;

    public CompletedCharacter(String Name, String Class, String Archetype, String Race, String subRace, String Background,
                              int Level, int[] Score, int[] Modifier, boolean[] Skills, String Language, String Tool, String OtherWeapon,
                              int HP, int AC, String Armor, String Weapon) {
        mName = Name;
        mClass = Class;
        mArchetype = Archetype;
        mRace = Race;
        mSubRace = subRace;
        mBackground = Background;
        mLevel = Level;
        mScore = Score;
        mModifier = Modifier;
        mSkills = Skills;
        mLanguage = Language;
        mTool = Tool;
        mOtherWeapon = OtherWeapon;
        mHP = HP;
        mAC = AC;
        mArmor = Armor;
        mWeapon = Weapon;
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

    public String getArchetype() {
        return mArchetype;
    }

    public String getRace() {
        return mRace;
    }

    public String getSubRace() {
        return mSubRace;
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

    public int[] getModifier() {
        return mModifier;
    }

    public boolean[] getSkills() {
        return mSkills;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getTool() {
        return mTool;
    }

    public String getOtherWeapon() {
        return mOtherWeapon;
    }

    public int getHP() {
        return mHP;
    }

    public int getAC() {
        return mAC;
    }

    public String getArmor() {
        return mArmor;
    }

    public String getWeapon() {
        return mWeapon;
    }

}