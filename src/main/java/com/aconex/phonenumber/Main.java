package com.aconex.phonenumber;

import com.aconex.phonenumber.dict.WordDictionary;
import com.aconex.phonenumber.wordnumber.WordsCandidate;
import com.aconex.phonenumber.wordnumber.WordNumberFinder;
import com.aconex.phonenumber.wordnumber.WordsSplitter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class to run.
 *
 * Parse arguments to read a user defined dictionary by using -d {dictionary_file_path} in command line.
 * Use a default dictionary if no file specified. the default dictionary contains 41242 entries which is fair enough for words look up.
 *
 * Read phone number files specified as command-line arguments or STDIN when no files are given.
 * Each line of these files will contain a single phone number.
 *
 * The application prints out all possible word replacements from a dictionary for each phone number
 */
public class Main {
    private static final String DEFAULT_DICTIONARY = "/dict_2of12.txt";
    private static final Charset utf8 = Charset.forName("utf-8");

    public static void main(String[] args) throws IOException {
        List<Reader> phoneNumberReaders = new ArrayList<>();
        String dictFile = parseArgs(args, phoneNumberReaders);

        if (phoneNumberReaders.isEmpty()) {
            phoneNumberReaders.add(new InputStreamReader(System.in, utf8));
        }

        processPhoneNumbers(dictFile, phoneNumberReaders, System.out);
    }

    /**
     * read phone numbers from list of readers, find out all possible replacements and print out the words
     * @param dictFile file path of dict file, will use default dict if is null
     * @param phoneNumberReaders list of phone number readers to support read from multiple files
     * @param out
     * @throws IOException
     */
    protected static void processPhoneNumbers(String dictFile, List<Reader> phoneNumberReaders, PrintStream out) throws IOException {
        WordDictionary dict = new WordDictionary()
                .initFromReader(new InputStreamReader(dictStream(dictFile), utf8));
        WordNumberFinder wordNumberFinder = new WordNumberFinder(new WordsSplitter(dict));

        for (Reader phoneNumberFile : phoneNumberReaders) {
            try (BufferedReader fileReader = new BufferedReader(phoneNumberFile)) {
                while (true) {
                    String line = fileReader.readLine();
                    if (line == null) {
                        //end of file
                        break;
                    }

                    List<WordsCandidate> candidates = wordNumberFinder.findWordNumbers(line);

                    print(candidates, out);
                }
            }
        }
    }

    /**
     * init dictionary from specified path, if path is null, use a default dictionary file
     * @param dictFile dict file path
     * @return WordDictionary
     * @throws IOException
     */
    private static InputStream dictStream(String dictFile) throws IOException {
        if (dictFile == null) {
            return Main.class.getResourceAsStream(DEFAULT_DICTIONARY);
        } else {
            return new FileInputStream(dictFile);
        }
    }

    /**
     * parse arguments, use -d to specify a dictionary file, other arguments are phone number files
     * @param args
     * @param phoneNumberReaders list of phone number readers, to save readers for phone number files
     * @return dict file path
     * @throws FileNotFoundException
     */
    private static String parseArgs(String[] args, List<Reader> phoneNumberReaders) throws FileNotFoundException {
        String dictFile = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-d")) {
                dictFile = args[i + 1];
                i++;
            } else {
                phoneNumberReaders.add(new InputStreamReader(new FileInputStream(args[i]), utf8));
            }
        }

        return dictFile;
    }

    private static void print(List<WordsCandidate> candidates, PrintStream out) {
        for (WordsCandidate wordsCandidate : candidates) {
            out.println(wordsCandidate.join("-"));
        }
    }
}
