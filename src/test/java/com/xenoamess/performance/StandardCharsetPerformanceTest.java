package com.xenoamess.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Test to show whether using StandardCharsets is faster than using charset names.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
public class StandardCharsetPerformanceTest {
    private static final String TEST_STRING_LONG = "Stack Overflow\n" +
            "Products\n" +
            "Search?\n" +
            "\n" +
            "XenoAmess\n" +
            "83\n" +
            ", 83 reputation\n" +
            "?11 silver badge?99 bronze badges\n" +
            "Home\n" +
            "PUBLIC\n" +
            "Stack Overflow\n" +
            "Tags\n" +
            "Users\n" +
            "Jobs\n" +
            "TEAMS\n" +
            "What?s this?\n" +
            "Free 30 Day Trial\n" +
            "Is there any technical reason not to use StandardCharsets in Java?\n" +
            "Ask Question\n" +
            "Asked 4 years ago\n" +
            "Active 1 year, 2 months ago\n" +
            "Viewed 708 times\n" +
            "\n" +
            "2\n" +
            "\n" +
            "\n" +
            "1\n" +
            "As of Java 1.7, StandardCharsets are part of the standard library, but I work with a lot of legacy code " +
            "which was written well before that was implemented. I have been replacing stuff with StandardCharsets " +
            "whenever I run across it (primarily to make the code prettier/cleaner), but I have worries about making " +
            "these changes in areas which have performance-critical sections or that I can't easily debug.\n" +
            "\n" +
            "Is there any technical reason for not using Standard Charsets? As in, are there 'gotchas' or " +
            "inefficiencies that might arise from using StandardCharsets instead of Guava charsets or something like " +
            "getBytes(\"UTF-8\")? I know that \"These charsets are guaranteed to be available on every implementation" +
            " of the Java platform.\", but I don't know if they're slower or have quirks that the older methods don't" +
            " have.\n" +
            "\n" +
            "To try and keep this on-topic, assume that there's no subjective force affecting this like the " +
            "preference of other developers, resistance to change, etc.\n" +
            "\n" +
            "Also, if it affects anything, UTF-8 is the encoding I really care about.\n" +
            "\n" +
            "java encoding\n" +
            "share  edit  follow  flag \n" +
            "asked May 11 '16 at 15:40\n" +
            "\n" +
            "Jeutnarg\n" +
            "1,0681616 silver badges2424 bronze badges\n" +
            "add a comment\n" +
            "start a bounty\n" +
            "3 Answers\n" +
            "Active\n" +
            "Oldest\n" +
            "Votes\n" +
            "\n" +
            "2\n" +
            "\n" +
            "As in, are there 'gotchas' or inefficiencies that might arise from using StandardCharsets instead of " +
            "Guava charsets or something like getBytes(\"UTF-8\")?\n" +
            "\n" +
            "First of all, java.nio.charset.StandardCharsets.UTF_8 (as implemented in OpenJDK/Oracle JDK), com.google" +
            ".common.base.Charsets.UTF_8 and org.apache.commons.io.Charsets.UTF_8 are all implemented exactly " +
            "identically:\n" +
            "\n" +
            "public static final Charset UTF_8 = Charset.forName(\"UTF-8\");\n" +
            "So, at least, you don't have to worry about differences with Guava Charsets or with Charset.forName" +
            "(\"UTF-8\").\n" +
            "\n" +
            "As for String.getBytes(String) and String.getBytes(Charset), I do see a difference in the " +
            "documentation:\n" +
            "\n" +
            "For String.getBytes(Charset): \"This method always replaces malformed-input and unmappable-character " +
            "sequences with this charset's default replacement byte array.\".\n" +
            "For String.getBytes(String): \"The behavior of this method when this string cannot be encoded in the " +
            "given charset is unspecified.\".\n" +
            "So, depending on which JRE you use, I expect there might be a difference in the handling of unmappable " +
            "characters between someString.getBytes(\"UTF-8\") and someString.getBytes(StandardCharsets.UTF_8).\n" +
            "\n" +
            "share  edit  follow  flag \n" +
            "answered May 11 '16 at 16:26\n" +
            "\n" +
            "Cyäegha\n" +
            "3,90322 gold badges1616 silver badges3131 bronze badges\n" +
            "add a comment\n" +
            "\n" +
            "2\n" +
            "\n" +
            "You should use them, if only for the reason that you can't get an UnsupportedCharSetException, which is " +
            "the case if you use the forName methods and misspell the name.\n" +
            "\n" +
            "It is always a good idea to \"move\" the possibility of an error from runtime to compile time.\n" +
            "\n" +
            "share  edit  follow  flag \n" +
            "answered May 11 '16 at 16:31\n" +
            "\n" +
            "Ingo\n" +
            "34k55 gold badges4848 silver badges9090 bronze badges\n" +
            "add a comment\n" +
            "\n" +
            "0\n" +
            "\n" +
            "The best reason to not use StandardCharsets would probably be the use of special characters. Not every " +
            "character has been available since Java 1 and therefore it's likely that although this is the best for " +
            "legacy programs, it's not universally accessible and useful to everyone.\n" +
            "\n" +
            "Then again, it's probably fine for most people - and I can't imagine any performance issues here " +
            "resulting.\n" +
            "\n" +
            "share  edit  follow  flag \n" +
            "answered May 11 '16 at 15:49\n" +
            "\n" +
            "E. Nusinovich\n" +
            "10111 silver badge55 bronze badges\n" +
            "add a comment\n" +
            "Your Answer\n" +
            "Links Images Styling/Headers Lists Blockquotes Code HTMLAdvanced help\n" +
            "\n" +
            "\n" +
            "Community wiki\n" +
            "Post Your Answer\n" +
            "Not the answer you're looking for? Browse other questions tagged java encoding or ask your own question" +
            ".\n" +
            "The Overflow Blog\n" +
            "Why is Kubernetes getting so popular?\n" +
            "Podcast 240: JavaScript is ready to get its own place\n" +
            "Featured on Meta\n" +
            "What posts should be escalated to staff using [status-review], and how do I?\n" +
            "We're switching to CommonMark\n" +
            "Can we have Hot Meta Posts re-enabled now that SE has admitted that Meta?\n" +
            "We need more nominations for ?Featured on Meta?\n" +
            "Want a java job?\n" +
            "\n" +
            "Software Engineer Java Full Stack Must live in Eastern or Central Time Zone USA\n" +
            "Cardinal Financial Company, LPNo office location\n" +
            "$60K - $100KREMOTE\n" +
            "javajavascript\n" +
            "\n" +
            "Senior Java Developer Who Loves Puzzles\n" +
            "O'Reilly Auto PartsNo office location\n" +
            "REMOTE\n" +
            "javajavascript\n" +
            "\n" +
            "Software Engineer\n" +
            "IDbyDNASan Carlos, CA\n" +
            "RELOCATION\n" +
            "javapython\n" +
            "\n" +
            "Senior Back End Developer\n" +
            "iCIMSSan Jose, CA\n" +
            "javajavascript\n" +
            "\n" +
            "Senior Software Engineer, Backend\n" +
            "JelliSan Mateo, CA\n" +
            "javahibernate\n" +
            "\n" +
            "Full Stack Developer - Java, ElasticSearch, GraphQL and ReactJS\n" +
            "Contrast SecurityBaltimore, MD\n" +
            "REMOTE\n" +
            "javamysql\n" +
            "\n" +
            "Mobile Android Engineer\n" +
            "Earnin Inc.Palo Alto, CA\n" +
            "$150K - $175KRELOCATION\n" +
            "javapython\n" +
            "\n" +
            "Senior Java Backend Engineer\n" +
            "TripActionsPalo Alto, CA\n" +
            "$180K - $200KRELOCATION\n" +
            "javahibernate\n" +
            "View more jobs on Stack Overflow\n" +
            "Related\n" +
            "6577\n" +
            "Is Java ?pass-by-reference? or ?pass-by-value??\n" +
            "3301\n" +
            "How do I efficiently iterate over each entry in a Java Map?\n" +
            "2382\n" +
            "Does a finally block always get executed in Java?\n" +
            "3171\n" +
            "What is the difference between public, protected, package-private and private in Java?\n" +
            "4063\n" +
            "How do I read / convert an InputStream into a String in Java?\n" +
            "3122\n" +
            "When to use LinkedList over ArrayList in Java?\n" +
            "3499\n" +
            "How do I generate random integers within a specific range in Java?\n" +
            "2275\n" +
            "How do I determine whether an array contains a particular value in Java?\n" +
            "3026\n" +
            "How do I convert a String to an int in Java?\n" +
            "3221\n" +
            "How to create a memory leak in Java?\n" +
            "Hot Network Questions\n" +
            "How do C64 cartridges swap running programs without losing state and context?\n" +
            "Make true and false global!\n" +
            "Manage Large Table\n" +
            "How can I programmatically determine whether an Apple II .dsk disk image is a DOS .do image or a ProDOS " +
            ".po image?\n" +
            "What is this game box easter egg in The Last of Us?\n" +
            "Why do some PlotThemes not work with Mesh as expected? What can be done about it?\n" +
            "Protect API from being tampered?\n" +
            "Reaction mechanism of 2-amino-4,6-dimethylpyrimidine as a nucleophile\n" +
            "9-5=5? Matchstick problem\n" +
            "Will using Convergent Future give you a critical success if the minimum number you need to hit is 20?\n" +
            "What fallacy is this? ?This happened, therefore there must be good reasons for it?\n" +
            "Can I encrypt a message by swapping bits in the text?\n" +
            "Regarding matter-antimatter asymmetry\n" +
            "Clothes in a vegetarian society?\n" +
            "Find if there are consecutive 1s in a binary representation of a number\n" +
            "Why aren't White Americans called European-Americans?\n" +
            "Why does pistachio ice cream not taste like pistachio nuts?\n" +
            "more hot questions\n" +
            " Question feed\n" +
            "\n" +
            "STACK OVERFLOW\n" +
            "Questions\n" +
            "Jobs\n" +
            "Developer Jobs Directory\n" +
            "Salary Calculator\n" +
            "Help\n" +
            "Mobile\n" +
            "Disable Responsiveness\n" +
            "PRODUCTS\n" +
            "Teams\n" +
            "Talent\n" +
            "Advertising\n" +
            "Enterprise\n" +
            "COMPANY\n" +
            "About\n" +
            "Press\n" +
            "Work Here\n" +
            "Legal\n" +
            "Privacy Policy\n" +
            "Contact Us\n" +
            "STACK EXCHANGE\n" +
            "NETWORK\n" +
            "Technology\n" +
            "Life / Arts\n" +
            "Culture / Recreation\n" +
            "Science\n" +
            "Other\n" +
            "Blog\n" +
            "Facebook\n" +
            "Twitter\n" +
            "LinkedIn\n" +
            "Instagram\n" +
            "site design / logo © 2020 Stack Exchange Inc; user contributions licensed under cc by-sa. rev 2020.6.2" +
            ".36947\n" +
            "\n" +
            "x1\n" +
            " ";

    private static final String TEST_STRING_SHORT = "hello world in java";

    private static final String TEST_STRING_EMPTY = "";

    @Benchmark
    public void testUsingStringLong() throws UnsupportedEncodingException {
        TEST_STRING_LONG.getBytes("UTF-8");
    }

    @Benchmark
    public void testUsingCharsetLong() {
        TEST_STRING_LONG.getBytes(StandardCharsets.UTF_8);
    }

    @Benchmark
    public void testUsingStringShort() throws UnsupportedEncodingException {
        TEST_STRING_SHORT.getBytes("ascii");
    }

    @Benchmark
    public void testUsingCharsetShort() {
        TEST_STRING_SHORT.getBytes(StandardCharsets.US_ASCII);
    }

    @Benchmark
    public void testUsingStringEmpty() throws UnsupportedEncodingException {
        TEST_STRING_EMPTY.getBytes("ascii");
    }

    @Benchmark
    public void testUsingCharsetEmpty() {
        TEST_STRING_EMPTY.getBytes(StandardCharsets.US_ASCII);
    }
}
