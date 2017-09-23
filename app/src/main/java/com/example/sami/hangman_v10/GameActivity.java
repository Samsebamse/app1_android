package com.example.sami.hangman_v10;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView viewCorrect;
    private TextView viewTaken;
    private EditText input;
    private Button buttonOk;
    private ImageView imageHangman;
    private String secretWord;

    private char c;

    Toast popupMesssage;

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
            int errors = savedInstanceState.getInt("errors");
            logic = new Logic(secret, dash, taken, tries, errors);
        }
        else{
            this.secretWord = generateWord();
            logic = new Logic(secretWord);
        }

        setContentView(R.layout.activity_game);
        viewCorrect = (TextView) findViewById(R.id.correctLetters);
        viewTaken = (TextView) findViewById(R.id.takenLetters);
        input = (EditText) findViewById(R.id.inputUser);
        buttonOk = (Button) findViewById(R.id.confirmButton);
        imageHangman = (ImageView) findViewById(R.id.hangmanView);

        updateInfo();
        buttonHandler();
        keyboardHandler();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("secret", secretWord);
        outState.putStringArrayList("dashedLines", logic.getDashedLines());
        outState.putStringArrayList("takenLetters", logic.getAllLetters());
        outState.putInt("tries", logic.getTries());
        outState.putInt("errors", logic.getErrors());

        super.onSaveInstanceState(outState);
    }

    public String generateWord() {
        Resources res = getResources();
        TypedArray wordlist = res.obtainTypedArray(R.array.wordlist);

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length());

        return wordlist.getString(randomIndex);
    }

    public void keyboardHandler() {
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
            }
        });
    }

    public void buttonHandler() {

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input.getText().length() > 0) {
                    c = input.getText().charAt(0);
                    logic.checkWord(c);
                        if(logic.sameLetter){
                            popupMessage(R.string.letter_exist);
                        }
                    updateInfo();
                    input.setText("");
                }
                else{
                    popupMessage(R.string.enter_letter);
                }
            }
        });
    }

    public void updateInfo() {
        viewCorrect.setText(logic.listDashedLines());
        viewTaken.setText(logic.listAllLetters());
        imageHangman.setImageResource(logic.getResID());
    }

    public void popupMessage(int resId) {
        popupMesssage = Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_LONG);
        popupMesssage.show();

    }
}
