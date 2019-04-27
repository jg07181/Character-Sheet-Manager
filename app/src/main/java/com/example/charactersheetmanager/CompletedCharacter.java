package com.example.charactersheetmanager;

public class CompletedCharacter {

    private String mName, mClass, mArchetype, mRace, mSubRace, mBackground, mLanguage, mTool, mOtherWeapon, mArmor, mWeapon;
    private int mLevel, mHP, mAC;
    private int[] mScore, mModifier;
    private boolean[] mSkills, mSaves, mWeaponArmor;

    CompletedCharacter(String Name, String Class, String Archetype, String Race, String subRace, String Background,
                       int Level, int[] Score, int[] Modifier, boolean[] Skills, String Language, String Tool, String OtherWeapon,
                       int HP, int AC, String Armor, String Weapon, boolean[] Saves, boolean[] WeaponArmor) {
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
        mSaves = Saves;
        mWeaponArmor = WeaponArmor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    String getClassName() {
        return mClass;
    }

    String getArchetype() {
        return mArchetype;
    }

    public String getRace() {
        return mRace;
    }

    String getSubRace() {
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

    int[] getScore() {
        return mScore;
    }

    int[] getModifier() {
        return mModifier;
    }

    boolean[] getSkills() {
        return mSkills;
    }

    String getLanguage() {
        return mLanguage;
    }

    String getTool() {
        return mTool;
    }

    String getOtherWeapon() {
        return mOtherWeapon;
    }

    int getHP() {
        return mHP;
    }

    int getAC() {
        return mAC;
    }

    String getArmor() {
        return mArmor;
    }

    String getWeapon() {
        return mWeapon;
    }

    boolean[] getSaves() {
        return mSaves;
    }

    boolean[] getWeaponArmor() {
        return mWeaponArmor;
    }

}