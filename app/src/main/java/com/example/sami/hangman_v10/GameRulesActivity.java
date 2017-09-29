package com.example.sami.hangman_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameRulesActivity extends AppCompatActivity implements TopFragment.TopSection{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);
    }
}
