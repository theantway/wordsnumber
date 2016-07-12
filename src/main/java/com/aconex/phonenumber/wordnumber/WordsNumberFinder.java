package com.aconex.phonenumber.wordnumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.Character.isDigit;

/**
 * Replace a phone number with all possible letters
 * and use {@link WordsSplitter} to check if the replacement has matched words in dictionary
 */
public class WordsNumberFinder {
    private static final Logger logger = LoggerFactory.getLogger(WordsNumberFinder.class);
    private static final int MAX_PHONE_NUMBER_LENGT = 15;

    private final WordsSplitter wordsSplitter;
    private final String originalNumber;
    private final List<WordsNumber> wordsNumbers = new ArrayList<>();

    private final Deque<Character> currentReplacedNumber = new ArrayDeque<>();

    private static final Map<Character, char[]> PHONE_KEY_MAPS = new HashMap<Character, char[]>() {
        {
            add('0', "");
            add('1', "");
            add('2', "ABC");
            add('3', "DEF");
            add('4', "GHI");
            add('5', "JKL");
            add('6', "MNO");
            add('7', "PQRS");
            add('8', "TUV");
            add('9', "WXYZ");
        }

        private void add(char c, String letters) {
            put(c, letters.toCharArray());
        }
    };

    public WordsNumberFinder(WordsSplitter wordsSplitter, String originalNumber) {
        this.wordsSplitter = wordsSplitter;
        this.originalNumber = originalNumber;
    }

    /**
     * Find all possible list of words from a dictionary to represent a phone number.
     *
     * @return list of {@link WordsNumber}, empty list if no matched words found.
     */
    public List<WordsNumber> findWordNumbers() {
        String normalizedNumber = normalizeNumber(originalNumber);

        if (normalizedNumber.length() == 0) {
            return Collections.emptyList();
        }

        if (normalizedNumber.length() > MAX_PHONE_NUMBER_LENGT) {
            logger.warn("Phone number too large: " + originalNumber);
            return Collections.emptyList();
        }

        return findWordNumbers(normalizedNumber, 0);
    }

    /**
     * clean up phone number, keeps only digit characters.
     *
     * @param number
     * @return
     */
    private String normalizeNumber(String number) {
        if (number == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()) {
            if (isDigit(c)) {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    /**
     * use dfs to replace each number, and return all matched words numbers
     *
     * @param number          phone number
     * @param pos             replace from this position
     * @return list of {@link WordsNumber}, empty list if no matched words found.
     */
    private List<WordsNumber> findWordNumbers(String number, int pos) {
        if (pos == number.length()) {
            wordsNumbers.addAll(wordsSplitter.splitWords(currentReplacedNumber()));
            return wordsNumbers;
        }

        //Stop replacing the following numbers if no potential words for current replacement
        if (!wordsSplitter.canSplitWords(currentReplacedNumber())) {
            return wordsNumbers;
        }

        char c = number.charAt(pos);

        for (Character letter : PHONE_KEY_MAPS.get(c)) {
            currentReplacedNumber.addLast(letter);
            findWordNumbers(number, pos + 1);
            currentReplacedNumber.removeLast();
        }

        if (pos == 0 || !isDigit(currentReplacedNumber.peekLast())) {
            currentReplacedNumber.addLast(c);
            findWordNumbers(number, pos + 1);
            currentReplacedNumber.removeLast();
        }

        return wordsNumbers;
    }

    /**
     * convert Deque of Character to a string
     *
     * @return string
     */
    private String currentReplacedNumber() {
        StringBuilder builder = new StringBuilder(currentReplacedNumber.size());

        currentReplacedNumber.forEach(builder::append);

        return builder.toString();
    }
}
