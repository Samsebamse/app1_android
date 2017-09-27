package com.example.sami.hangman_v10;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class RankActivity extends AppCompatActivity {

    private PieChart pieChart;
    private Button reset;
    SharedPreferences topScoreList;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        topScoreList = PreferenceManager.getDefaultSharedPreferences(this);
        int antallSeiere = topScoreList.getInt("vant", 0);
        int antallTap = topScoreList.getInt("tapt", 0);
        int antallSpill = antallSeiere + antallTap;

        List<PieEntry> entries = new ArrayList<>();
        int color1 = R.color.colorAccent;
        int color2 = R.color.colorPrimary;
        String title = getResources().getString(R.string.stats);

        PieDataSet set = new PieDataSet(entries, title);
        set.setColors(new int[] {color1, color2}, this);
        entries.add(new PieEntry(antallSeiere, color1));
        entries.add(new PieEntry(antallTap, color2));
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate();

        reset = (Button) findViewById(R.id.resetStatsButton);

        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editor = topScoreList.edit();
                editor.remove("vant");
                editor.remove("tapt");
                editor.apply();
                pieChart.clear();
            }
        });
    }
}
