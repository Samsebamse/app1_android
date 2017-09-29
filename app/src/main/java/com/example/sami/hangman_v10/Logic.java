package com.example.sami.hangman_v10;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 * Created by Sami on 20-Sep-17.
 */

public class Logic {

    private final String secretWord;

    private ArrayList<String> dashedLine;
    private ArrayList<String> allLetters;
    private ArrayList<Integer> buttonId;

    private int tries = 0;
    private int errorCounter = 0;
    private int correctCounter = 0;

    private int [] resId = {R.mipmap.hangman1, R.mipmap.hangman2, R.mipmap.hangman3,
    R.mipmap.hangman4, R.mipmap.hangman5, R.mipmap.hangman6,
    R.mipmap.hangman7};

    private int [] topScore;

    public Logic(String secretWord){
        this.secretWord = secretWord;

        dashedLine = new ArrayList<>();
        allLetters = new ArrayList<>();
        buttonId = new ArrayList<Integer>();

        for (int i = 0; i < secretWord.length(); i++){
            dashedLine.add("_");
            dashedLine.add(" ");
        }
    }

    public Logic(String secret, ArrayList<String> dash, ArrayList<String> taken, ArrayList<Integer> buttonId, int tries, int error, int correct){
        this.secretWord = secret;
        this.dashedLine = new ArrayList<String>(dash);
        this.allLetters = new ArrayList<String>(taken);
        this.buttonId = new ArrayList<Integer>(buttonId);
        this.tries = tries;
        this.errorCounter = error;
        this.correctCounter = correct;
    }

    public void checkWord(String userInput, int btnId){

        boolean correctGuess = false;
        buttonId.add(btnId);
        tries++;

        for(int j = 0; j < secretWord.length(); j++){
            if(userInput.charAt(0) == secretWord.charAt(j)){
                dashedLine.set(j*2, String.valueOf(secretWord.charAt(j)));
                correctGuess = true;
                correctCounter++;
            }
        }
        if(!correctGuess) {
            allLetters.add(userInput);
            errorCounter++;
        }
    }

    public ArrayList<String> getDashedLines(){
        return dashedLine;
    }
    public ArrayList<String> getAllLetters(){
        return allLetters;
    }
    public ArrayList<Integer> getButtonId(){
        return buttonId;
    }
    public int getTries(){
        return tries;
    }
    public int getErrorsCounter(){
        return errorCounter;
    }
    public int getCorrectCounter(){
        return correctCounter;
    }
    public int getResIdImg(){
        return resId[errorCounter];
    }

    public int getSecretNumb(){
        return secretWord.length();
    }
    public String getSecretWord(){
        return secretWord;
    }


    public StringBuilder listAllLetters(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < allLetters.size(); i++){
            sb.append(allLetters.get(i));
            sb.append(" ");
        }
        if(allLetters.size() == 10 || allLetters.size() == 20 || allLetters.size() == 30){
            sb.append("\n");
        }
        return sb;
    }

    public StringBuilder listDashedLines(){
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < dashedLine.size(); j++){
            sb.append(dashedLine.get(j));
        }
        return sb;
    }

}
