1-800-CODING-CHALLENGE Solution
===============================

Why Choose This Exercise?
-------------------------
Both exercises are interesting and solving a real problem.
I think the word phone number exercise focused on one specific problem. By working on this one I can demonstrate my coding style and problem solving skills.


Code Quality
------------
- Using TestNG for unit tests.
- Using Jacoco for code coverage. The code coverage report can be found at target/site/jacoco after mvn test.
- Using FindBugs for code quality.


Code Structures
---------------
There are following classes:

1. WordDictionary using Trie to save entries of dictionary, it support fast word lookup and prefix lookup
2. WordsSplitter and WordsNumber. WordsSplitter split a string into list of WordsNumber.
    for the same replacement, there are multiple words combinations, e.g. MYSELF -> MY-SELF and MYSELF.
    Each WordsNumber represents a list of words to split the replacement.
3. WordNumberFinder. generate number replacements and use WordsSplitter to get all WordsNumber for this replacement.
4. Main class: the glue class to init dict, read number files and use WordNumberFinder to find replacements for each number


Designs
-------
1. Use Trie to save dictionary entries. The structure is fast for word lookup and prefix lookup. See reference at: https://en.wikipedia.org/wiki/Trie

2. Using Deep First Search(DFS) to replace numbers with letters, the time complexity is about 3^numbers_count

    It would take a long time to find all possible replacements when the number is large. So I improved the performance by using early check: stop processing if current replacement could not match any words.

    For example, to find the replacements for number: '861373466543745' by using the default dict which has 41242 entries,
    it took 66892 ms without early checks, and took 154 ms after added early checks


Build And Run
--------------------
You can choose either way of the following methods to run the application:

1. By using IDE, you can open maven project by import pom.xml file in your favorite ide(Intellij Idea is suggested), run the Main class

2. To create a executable jar file with dependencies included. then you can run by:
```bash
    cd {PROJECT_ROOT_DIR}
    mvn clean package site
    java -jar target/phonenumber-1.0-SNAPSHOT-jar-with-dependencies.jar -d src/main/resources/dict_2of12.txt src/test/resources/phonenumbers.txt
```
The main class is com.aconex.phonenumber.Main, command line usages:

Main -d {dict_path} [phone_number_file1] [phone_number_file2] ...

- If no dict file specified, the default dictionary file will be used.
- If no phone number file specified, application will read from console for phone numbers.


Assumptions
-----------
1. Only a-zA-Z characters words is loaded to dictionary, other characters are ignored.
2. Chose a default dictionary from http://wordlist.aspell.net which has 41242 entries. The file is located at src/main/resources/dict_2of12.txt
3. Print out all possible combinations for the same replacement, e.g. MYSELF can be split into ["MY", "SELF"] or ["MYSELF"]
4. Application support to specify multiple phone number files from command line
