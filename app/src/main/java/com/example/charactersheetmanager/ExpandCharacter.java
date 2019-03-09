package com.example.charactersheetmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandCharacter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_character);

        ArrayList<String> test1 = getIntent().getStringArrayListExtra("backgroundInfo");
        ArrayList<String> test2 = getIntent().getStringArrayListExtra("raceInfo");

        TextView testBackground = findViewById(R.id.testBackground);
        TextView testRace = findViewById(R.id.testRace);

        testBackground.setText(TextUtils.join(", ", test1));
        testRace.setText(TextUtils.join(",", test2));
    }
}
