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

        List<WordsCandidate> candidates = wordsSplitter.splitWords("CALLME");

        assertThat(candidates.size(), Is.is(1));
        assertThat(candidates.get(0).join("-"), Is.is("CALL-ME"));
    }

    public void should_return_all_possible_splitting_candidates() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict("M", "Y", "MY", "SELF", "MYSELF"));

        List<WordsCandidate> candidates = wordsSplitter.splitWords("MYSELF");

        assertThat(candidates.size(), Is.is(3));
        assertThat(candidates.get(0).join("-"), Is.is("M-Y-SELF"));
        assertThat(candidates.get(1).join("-"), Is.is("MY-SELF"));
        assertThat(candidates.get(2).join("-"), Is.is("MYSELF"));
    }

    public void should_return_empty_list_when_no_matched_words() {
        WordsSplitter wordsSplitter = new WordsSplitter(buildDict());

        List<WordsCandidate> candidates = wordsSplitter.splitWords("any3");

        assertThat(candidates.size(), Is.is(0));
    }
}