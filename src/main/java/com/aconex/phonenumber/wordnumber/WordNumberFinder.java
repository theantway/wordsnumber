package com.aconex.phonenumber.wordnumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.Character.isDigit;

/**
 * Replace a phone number with all possible letters
 * and use {@link WordsSplitter} to check if the replacement has matched words in dictionary
 */
public class WordNumberFinder {
    private static final Logger logger = LoggerFactory.getLogger(WordNumberFinder.class);
    private static final int MAX_PHONE_NUMBER_LENGT = 15;

    private WordsSplitter wordsSplitter;

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

    public WordNumberFinder(WordsSplitter wordsSplitter) {
        this.wordsSplitter = wordsSplitter;
    }

    /**
     * Find all possible list of words from a dictionary to represent a phone number.
     *
     * @param number phone number
     * @return list of {@link WordsCandidate}, empty list if no matched words found.
     */
    public List<WordsCandidate> findWordNumbers(String number) {
        String normalizedNumber = normalizeNumber(number);

        if (normalizedNumber.length() == 0) {
            return Collections.emptyList();
        }

        if (normalizedNumber.length() > MAX_PHONE_NUMBER_LENGT) {
            logger.warn("Phone number too large: " + number);
            return Collections.emptyList();
        }

        return findWordNumbers(normalizedNumber, 0, new ArrayList<>(), new ArrayDeque<>());
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
     * use dfs to replace each number, and return all matched candidates
     *
     * @param number          phone number
     * @param pos             replace from this position
     * @param candidates      save matched candidates
     * @param replacedNumbers current replaced number
     * @return list of {@link WordsCandidate}, empty list if no matched words found.
     */
    private List<WordsCandidate> findWordNumbers(String number, int pos, List<WordsCandidate> candidates, Deque<Character> replacedNumbers) {
        if (pos == number.length()) {
            candidates.addAll(wordsSplitter.splitWords(toStr(replacedNumbers)));
            return candidates;
        }

        //Stop replacing the following numbers if no potential words for current replacement
        if (!wordsSplitter.canSplitWords(toStr(replacedNumbers))) {
            return candidates;
        }

        char c = number.charAt(pos);

        for (Character letter : PHONE_KEY_MAPS.get(c)) {
            replacedNumbers.addLast(letter);
            findWordNumbers(number, pos + 1, candidates, replacedNumbers);
            replacedNumbers.removeLast();
        }

        if (pos == 0 || !isDigit(replacedNumbers.peekLast())) {
            replacedNumbers.addLast(c);
            findWordNumbers(number, pos + 1, candidates, replacedNumbers);
            replacedNumbers.removeLast();
        }

        return candidates;
    }

    /**
     * convert Deque of Character to a string
     *
     * @param deque
     * @return string
     */
    private String toStr(Deque<Character> deque) {
        StringBuilder builder = new StringBuilder(deque.size());

        for (Character aDeque : deque) {
            builder.append(aDeque);
        }

        return builder.toString();
    }
}
