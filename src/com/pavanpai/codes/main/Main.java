package com.pavanpai.codes.main;

import com.pavanpai.codes.model.ISpellCorrector;
import com.pavanpai.codes.model.SpellCorrector;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        ISpellCorrector spellCorrector = new SpellCorrector();
        spellCorrector.addDictionary("src/com/pavanpai/codes/resources/dictionary.txt");
        List<String> similarWords = spellCorrector.suggestSimilarWords("mudassir");


        if(!similarWords.isEmpty()){

            if(similarWords.size()==1){
                System.out.println("suggested word is: "+similarWords.getFirst());
            }else{
                System.out.println("suggested words are: "+similarWords);
            }

        }else{
            System.out.println("no similar word found");
        }
        
    }
}