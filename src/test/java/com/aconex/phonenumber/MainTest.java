package com.aconex.phonenumber;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import static java.lang.String.join;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@Test
public class MainTest {
//    @Test(timeOut = 1500)
    public void should_find_all_possible_words_for_number_using_default_dict() throws IOException {
        Main.CommandOption commandOption = new Main.CommandOption();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        commandOption.addPhoneNumberReader(new StringReader(join("\n",
                "1697353",
                "2255.63",
                "428445374",
                "12556348",
                "11112000",
                "861373464564564",
                "861373464564564456456"
        )));
        Main.processPhoneNumbers(commandOption, new PrintStream(outputStream));

        String output = outputStream.toString();

        assertThat(output, containsString("1-MY-SELF"));
        assertThat(output, containsString("1-MYSELF"));
        assertThat(output, containsString("CALL-ME"));
        assertThat(output, containsString("HAT-HIKE-PI"));
        assertThat(output, containsString("TO-1-ERE-GO-I-JOG-LOG"));
    }
}