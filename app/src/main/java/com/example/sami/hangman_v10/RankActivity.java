package com.example.sami.hangman_v10;

import android.content.SharedPreferences;
import android.graphics.Color;
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

public class RankActivity extends AppCompatActivity {

    private PieChart pieChart;

    SharedPreferences topScoreList;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        createPieChart();
        handleResetButton(R.id.resetStatsButton);
    }

    private void handleResetButton(final int buttonId) {
        final Button resetButton = (Button) findViewById(buttonId);
        resetButton.setOnClickListener(new View.OnClickListener() {
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
        data.setValueTextSize(25);
        dataSet.setFormSize(30);
        dataSet.setValueTextSize(20);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
