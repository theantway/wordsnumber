package com.aconex.phonenumber.dict;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.join;

public class WordDictionaryHelper {

    public static InputStream dictStream(String... entries) {
        return new ByteArrayInputStream(join("\n", entries).getBytes());
    }

    public static WordDictionary buildDict(String... entries) {
        try {
            return new WordDictionary().initFromStream(dictStream(entries));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
