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
        SharedPreferences topScoreList = PreferenceManager.getDefaultSharedPreferences(this);
        String str1 = topScoreList.getString("rank1", null);
        String str2 = topScoreList.getString("rank2", null);
        viewRank.append(str1);
        viewRank.append(str2);



    }
}
