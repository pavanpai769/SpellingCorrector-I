package com.spellcorrector.model;

import java.io.IOException;
import java.util.List;

public interface ISpellCorrector {
    void addDictionary(String dictionaryPath) throws IOException;

    List<String> suggestSimilarWords(String word);
}
