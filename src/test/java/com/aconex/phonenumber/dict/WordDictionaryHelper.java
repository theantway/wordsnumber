package com.aconex.phonenumber.dict;

import java.io.*;

import static java.lang.String.join;

public class WordDictionaryHelper {

    public static Reader dictReader(String... entries) {
        return new StringReader(join("\n", entries));
    }

    public static WordDictionary buildDict(String... entries) {
        try {
            return new WordDictionary().initFromReader(dictReader(entries));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
