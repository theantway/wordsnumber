package com.aconex.phonenumber.dict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;

/**
 * WordDictionary provides function to store and query word case-insensitively
 */
public class WordDictionary {
    /**
     * Use Trie tree to save all the entries
     */
    private final Trie entries = new Trie();

    /**
     * entry counts in this dictionary
     */
    private int count = 0;

    /**
     * init from inputstream which contains a word each line.
     * The word is normalized: only a-zA-Z characters left, e.g. I'm -> Im
     * After normalization, the empty word is ignored
     *
     * @param reader
     * @throws IOException
     */
    public WordDictionary initFromReader(Reader reader) throws IOException {
        try (BufferedReader dictReader = new BufferedReader(reader)) {
            while (true) {
                String line = dictReader.readLine();
                if (line == null) {
                    //end of stream
                    return this;
                }

                addWord(line);
            }
        }
    }

    /**
     * entries count in this dictionary
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * check if contains a word case-insensitively
     * @param word
     * @return
     */
    public boolean containsWord(String word) {
        return entries.contains(word);
    }

    /**
     * check if any word in dictionary starts with prefix case-insensitively
     * @param prefix
     * @return
     */
    public boolean hasWordStartsWith(String prefix) {
        return entries.hasWordStartsWith(prefix);
    }

    private void addWord(String word) {
        String normalizedWord = normalizeWord(word);

        //ignore invalid words
        if (normalizedWord.isEmpty()) {
            return;
        }

        count++;
        entries.add(normalizedWord);
    }

    /**
     * normalize by: change to uppercase, ignore characters out of range of A-Z
     * @param word
     * @return
     */
    private String normalizeWord(String word) {
        word = word.toUpperCase(Locale.ENGLISH);

        StringBuilder builder = new StringBuilder(word.length());
        for (char c : word.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                builder.append(c);
            }
        }

        return builder.toString();
    }
}
