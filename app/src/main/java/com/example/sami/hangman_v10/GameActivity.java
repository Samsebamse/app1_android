package com.example.sami.hangman_v10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static android.R.id.edit;

public class GameActivity extends AppCompatActivity {

    private TextView viewCorrect;
    private TextView viewTaken;
    private EditText input;
    private Button buttonOk;
    private ImageView imageHangman;
    private String secretWord;

    private char c;

    private Toast popupMesssage;
    private AlertDialog.Builder dialogBuilder;

    //Accessor for Logic class
    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            String secret = savedInstanceState.getString("secret");
            ArrayList<String> dash = savedInstanceState.getStringArrayList("dashedLines");
            ArrayList<String> taken = savedInstanceState.getStringArrayList("takenLetters");
            int tries = savedInstanceState.getInt("tries");
            int error = savedInstanceState.getInt("errors");
            int correct = savedInstanceState.getInt("correct");
            logic = new Logic(secret, dash, taken, tries, error, correct);
        }
        else{
            this.secretWord = generateWord();
            logic = new Logic(secretWord);
        }

        dialogBuilder = new AlertDialog.Builder(this);

        setContentView(R.layout.activity_game);
        viewCorrect = (TextView) findViewById(R.id.correctLetters);
        viewTaken = (TextView) findViewById(R.id.takenLetters);
        input = (EditText) findViewById(R.id.inputUser);
        buttonOk = (Button) findViewById(R.id.confirmButton);
        imageHangman = (ImageView) findViewById(R.id.hangmanView);

        topListHandler(1000);
        updateInfo();
        buttonHandler();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("secret", secretWord);
        outState.putStringArrayList("dashedLines", logic.getDashedLines());
        outState.putStringArrayList("takenLetters", logic.getAllLetters());
        outState.putInt("tries", logic.getTries());
        outState.putInt("errors", logic.getErrorsCounter());
        outState.putInt("correct", logic.getCorrectCounter());

        super.onSaveInstanceState(outState);
    }

    public String generateWord() {
        Resources res = getResources();
        TypedArray wordlist = res.obtainTypedArray(R.array.wordlist);

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length());

        return wordlist.getString(randomIndex);
    }

    public void buttonHandler() {

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input.getText().length() > 0) {
                    c = input.getText().charAt(0);
                    logic.checkWord(c);
                        if(logic.isSameLetter()){
                            popupMessage(R.string.letter_exist);
                        }
                    updateInfo();
                    input.setText("");
                }
                else{
                    popupMessage(R.string.enter_letter);
                }

                if(logic.getErrorsCounter() > 5){
                    dialogBox(R.string.lost, R.string.yes, R.string.no);
                }
                else if(logic.getCorrectCounter() == logic.getSecretNumb()){
                    dialogBox(R.string.won, R.string.yes, R.string.no);
                }

            }
        });
    }

    public void updateInfo() {
        viewCorrect.setText(logic.listDashedLines());
        viewTaken.setText(logic.listAllLetters());
        imageHangman.setImageResource(logic.getResId());
    }

    public void popupMessage(int resId) {
        popupMesssage = Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_LONG);
        popupMesssage.show();

    }
    public void dialogBox(int message, int yes, int no){
        dialogBuilder.setMessage(getString(message));
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton(
                getString(yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent playAgain = getIntent();
                        finish();
                        startActivity(playAgain);

                    }
                });

        dialogBuilder.setNegativeButton(
                getString(no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent mainMenu = new Intent(GameActivity.this, MainActivity.class);
                        finish();
                        startActivity(mainMenu);


                    }
                });

        AlertDialog alertbox = dialogBuilder.create();
        alertbox.show();
    }

    private void topListHandler(int topScore){
        SharedPreferences topScoreList = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = topScoreList.edit();
        editor.putStringSet("topscorelist", new TreeSet<String>());
        editor.apply();

    }

}
