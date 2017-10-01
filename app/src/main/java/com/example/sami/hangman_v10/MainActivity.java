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
import android.widget.ImageButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private boolean language = false;
    private Button clickedButton;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtonHandler(R.id.buttonLanguage);
        // get saved language value
        loadLanguage();

        // change all language texts
        checkLanguage();

        allButtonHandler(R.id.buttonStart);
        allButtonHandler(R.id.buttonRules);
        allButtonHandler(R.id.buttonTopscore);
    }

    public void imageButtonHandler(int buttonId){
        imageButton = (ImageButton) findViewById(buttonId);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                language = !language;
                saveLanguage();
                checkLanguage();
                reloadActivity();
            }
        });
    }
    public void allButtonHandler(final int buttonId){
        clickedButton = (Button) findViewById(buttonId);

        clickedButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch(buttonId){

                    case R.id.buttonStart:
                    Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(gameActivity);
                    finish();
                    break;

                    case R.id.buttonRules:
                        Intent gameRules = new Intent(getApplicationContext(), GameRulesActivity.class);
                        startActivity(gameRules);
                        finish();
                        break;

                    case R.id.buttonTopscore:
                        Intent RankActivity = new Intent(getApplicationContext(), RankActivity.class);
                        startActivity(RankActivity);
                        finish();
                        break;

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

    public void reloadActivity(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    public void saveLanguage(){
        SharedPreferences.Editor editor = getSharedPreferences("languageSharedPref", MODE_PRIVATE).edit();
        editor.putBoolean("language", language);
        editor.apply();
    }

    public void loadLanguage(){
        SharedPreferences prefs = getSharedPreferences("languageSharedPref", MODE_PRIVATE);
        language = prefs.getBoolean("language", false);
    }
    public void checkLanguage(){
        if(language){
            setLocale("no");
            imageButton.setBackgroundResource(R.mipmap.noflag);
        }
        else{
            setLocale("en");
            imageButton.setBackgroundResource(R.mipmap.usflag);
        }
    }
}
