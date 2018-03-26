package lab6.jakobczyk.michal.java.polsl.pl.morseapp.model;

import java.util.*;

/**
 * Sign system for Morse
 *
 * @author Michał Jakóbczyk
 * @version 4.0
 */
public class MorseSigns extends Signs {

    /**
     * Default constructor. Contains 52 morse signs in total
     */
    public MorseSigns() {
        super();
        this.language = new ArrayList<>(
                Arrays.asList(".-", "-...", "-.-.", "-..", ".", "..-.",
                        "--.", "....", "..", ".---", "-.-", ".-..",
                        "--", "-.", "---", ".--.", "--.-", ".-.", "...",
                        "-", "..-", ".--", "...-", "-.--", "-..-", "--..",
                        ".----", "..---", "...--", "....-", ".....", "-....",
                        "--...", "---..", "----.", "-----", ".-.-.-", "--..--",
                        ".----.", ".-..-.", "..--.-", "---...", "-.-.-.", "..--..",
                        "-.-.--", "-....-", ".-.-.", "-..-.", "-.--.", "-.--.-", "-...-", ".--.-."));
    }
}
