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
     * Split a str into words defined in dictionary. returns all the possible words number, e.g.
     * <p>
     * There are two words number to split MYSELF, ["MY", "SELF"] and ["MYSELF"]
     *
     * @param str string to split to words
     * @return List of {@link WordsNumber}, return empty list if not possible to split into words in dictionary
     */
    public List<WordsNumber> splitStringToWords(String str) {
        return splitStringToWords(str, 0);
    }

    /**
     * check if it is possible to split string into words from dictionary
     * @param str
     * @return true if able to split, else false
     */
    public boolean canSplitStringToWords(String str) {
        return canSplitStringToWords(str, 0);
    }

    /**
     * from given position, split a str into words defined in dictionary
     *
     * @param str string to split to words
     * @param pos split from this position
     * @return List of {@link WordsNumber}, empty list if not possible to split into words in dictionary
     */
    private List<WordsNumber> splitStringToWords(String str, int pos) {
        List<WordsNumber> wordsNumbers = new ArrayList<>();
        if (pos == str.length()) {
            return singletonList(new WordsNumber());
        }

        for (int i = pos; i < str.length(); i++) {
            char c = str.charAt(i);

            if (isDigit(c)) {
                if (i == pos) {
                    String word = String.valueOf(c);
                    wordsNumbers.addAll(addWord(word, splitStringToWords(str, i + 1)));
                }

                return wordsNumbers;
            } else {
                String word = str.substring(pos, i + 1);
                if (dictionary.containsWord(word)) {
                    wordsNumbers.addAll(addWord(word, splitStringToWords(str, i + 1)));
                }
            }
        }

        return wordsNumbers;
    }

    /**
     * check if str can be split into words from pos
     * @param str
     * @param pos
     * @return
     */
    private boolean canSplitStringToWords(String str, int pos) {
        if (pos == str.length()) {
            return true;
        }

        for (int i = pos; i < str.length(); i++) {
            char c = str.charAt(i);

            if (isDigit(c)) {
                if (i == pos) {
                    return canSplitStringToWords(str, i + 1);
                }
            } else {
                String word = str.substring(pos, i + 1);
                if (dictionary.containsWord(word)) {
                    if (canSplitStringToWords(str, i + 1)){
                        return true;
                    }
                } else if (!dictionary.hasWordStartsWith(word)) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<WordsNumber> addWord(String word, List<WordsNumber> restWordList) {
        for (WordsNumber restWordsNumber : restWordList) {
            restWordsNumber.addWordToFront(word);
        }

        return restWordList;
    }
}
