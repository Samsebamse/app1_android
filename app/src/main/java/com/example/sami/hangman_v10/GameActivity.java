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
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Counter for wrong guesses
    private int counter = 0;

    //Display first image of hangman and storing ID to all of them in an int array
    private int [] resID = {R.mipmap.hangman1, R.mipmap.hangman2, R.mipmap.hangman3,
                    R.mipmap.hangman4, R.mipmap.hangman5, R.mipmap.hangman6,
                    R.mipmap.hangman7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Creating a reference to widgets
        final TextView display = (TextView) findViewById(R.id.wordCount);
        final EditText input = (EditText) findViewById(R.id.inputUser);
        final Button buttonOk = (Button) findViewById(R.id.confirmButton);
        final ImageView imageHangman = (ImageView) findViewById(R.id.hangmanView);

        imageHangman.setImageResource(resID[counter]);

        //Retrieve words saved in arrays.xml
        Resources res = getResources();
        TypedArray wordlist = res.obtainTypedArray(R.array.wordlist);

        //Random generator
        Random random = new Random();

        //Picks up a number from 0 to total amount of words in arrays.xml
        int index = random.nextInt(wordlist.length());

        //Storing the random word to a string
        final String secretWord = wordlist.getString(index);

        //Counting amount of letters whihin that random word
        final int length = secretWord.length();

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++){
            sb.append("_");
            sb.append(" ");
        }

        //Displaying dashes for the secret word
        display.setText(sb.toString());


        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                char chr1 = input.getText().charAt(0);
                char [] chr2 = secretWord.toCharArray();

                for(int j = 0; j < length; j++){
                    if(chr1 == chr2[j]){
                        sb.setCharAt(j*2, chr2[j]);
                        display.setText(sb.toString());
                    }
                    else{
                        counter++;
                        imageHangman.setImageResource(resID[counter]);
                    }
                }
                input.setText("");
            }
        });

    }
}
