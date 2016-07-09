package com.aconex.phonenumber.dict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * WordDictionary provides function to store and query word case-insensitively
 */
public class WordDictionary {
    private static final String EMPTY = "";

    private final Set<String> entries = new HashSet<>();

    /**
     * init from inputstream which contains a word each line.
     * The word is normalized: only a-zA-Z characters left, e.g. I'm -> Im
     * After normalization, the empty word is ignored
     *
     * @param inputStream
     * @throws IOException
     */
    public WordDictionary initFromStream(InputStream inputStream) throws IOException {
        Charset utf8 = Charset.forName("utf-8");
        try (BufferedReader dictReader = new BufferedReader(new InputStreamReader(inputStream, utf8))) {
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
        return entries.size();
    }

    /**
     * check if contains a word case-insensitively
     * @param word
     * @return
     */
    public boolean containsWord(String word) {
        return entries.contains(word.toUpperCase(Locale.ENGLISH));
    }

    private void addWord(String word) {
        String normalizedWord = normalizeWord(word);

        //ignore invalid words
        if (normalizedWord.isEmpty()) {
            return;
        }

        entries.add(normalizedWord);
    }

    /**
     * normalize by: change to uppercase, ignore characters out of range of A-Z
     * @param word
     * @return
     */
    private String normalizeWord(String word) {
        if (word == null) {
            return EMPTY;
        }

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
