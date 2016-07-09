package com.aconex.phonenumber;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;

import static java.lang.String.join;
import static java.util.Arrays.asList;

@Test
public class MainTest {
    public void should_find_all_possible_words_for_number_using_default_dict() throws IOException {
        Main.processPhoneNumbers(null, asList(new StringReader(join("\n",
                "1697353",
                "2255.63",
                "428445374",
                "12556348",
                "11112000"
        ))));
    }
}