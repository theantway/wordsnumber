package com.aconex.phonenumber.wordnumber;

import java.util.*;

/**
 * Find a list of words from a dictionary to represent a phone number.
 */
public class WordNumberFinder {
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
        return findWordNumbers(number, 0, new ArrayList<>(), new ArrayDeque<>());
    }

    /**
     * use dfs to replace each number, and return all matched candidates
     * @param number phone number
     * @param pos replace from this position
     * @param candidates save matched candidates
     * @param replacedNumbers current replaced number
     * @return list of {@link WordsCandidate}, empty list if no matched words found.
     */
    private List<WordsCandidate> findWordNumbers(String number, int pos, List<WordsCandidate> candidates, Deque<Character> replacedNumbers) {
        if (pos == number.length()) {
            candidates.addAll(wordsSplitter.splitWords(toStr(replacedNumbers)));
            return candidates;
        }

        char c = number.charAt(pos);
        if (!Character.isDigit(c)) {
            //ignore non number characters
            findWordNumbers(number, pos + 1, candidates, replacedNumbers);
        } else {
            for (Character letter : PHONE_KEY_MAPS.get(c)) {
                replacedNumbers.addLast(letter);
                findWordNumbers(number, pos + 1, candidates, replacedNumbers);
                replacedNumbers.removeLast();
            }

            if (pos == 0 || !Character.isDigit(replacedNumbers.peekLast())) {
                replacedNumbers.addLast(c);
                findWordNumbers(number, pos + 1, candidates, replacedNumbers);
                replacedNumbers.removeLast();
            }
        }

        return candidates;
    }

    /**
     * convert Deque of Character to a string
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
