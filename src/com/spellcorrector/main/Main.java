package com.spellcorrector.main;

import com.spellcorrector.model.ISpellCorrector;
import com.spellcorrector.model.SpellCorrector;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        ISpellCorrector spellCorrector = new SpellCorrector();
        spellCorrector.addDictionary("src/com/spellcorrector/resources/dictionary.txt");

        Scanner obj = new Scanner(System.in);
        System.out.print("Enter the word: ");
        String inputWord = obj.next();

        List<String> similarWords = spellCorrector.suggestSimilarWords(inputWord);


        if (!similarWords.isEmpty()) {

            if (similarWords.size() == 1) {
                System.out.println("suggested word is: " + similarWords.getFirst());
            } else {
                System.out.println("suggested words are: " + similarWords);
            }

        } else {
            System.out.println("no similar word found");
        }

    }
}