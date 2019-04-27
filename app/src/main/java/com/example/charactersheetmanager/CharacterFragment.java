package com.example.charactersheetmanager;

public class CharacterFragment {
    private String CharacterName, Level, Race, CharacterClass, Background;
    private String ID;

    CharacterFragment(String characterName, String level, String race, String characterClass, String background, String id) {
        CharacterName = characterName;
        Level = level;
        Race = race;
        CharacterClass = characterClass;
        Background = background;
        ID = id;
    }

    String getCharacterName() {
        return CharacterName;
    }

    public String getLevel() {
        return Level;
    }

    public String getRace() {
        return Race;
    }

    String getCharacterClass() {
        return CharacterClass;
    }

    public String getBackground() {
        return Background;
    }

    public String getID() {return ID;}
}
