package com.pavanpai.codes.model;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    Map<String,Integer> wordFrequencyMap = new HashMap<>();

    @Override
    public void addDictionary(String dictionaryPath) throws IOException {
        File dictionary = new File(dictionaryPath);

        try(Scanner sc = new Scanner(dictionary)){
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(!line.contains(" ")){
                    int wordFreq = wordFrequencyMap.getOrDefault(line,0);
                    wordFrequencyMap.put(line,wordFreq+1);
                }else{
                    String[] words = line.split("[ ,]+");
                    for(String word:words){
                        int wordFreq = wordFrequencyMap.getOrDefault(word,0);
                        wordFrequencyMap.put(word,wordFreq);
                    }
                }
            }
        }catch(IOException e){
            throw new IOException("Failed to read from the file at: "+dictionaryPath);
        }

    }

    @Override
    public List<String> suggestSimilarWords(String word) {
        if(word==null) return new ArrayList<>();

        if(wordFrequencyMap.containsKey(word)) return new ArrayList<>(List.of(word));

        word = word.toLowerCase();

        TreeMap<Integer, TreeMap<Integer,TreeSet<String>>> minDistSimilarWordMap = new TreeMap<>();



        for(Map.Entry<String,Integer> dictionartEntry : wordFrequencyMap.entrySet()) {

            String currentWord = dictionartEntry.getKey();
            int currentWordFrequency = dictionartEntry.getValue();

            int minEditDistance = getEditDistance(word,currentWord.toLowerCase());

            TreeMap<Integer, TreeSet<String>> similarWordMap;

            if (minDistSimilarWordMap.containsKey(minEditDistance)) {

                similarWordMap = minDistSimilarWordMap.get(minEditDistance);

            } else {

                similarWordMap = new TreeMap<>();
            }


            TreeSet<String> similarWordSet;

            if(similarWordMap.containsKey(currentWordFrequency)){

                similarWordSet = similarWordMap.get(currentWordFrequency);

            }else{

                similarWordSet= new TreeSet<>();
            }

            similarWordSet.add(currentWord);
            similarWordMap.put(currentWordFrequency,similarWordSet);
            minDistSimilarWordMap.put(minEditDistance,similarWordMap);
        }

        int minimumDistance = minDistSimilarWordMap.firstKey();

        TreeMap<Integer,TreeSet<String>> similarWordsMap = minDistSimilarWordMap.get(minimumDistance);

        // you can use firstEntry().getValue() method also.

        int maximumFrequency = similarWordsMap.lastKey();

        TreeSet<String> similarWordsSet = similarWordsMap.get(maximumFrequency);

        // you can use lastEntry().getValur() also.

        return new ArrayList<>(similarWordsSet);
    }

    @Override
    public int getEditDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()][word2.length()];
        for(int[] row:dp){
            Arrays.fill(row,-1);
        }
        return editDistance(0,0,word1,word2,dp);
    }

    @Override
    public int editDistance(int idx1, int idx2, String word1, String word2, int[][] dp) {

        if(word1.equals("")) return word2.length();

        if(word2.equals("")) return word1.length();

        if(idx1==word1.length()) return word2.length()-idx2;

        if(idx2==word2.length()) return word1.length()-idx1;

        if(idx1>=word1.length() || idx2>=word2.length()) return Integer.MAX_VALUE-90000;

        if(dp[idx1][idx2]!=-1) return dp[idx1][idx2];

        char c1 = word1.charAt(idx1);
        char c2 = word2.charAt(idx2);

        if(idx1==word1.length()-1 && idx2==word2.length()-1){
            dp[idx1][idx2] =  (c1==c2) ? 0 : 1;
            return dp[idx1][idx2];
        }

        if(c1==c2) return editDistance(idx1+1,idx2+1,word1,word2,dp);

        int insert = editDistance(idx1,idx2+1,word1,word2,dp)+1;
        int delete = editDistance(idx1+1,idx2,word1,word2,dp)+1;
        int update = editDistance(idx1+1,idx2+1,word1,word2,dp)+1;

        dp[idx1][idx2]=Math.min(insert,Math.min(delete,update));
        return dp[idx1][idx2];

    }

}
