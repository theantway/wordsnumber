package com.aconex.phonenumber.dict;

import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.aconex.phonenumber.dict.WordDictionaryHelper.dictReader;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

@Test
public class WordDictionaryTest {
    public void should_has_no_entry_by_default() {
        assertThat(new WordDictionary().size(), Is.is(0));
    }

    public void should_add_word_to_dict() throws IOException {
        WordDictionary dict = new WordDictionary().initFromReader(dictReader("test", "word"));

        assertThat(dict.size(), Is.is(2));
        assertTrue(dict.containsWord("test"));
        assertTrue(dict.containsWord("word"));
    }

    public void should_query_word_in_case_insensitive() throws IOException {
        WordDictionary dict = new WordDictionary().initFromReader(dictReader("tEsT", "wOrD"));

        assertThat(dict.size(), Is.is(2));
        assertTrue(dict.containsWord("test"));
        assertTrue(dict.containsWord("word"));
        assertTrue(dict.containsWord("TesT"));
        assertTrue(dict.containsWord("WorD"));
    }

    public void should_normalize_word_before_add_to_dict() throws IOException {
        WordDictionary dict = new WordDictionary().initFromReader(dictReader("test 1'-_\"~!@#$^+ word"));

        assertThat(dict.size(), Is.is(1));
        assertTrue(dict.containsWord("testword"));
    }

    public void should_not_add_invalid_word_to_dict() throws IOException {
        WordDictionary dict = new WordDictionary().initFromReader(dictReader(" 1'-_\"~!@#$^+ "));

        assertThat(dict.size(), Is.is(0));
    }
}