package com.aconex.phonenumber.wordnumber;

import com.aconex.phonenumber.dict.WordDictionary;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.util.Collections.singletonList;

/**
 * split string into words defined in dictionary
 */
public class WordsSplitter {
    private final WordDictionary dictionary;

    public WordsSplitter(WordDictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Split a str into words defined in dictionary. returns all the possible candidates, e.g.
     *
     * There are two candidates to split MYSELF, ["MY", "SELF"] and ["MYSELF"]
     *
     * @param str string to split to words
     * @return List of {@link WordsCandidate}, return empty list if not possible to split into words in dictionary
     */
    public List<WordsCandidate> splitWords(String str) {
        return splitWords(str, 0);
    }

    /**
     * from given position, split a str into words defined in dictionary
     * @param str string to split to words
     * @param pos split from this position
     * @return List of {@link WordsCandidate}, empty list if not possible to split into words in dictionary
     */
    private List<WordsCandidate> splitWords(String str, int pos) {
        List<WordsCandidate> candidates = new ArrayList<>();
        if (pos == str.length()) {
            return singletonList(new WordsCandidate());
        }

        for (int i = pos; i < str.length(); i++) {
            char c = str.charAt(i);

            if (isDigit(c)) {
                if (i == pos) {
                    String word = String.valueOf(c);
                    candidates.addAll(addWords(word, splitWords(str, i + 1)));
                }

                return candidates;
            } else {
                String word = str.substring(pos, i + 1);
                if (dictionary.containsWord(word)) {
                    candidates.addAll(addWords(word, splitWords(str, i + 1)));
                }
            }
        }

        return candidates;
    }

    private List<WordsCandidate> addWords(String word, List<WordsCandidate> restWordList) {
        for (WordsCandidate restWordsCandidate : restWordList) {
            restWordsCandidate.addFront(word);
        }

        return restWordList;
    }
}
