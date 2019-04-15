package com.example.charactersheetmanager;

public class CharacterFragment {
    private String CharacterName, Level, Race, CharacterClass, Background;

    public CharacterFragment(String characterName, String level, String race, String characterClass, String background) {
        CharacterName = characterName;
        Level = level;
        Race = race;
        CharacterClass = characterClass;
        Background = background;
    }

    public String getCharacterName() {
        return CharacterName;
    }

    public String getLevel() {
        return Level;
    }

    public String getRace() {
        return Race;
    }

    public String getCharacterClass() {
        return CharacterClass;
    }

    public String getBackground() {
        return Background;
    }
}
