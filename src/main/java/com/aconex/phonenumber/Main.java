package com.aconex.phonenumber;

import com.aconex.phonenumber.dict.WordDictionary;
import com.aconex.phonenumber.wordnumber.WordsCandidate;
import com.aconex.phonenumber.wordnumber.WordNumberFinder;
import com.aconex.phonenumber.wordnumber.WordsSplitter;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        WordDictionary dict = new WordDictionary().initFromStream(Main.class.getResourceAsStream("/dict_2of12.txt"));
        WordsSplitter wordsSplitter = new WordsSplitter(dict);

        print(new WordNumberFinder(wordsSplitter).findWordNumbers("1697353"));
        print(new WordNumberFinder(wordsSplitter).findWordNumbers("2255.63"));
        print(new WordNumberFinder(wordsSplitter).findWordNumbers("428445374"));
        print(new WordNumberFinder(wordsSplitter).findWordNumbers("12556348"));
        print(new WordNumberFinder(wordsSplitter).findWordNumbers("11112000"));
    }

    private static void print(List<WordsCandidate> candidates) {
        for (WordsCandidate wordsCandidate : candidates) {
            System.out.println(wordsCandidate.join("-"));
        }
    }
}
