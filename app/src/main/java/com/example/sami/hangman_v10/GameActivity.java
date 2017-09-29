package com.example.sami.hangman_v10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class GameActivity extends AppCompatActivity {

    private TextView viewCorrect;
    private TextView viewTaken;
    private ImageView imageHangman;
    private String secretWord;

    private Button buttonClicked;

    private AlertDialog.Builder dialogBuilder;

    //Accessor for Logic class
    private Logic logic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        allButtonHandler();

        dialogBuilder = new AlertDialog.Builder(this);
        viewCorrect = (TextView) findViewById(R.id.correctLetters);
        viewTaken = (TextView) findViewById(R.id.takenLetters);
        imageHangman = (ImageView) findViewById(R.id.hangmanView);

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
        else{
            this.secretWord = generateWord();
            logic = new Logic(secretWord);
        }

        updateInfo();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("secret", logic.getSecretWord());
        outState.putStringArrayList("dashedLines", logic.getDashedLines());
        outState.putStringArrayList("takenLetters", logic.getAllLetters());
        outState.putIntegerArrayList("buttonId", logic.getButtonId());
        outState.putInt("tries", logic.getTries());
        outState.putInt("errors", logic.getErrorsCounter());
        outState.putInt("correct", logic.getCorrectCounter());

        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        String secret = savedInstanceState.getString("secret");
        ArrayList<String> dash = savedInstanceState.getStringArrayList("dashedLines");
        ArrayList<String> taken = savedInstanceState.getStringArrayList("takenLetters");
        ArrayList<Integer> buttonId = savedInstanceState.getIntegerArrayList("buttonId");
        int tries = savedInstanceState.getInt("tries");
        int error = savedInstanceState.getInt("errors");
        int correct = savedInstanceState.getInt("correct");

        logic = new Logic(secret, dash, taken, buttonId, tries, error, correct);

        Iterator<Integer> itr = logic.getButtonId().iterator();
        while(itr.hasNext()){
            Button pressedButtons = (Button) findViewById(itr.next());
            pressedButtons.setEnabled(false);
        }

    }

    public void allButtonHandler(){

        /*
        for(int buttonId = 0; buttonId < 29; buttonId++){
            buttonId = getResources().getIdentifier("btn%c", "id", getActivity().getPackageName());
            buttonClickedHandler(buttonId);
        }
        */
        buttonClickedHandler(R.id.btna);
        buttonClickedHandler(R.id.btnb);
        buttonClickedHandler(R.id.btnc);
        buttonClickedHandler(R.id.btnd);
        buttonClickedHandler(R.id.btne);
        buttonClickedHandler(R.id.btnf);
        buttonClickedHandler(R.id.btng);
        buttonClickedHandler(R.id.btnh);
        buttonClickedHandler(R.id.btni);
        buttonClickedHandler(R.id.btnj);
        buttonClickedHandler(R.id.btnk);
        buttonClickedHandler(R.id.btnl);
        buttonClickedHandler(R.id.btnm);
        buttonClickedHandler(R.id.btnn);
        buttonClickedHandler(R.id.btno);
        buttonClickedHandler(R.id.btnp);
        buttonClickedHandler(R.id.btnq);
        buttonClickedHandler(R.id.btnr);
        buttonClickedHandler(R.id.btns);
        buttonClickedHandler(R.id.btnt);
        buttonClickedHandler(R.id.btnu);
        buttonClickedHandler(R.id.btnv);
        buttonClickedHandler(R.id.btnw);
        buttonClickedHandler(R.id.btnx);
        buttonClickedHandler(R.id.btny);
        buttonClickedHandler(R.id.btnz);
        buttonClickedHandler(R.id.btnæ);
        buttonClickedHandler(R.id.btnø);
        buttonClickedHandler(R.id.btnå);
    }

    public String generateWord() {
        Resources res = getResources();
        TypedArray wordlist = res.obtainTypedArray(R.array.wordlist);

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length());

        return wordlist.getString(randomIndex);
    }

    public void buttonClickedHandler(final int buttonId) {
        buttonClicked  = (Button) findViewById(buttonId);
        buttonClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String letter = ((Button)view).getText().toString();
                view.setEnabled(false);
                logic.checkWord(letter, buttonId);

                if(logic.getErrorsCounter() > 5){
                    dialogBox(R.string.lost, R.string.yes, R.string.no);
                    statsHandler(false);
                }
                else if(logic.getCorrectCounter() == logic.getSecretNumb()){
                    dialogBox(R.string.won, R.string.yes, R.string.no);
                    statsHandler(true);
                }
                updateInfo();
            }
        });
    }

    public void updateInfo() {
        viewCorrect.setText(logic.listDashedLines());
        viewTaken.setText(logic.listAllLetters());
        imageHangman.setImageResource(logic.getResIdImg());
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

    private void statsHandler(boolean vant){
        if(vant){
            SharedPreferences topScoreList = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = topScoreList.edit();
            editor.putInt("win", topScoreList.getInt("win", 0)+1);
            editor.apply();
        }
        else{
            SharedPreferences topScoreList = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = topScoreList.edit();
            editor.putInt("loss", topScoreList.getInt("loss", 0)+1);
            editor.apply();
        }
    }

}
