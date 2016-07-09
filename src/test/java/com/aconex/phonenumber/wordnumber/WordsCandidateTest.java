package com.aconex.phonenumber.wordnumber;

import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

@Test
public class WordsCandidateTest {
    public void should_able_to_add_word_to_front() {
        WordsCandidate candidate = new WordsCandidate()
                .addFront("CALL")
                .addFront("IT");

        assertThat(candidate.join("-"), Is.is("IT-CALL"));
    }
}