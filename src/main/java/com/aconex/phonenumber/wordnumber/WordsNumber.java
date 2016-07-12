package com.aconex.phonenumber.wordnumber;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A WordsNumber represents a list of words to split the replacement
 */
public class WordsNumber {
    private final Deque<String> words = new ArrayDeque<>();

    /**
     * add a word to the front of the list
     * @param word
     * @return
     */
    public WordsNumber addFront(String word) {
        words.addFirst(word);

        return this;
    }

    /**
     * join words using a delimiter
     * @param joiner delimiter
     * @return string
     */
    public String join(String joiner) {
        return String.join(joiner, words);
    }
}
