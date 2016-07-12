package com.aconex.phonenumber;

import com.aconex.phonenumber.dict.WordDictionary;
import com.aconex.phonenumber.wordnumber.WordsNumber;
import com.aconex.phonenumber.wordnumber.WordNumberFinder;
import com.aconex.phonenumber.wordnumber.WordsSplitter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class to run.
 * <p>
 * Parse arguments to read a user defined dictionary by using -d {dictionary_file_path} in command line.
 * Use a default dictionary if no file specified. the default dictionary contains 41242 entries which is fair enough for words look up.
 * <p>
 * Read phone number files specified as command-line arguments or STDIN when no files are given.
 * Each line of these files will contain a single phone number.
 * <p>
 * The application prints out all possible word replacements from a dictionary for each phone number
 */
public class Main {
    private static final String DEFAULT_DICTIONARY = "/dict_2of12.txt";
    private static final Charset utf8 = Charset.forName("utf-8");

    public static void main(String[] args) throws IOException {
        CommandOption commandOption = parseArgs(args);

        if (!commandOption.hasPhoneNumberReader()) {
            commandOption.addPhoneNumberReader(new InputStreamReader(System.in, utf8));
        }

        processPhoneNumbers(commandOption, System.out);
    }

    /**
     * read phone numbers from list of readers, find out all possible replacements and print out the words
     *
     * @param commandOption
     * @param out
     * @throws IOException
     */
    protected static void processPhoneNumbers(CommandOption commandOption, PrintStream out) throws IOException {
        WordDictionary dict = new WordDictionary()
                .initFromReader(new InputStreamReader(dictStreamOrDefault(commandOption.dictFilePath), utf8));
        WordsSplitter wordsSplitter = new WordsSplitter(dict);

        for (Reader phoneNumberFile : commandOption.phoneNumberReaders) {
            try (BufferedReader fileReader = new BufferedReader(phoneNumberFile)) {
                while (true) {
                    String phoneNumberLine = fileReader.readLine();
                    if (phoneNumberLine == null) {
                        //end of file
                        break;
                    }

                    List<WordsNumber> wordNumbers = new WordNumberFinder(wordsSplitter, phoneNumberLine).findWordNumbers();

                    print(wordNumbers, out);
                }
            }
        }
    }

    /**
     * init dictionary from specified path, if path is null, use a default dictionary file
     *
     * @param dictFile dict file path
     * @return WordDictionary
     * @throws IOException
     */
    private static InputStream dictStreamOrDefault(String dictFile) throws IOException {
        if (dictFile == null || dictFile.isEmpty()) {
            return Main.class.getResourceAsStream(DEFAULT_DICTIONARY);
        } else {
            return new FileInputStream(dictFile);
        }
    }

    /**
     * parse arguments, use -d to specify a dictionary file, other arguments are phone number files
     *
     * @param args
     * @return dict file path
     * @throws FileNotFoundException
     */
    private static CommandOption parseArgs(String[] args) throws FileNotFoundException {
        CommandOption commandOption = new CommandOption();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-d")) {
                commandOption.dictFilePath = args[i + 1];
                i++;
            } else {
                commandOption.addPhoneNumberReader(new InputStreamReader(new FileInputStream(args[i]), utf8));
            }
        }

        return commandOption;
    }

    private static void print(List<WordsNumber> wordsNumbers, PrintStream out) {
        for (WordsNumber wordsNumber : wordsNumbers) {
            out.println(wordsNumber.join("-"));
        }
    }

    static class CommandOption {
        private String dictFilePath;
        private List<Reader> phoneNumberReaders = new ArrayList<>();

        protected void addPhoneNumberReader(Reader reader) {
            phoneNumberReaders.add(reader);
        }

        private boolean hasPhoneNumberReader() {
            return !phoneNumberReaders.isEmpty();
        }
    }
}
