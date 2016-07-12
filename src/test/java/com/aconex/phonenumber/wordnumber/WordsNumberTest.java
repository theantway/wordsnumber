package com.aconex.phonenumber.wordnumber;

import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

@Test
public class WordsNumberTest {
    public void should_able_to_add_word_to_front() {
        WordsNumber wordsNumber = new WordsNumber()
                .addWordToFront("CALL")
                .addWordToFront("IT");

        assertThat(wordsNumber.join("-"), Is.is("IT-CALL"));
    }
}