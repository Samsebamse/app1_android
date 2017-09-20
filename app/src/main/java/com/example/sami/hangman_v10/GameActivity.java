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

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView viewCorrect;
    private TextView viewTaken;
    private EditText input;
    private Button buttonOk;
    private ImageView imageHangman;

    //Accessor for Logic class
    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        viewCorrect = (TextView) findViewById(R.id.correctLetters);
        input = (EditText) findViewById(R.id.inputUser);
        buttonOk = (Button) findViewById(R.id.confirmButton);
        imageHangman = (ImageView) findViewById(R.id.hangmanView);

        logic = new Logic(generateWord());
        updateInfo();
        buttonHandler();
    }

    public String generateWord(){
        Resources res = getResources();
        TypedArray wordlist = res.obtainTypedArray(R.array.wordlist);

        Random random = new Random();
        int randomIndex = random.nextInt(wordlist.length());

        return wordlist.getString(randomIndex);
    }

    public void buttonHandler() {

        buttonOk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                logic.checkWord(input.getText().charAt(0));
                updateInfo();
                input.setText("");
            }
        });
    }

    public void updateInfo(){
        viewCorrect.setText(logic.getDashedLines());
        viewTaken.append(logic.getTakenLetters() + "\n");
        imageHangman.setImageResource(logic.getResID());
    }
}
