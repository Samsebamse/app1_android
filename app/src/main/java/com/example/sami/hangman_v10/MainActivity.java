package com.example.sami.hangman_v10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean language = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get saved language value
        getSavedState();

        // change all language texts
        if(language){
            setLocale("no");
        }
        else{
            setLocale("en");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allButtonHandler(R.id.buttonStart);
        allButtonHandler(R.id.buttonLanguage);
        allButtonHandler(R.id.buttonRules);
        allButtonHandler(R.id.buttonTopscore);
    } //

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public void allButtonHandler(int buttonId){
        final Button clickedButton = (Button) findViewById(buttonId);

        clickedButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(clickedButton.getId() == R.id.buttonStart) {
                    Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(gameIntent);
                }
                else if(clickedButton.getId() == R.id.buttonLanguage){
                    System.out.println("Før: "+language);
                    language = !language;
                    System.out.println("Etter: "+language);

                    if(language){
                        setLocale("no");
                    }
                    else{
                        setLocale("en");
                    }

                    saveState();
                    reloadActivity();
                }
                else if(clickedButton.getId() == R.id.buttonRules){

                }
                else if(clickedButton.getId() == R.id.buttonTopscore){

                }
            }

        });
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        onConfigurationChanged(conf);
        res.updateConfiguration(conf, dm);
    }

    private void reloadActivity(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private void saveState(){
        SharedPreferences.Editor editor = getSharedPreferences("languageSharedPref", MODE_PRIVATE).edit();
        editor.putBoolean("language", language);
        editor.apply();
    }

    private void getSavedState(){
        SharedPreferences prefs = getSharedPreferences("languageSharedPref", MODE_PRIVATE);
        language = prefs.getBoolean("language", false);
    }
}
