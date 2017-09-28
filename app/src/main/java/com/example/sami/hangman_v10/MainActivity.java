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
    private Button flagbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get saved language value
        loadLanguage();

        // change all language texts
        checkLanguage();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allButtonHandler(R.id.buttonStart);
        allButtonHandler(R.id.buttonLanguage);
        allButtonHandler(R.id.buttonRules);
        allButtonHandler(R.id.buttonTopscore);
    }


    public void allButtonHandler(final int buttonId){
        final Button clickedButton = (Button) findViewById(buttonId);
        clickedButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch(buttonId){

                    case R.id.buttonStart:
                    Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(gameActivity);
                    finish();
                    break;

                    case R.id.buttonLanguage:
                    language = !language;
                    checkLanguage();
                    saveLanguage();
                    reloadActivity();
                    break;

                    case R.id.buttonRules:
                        break;

                    case R.id.buttonTopscore:
                        Intent RankActivity = new Intent(MainActivity.this, RankActivity.class);
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

    private void reloadActivity(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private void saveLanguage(){
        SharedPreferences.Editor editor = getSharedPreferences("languageSharedPref", MODE_PRIVATE).edit();
        editor.putBoolean("language", language);
        editor.apply();
    }

    private void loadLanguage(){
        SharedPreferences prefs = getSharedPreferences("languageSharedPref", MODE_PRIVATE);
        language = prefs.getBoolean("language", false);
    }
    public void checkLanguage(){
        if(language){
            setLocale("no");
        }
        else{
            setLocale("en");
        }
    }
}
