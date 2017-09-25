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

    boolean sameLetter = false;
    private int tries = 0;
    private int errorCounter = 0;

    private int [] resID = {R.mipmap.hangman1, R.mipmap.hangman2, R.mipmap.hangman3,
    R.mipmap.hangman4, R.mipmap.hangman5, R.mipmap.hangman6,
    R.mipmap.hangman7};

    public Logic(String secretWord){
        this.secretWord = secretWord;

        dashedLine = new ArrayList<>();
        allLetters = new ArrayList<>();

        for (int i = 0; i < secretWord.length(); i++){
            dashedLine.add("_");
            dashedLine.add(" ");
        }
    }

    public Logic(String secret, ArrayList<String> dash, ArrayList<String> taken, int tries, int errors){
        this.secretWord = secret;
        this.dashedLine = new ArrayList<String>(dash);
        this.allLetters = new ArrayList<String>(taken);
        this.tries = tries;
        this.errorCounter = errors;
    }

    public void checkWord(char userInput){

        boolean correctGuess = false;
        sameLetter = false;

        if(!allLetters.contains(String.valueOf(userInput))){
            allLetters.add(String.valueOf(userInput));
            tries++;
        }
        else{
            sameLetter = true;
        }
        for(int j = 0; j < secretWord.length(); j++){
            if(userInput == secretWord.charAt(j)){
                dashedLine.set(j*2, String.valueOf(secretWord.charAt(j)));
                correctGuess = true;
            }
        }
        if(!correctGuess && !sameLetter) {
            errorCounter++;
        }
    }

    public ArrayList<String> getDashedLines(){
        return dashedLine;
    }
    public ArrayList<String> getAllLetters(){
        return allLetters;
    }
    public int getTries(){
        return tries;
    }
    public int getErrors(){
        return errorCounter;
    }
    public int getResID(){
        return resID[errorCounter];
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
