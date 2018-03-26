package pl.polsl.java.michal.jakobczyk.lab3.model;

import java.util.*;

/**
 * Sign system for Morse
 * @author Michał Jakóbczyk
 * @version 3.0
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
