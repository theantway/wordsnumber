package com.aconex.phonenumber.wordnumber;

import com.aconex.phonenumber.dict.WordDictionary;
import com.aconex.phonenumber.dict.WordDictionaryHelper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

@Test
public class WordNumberFinderTest {

    public void should_find_words_to_replace_numbers() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MYSELF");

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "697353").findWordNumbers();

        assertThat(wordNumbers.size(), is(1));
        assertThat(wordNumbers.get(0).join("-"), is("MYSELF"));
    }

    public void should_ignore_non_number_characters_in_phone_number() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MYSELF");

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "69.73.53").findWordNumbers();

        assertThat(wordNumbers.size(), is(1));
        assertThat(wordNumbers.get(0).join("-"), is("MYSELF"));
    }

    public void should_find_all_possible_word_replacements() {
        WordDictionary dict = WordDictionaryHelper.buildDict("MY", "SELF", "MYSELF");

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "697353").findWordNumbers();

        assertThat(wordNumbers.size(), is(2));
        assertThat(wordNumbers.get(0).join("-"), is("MY-SELF"));
        assertThat(wordNumbers.get(1).join("-"), is("MYSELF"));
    }

    public void should_return_empty_list_if_no_replacement_word_numbers()  {
        WordDictionary dict = WordDictionaryHelper.buildDict();

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "697353").findWordNumbers();

        assertThat(wordNumbers.size(), is(0));
    }

    public void should_return_empty_list_for_invalid_number()  {
        WordDictionary dict = WordDictionaryHelper.buildDict();

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "asdf").findWordNumbers();

        assertThat(wordNumbers.size(), is(0));
    }

    @Test(timeOut = 500)
    public void should_return_quickly_for_long_numbers()  {
        WordDictionary dict = WordDictionaryHelper.buildDict();

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "861373466543745").findWordNumbers();

        assertThat(wordNumbers.size(), is(0));
    }

    @Test(timeOut = 1000)
    public void should_return_quickly_for_long_numbers_using_default_dict() throws IOException {
        InputStreamReader defaultDictReader = new InputStreamReader(getClass().getResourceAsStream("/dict_2of12.txt"));
        WordDictionary dict = new WordDictionary().initFromReader(defaultDictReader);

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "861373466543745").findWordNumbers();

        assertThat(wordNumbers.size(), greaterThan(0));
    }

    @Test(timeOut = 500)
    public void should_return_empty_list_for_very_long_numbers()  {
        WordDictionary dict = WordDictionaryHelper.buildDict("A","B","C","D","E","F");

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), "2323232323232323").findWordNumbers();

        assertThat(wordNumbers.size(), is(0));
    }

    public void should_return_empty_for_null()  {
        WordDictionary dict = WordDictionaryHelper.buildDict();

        List<WordsNumber> wordNumbers = new WordNumberFinder(new WordsSplitter(dict), null).findWordNumbers();

        assertThat(wordNumbers.size(), is(0));
    }
}