package pl.polsl.java.michal.jakobczyk.lab5.model;

import java.util.*;

/**
 * Sign system for Latin
 * 
 * @author Michał Jakóbczyk
 * @version 4.0
 */
public class LatinSigns extends Signs {
    
    /**
     * Default constructor. Contains 52 Latina sings in total
     */
    public LatinSigns() {
        super();
         this.language = new ArrayList<>(
                Arrays.asList( "a", "b", "c", "d", "e", "f", "g", "h", "i", 
                    "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", 
                    "w", "v", "y", "x", "z", "1", "2", "3", "4", "5", "6", "7", 
                    "8", "9", "0", ".", ",", "'", "\"", "_", ":", ";", "?", "!", 
                    "-", "+", "/", "(", ")", "=", "@" ));
    }
}
