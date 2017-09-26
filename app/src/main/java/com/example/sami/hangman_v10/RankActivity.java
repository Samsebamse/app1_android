package com.example.sami.hangman_v10;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class RankActivity extends AppCompatActivity {

    TextView viewRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        viewRank = (TextView) findViewById(R.id.viewRank);
        SharedPreferences rankList = PreferenceManager.getDefaultSharedPreferences(this);
        viewRank.setText(rankList.getString("topScoreList", ""));



    }
}
