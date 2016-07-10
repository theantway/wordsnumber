package com.aconex.phonenumber.dict;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class TrieTest {
    public void should_check_if_contains_word() {
        Trie trie = new Trie();
        trie.add("test");

        assertTrue(trie.contains("test"));
    }

    public void should_check_if_contains_word_case_insensitively() {
        Trie trie = new Trie();
        trie.add("case-sensitive");

        assertTrue(trie.contains("CASESENSITIVE"));
    }

    public void should_check_if_contains_word_starts_with_prefix() {
        Trie trie = new Trie();
        trie.add("case-sensitive");

        assertTrue(trie.hasWordStartsWith("CASE"));
        assertTrue(trie.hasWordStartsWith("CASESENSITIVE"));
    }
}