package com.example.sami.hangman_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GameRulesActivity extends AppCompatActivity {

    ImageButton backToMainManuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);

        returnToMainMenu(R.id.backToMenuButton);

    }
    public void returnToMainMenu(int buttonId){
        backToMainManuButton = (ImageButton) findViewById(buttonId);
        backToMainManuButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenu);
                finish();
            }
        });
    }
}
