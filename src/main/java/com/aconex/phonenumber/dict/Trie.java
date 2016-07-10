package com.aconex.phonenumber.dict;

import java.util.Locale;

/**
 * A prefix tree implementation for case-insensitive word lookup and prefix lookup in dictionary.
 * Only support A-Z words, other characters are ignored.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Trie">Trie</a>
 */
class Trie {
    private static final int CHILDREN_NODE_SIZE = 26;

    /**
     * Root node of the trie
     */
    private TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    /**
     * add a new word to the trie
     * @param word
     */
    void add(String word) {
        TrieNode node = root;
        char c;

        word = word.toUpperCase(Locale.ENGLISH);
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            int pos = c - 'A';
            if (pos < 0 || pos > CHILDREN_NODE_SIZE) {
                continue;
            }

            if (node.children[pos] == null) {
                TrieNode newNode = new TrieNode();
                node.children[pos] = newNode;
                node = newNode;
            } else {
                node = node.children[pos];
            }
        }

        node.isEnd = true;
    }

    /**
     * check if contains a word
     * @param word
     * @return
     */
    boolean contains(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEnd;
    }

    /**
     * check if any existing word starts with given prefix
     * @param prefix
     * @return
     */
    boolean hasWordStartsWith(String prefix) {
        TrieNode node = searchNode(prefix);
        return node != null;
    }

    /**
     * search node by word
     * @param word
     * @return TrieNode, null if not found
     */
    private TrieNode searchNode(String word) {
        TrieNode node = root;
        char c;

        word = word.toUpperCase(Locale.ENGLISH);
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            int pos = c - 'A';
            if (pos < 0 || pos >= node.children.length) {
                return null;
            }
            if (node.children[pos] == null) {
                return null;
            } else {
                node = node.children[pos];
            }
        }

        return node;
    }

    static class TrieNode {
        /**
         * arrays for next letters
         */
        TrieNode[] children;

        /**
         * indicate if current node is the end of a full word.
         */
        boolean isEnd;

        TrieNode() {
            this.children = new TrieNode[CHILDREN_NODE_SIZE];
        }
    }
}
