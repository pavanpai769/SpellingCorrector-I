package com.pavanpai.codes.model;
import java.io.IOException;
import java.util.List;

public interface ISpellCorrector {
    void addDictionary(String dictionaryPath) throws IOException;
    List<String> suggestSimilarWords(String word);
    int getEditDistance(String word1,String word2);
    int editDistance(int idx1,int idx2,String word1,String word2,int[][] dp);
}
