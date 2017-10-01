package com.example.sami.hangman_v10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity{

    private PieChart pieChart;
    private SharedPreferences topScoreList;
    private SharedPreferences.Editor editor;
    private Button resetButton;
    private ImageButton backToMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        resetButtonHandler(R.id.resetStatsButton);
        mainMenuHandler(R.id.backToMenuButton);
        createPieChart();
    }
    public void mainMenuHandler(int buttonId){
        backToMainMenuButton = (ImageButton) findViewById(buttonId);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenu);
                finish();
            }
        });

    }
    private void resetButtonHandler(int buttonId) {
        resetButton = (Button) findViewById(buttonId);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = topScoreList.edit();
                editor.remove("win");
                editor.remove("loss");
                editor.apply();
                pieChart.clear();
            }
        });
    }

    public void createPieChart(){
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);
        topScoreList = PreferenceManager.getDefaultSharedPreferences(this);

        int [] values = {topScoreList.getInt("win", 0), topScoreList.getInt("loss", 0)};
        String [] xLabel = {getString(R.string.show_win), getString(R.string.show_loss)};

        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0; i < xLabel.length; i++){
            pieEntries.add(new PieEntry(values[i], xLabel[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, null);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);
        dataSet.setColors((new int[] {R.color.colorRed, R.color.colorBlueGray}), this);
        PieData data = new PieData(dataSet);
        Legend l = pieChart.getLegend();
        l.setTextSize(20);
        l.setFormSize(30);
        data.setValueTextSize(20);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
