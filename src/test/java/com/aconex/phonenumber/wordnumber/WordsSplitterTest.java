package com.aconex.phonenumber.wordnumber;

import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import java.util.List;

import static com.aconex.phonenumber.dict.WordDictionaryHelper.buildDict;
import static org.hamcrest.MatcherAssert.assertThat;

@Test
public class WordsSplitterTest {
    public void should_split_word() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict("CALL", "ME"));

        List<WordsNumber> wordsNumbers = wordsSplitter.splitStringToWords("CALLME");

        assertThat(wordsNumbers.size(), Is.is(1));
        assertThat(wordsNumbers.get(0).join("-"), Is.is("CALL-ME"));
    }

    public void should_return_if_can_split_word() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict("CALL", "ME"));

        assertThat(wordsSplitter.canSplitStringToWords("CALLME"), Is.is(true));
    }

    public void should_return_if_can_split_word_partial() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict("CALL", "ME"));

        assertThat(wordsSplitter.canSplitStringToWords("CALLM"), Is.is(true));
        assertThat(wordsSplitter.canSplitStringToWords("1CALLM"), Is.is(true));
    }

    public void should_return_all_possible_splitting_words_numbers() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict("M", "Y", "MY", "SELF", "MYSELF"));

        List<WordsNumber> wordsNumbers = wordsSplitter.splitStringToWords("MYSELF");

        assertThat(wordsNumbers.size(), Is.is(3));
        assertThat(wordsNumbers.get(0).join("-"), Is.is("M-Y-SELF"));
        assertThat(wordsNumbers.get(1).join("-"), Is.is("MY-SELF"));
        assertThat(wordsNumbers.get(2).join("-"), Is.is("MYSELF"));
    }

    public void should_return_empty_list_when_no_matched_words() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict());

        List<WordsNumber> wordsNumbers = wordsSplitter.splitStringToWords("any3");

        assertThat(wordsNumbers.size(), Is.is(0));
    }
}