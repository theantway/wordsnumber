package com.aconex.phonenumber.wordnumber;

import com.aconex.phonenumber.dict.WordDictionary;
import com.aconex.phonenumber.dict.WordDictionaryHelper;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class WordNumberFinderTest {

    public void should_find_words_to_replace_numbers() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MYSELF");

        List<WordsCandidate> wordNumbers = new WordNumberFinder(new WordsSplitter(dict)).findWordNumbers("697353");

        assertThat(wordNumbers.size(), is(1));
        assertThat(wordNumbers.get(0).join("-"), is("MYSELF"));
    }

    public void should_ignore_non_number_characters_in_phone_number() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MYSELF");

        List<WordsCandidate> wordNumbers = new WordNumberFinder(new WordsSplitter(dict)).findWordNumbers("69.73.53");

        assertThat(wordNumbers.size(), is(1));
        assertThat(wordNumbers.get(0).join("-"), is("MYSELF"));
    }

    public void should_find_all_possible_word_replacements() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MY", "SELF", "MYSELF");

        List<WordsCandidate> wordNumbers = new WordNumberFinder(new WordsSplitter(dict)).findWordNumbers("697353");

        assertThat(wordNumbers.size(), is(2));
        assertThat(wordNumbers.get(0).join("-"), is("MY-SELF"));
        assertThat(wordNumbers.get(1).join("-"), is("MYSELF"));
    }

    public void should_return_empty_list_if_no_replacement_candidates()  {
        WordDictionary dict = WordDictionaryHelper.buildDict();

        List<WordsCandidate> wordNumbers = new WordNumberFinder(new WordsSplitter(dict)).findWordNumbers("697353");

        assertThat(wordNumbers.size(), is(0));
    }
}