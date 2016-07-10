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
        trie.add("case-sensitive2");

        assertTrue(trie.contains("CASESENSITIVE"));
    }

    public void should_only_save_A_to_Z_letters() {
        Trie trie = new Trie();
        trie.add("test2[]");

        assertTrue(trie.contains("test"));
        assertFalse(trie.contains("test2"));
        assertFalse(trie.contains("test[]"));
    }

    public void should_check_if_contains_word_starts_with_prefix() {
        Trie trie = new Trie();
        trie.add("case-sensitive");

        assertTrue(trie.hasWordStartsWith("CASE"));
        assertTrue(trie.hasWordStartsWith("CASESENSITIVE"));
    }
}