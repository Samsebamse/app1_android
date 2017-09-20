package com.example.sami.hangman_v10;

import android.widget.Toast;

/**
 * Created by Sami on 20-Sep-17.
 */

public class Logic {

    private final String secretWord;
    private StringBuilder dashedLine;
    private StringBuilder takenLetters;
    private char[] takenLettersStored;
    private int errorCounter = 0;
    private Toast popupMessage;

    //Display first image of hangman and storing ID to all of them in an int array
    private int [] resID = {R.mipmap.hangman1, R.mipmap.hangman2, R.mipmap.hangman3,
    R.mipmap.hangman4, R.mipmap.hangman5, R.mipmap.hangman6,
    R.mipmap.hangman7};

    public Logic(String secretWord){
        this.secretWord = secretWord;

        dashedLine = new StringBuilder();
        takenLetters = new StringBuilder();

        for (int i = 0; i < secretWord.length(); i++){
            dashedLine.append("_");
            dashedLine.append(" ");
        }
    }

    public void checkWord(char userInput){

        takenLetters.append(userInput + "\n");

        for(int j = 0; j < secretWord.length(); j++) {
            if (userInput == secretWord.charAt(j)) {
                dashedLine.setCharAt(j * 2, secretWord.charAt(j));
                return;
            }



        }

        errorCounter++;
    }

    public StringBuilder getDashedLines(){
        return dashedLine;
    }

    public StringBuilder getTakenLetters(){
        return takenLetters;
    }

    public int getResID(){
        return resID[errorCounter];
    }

}
