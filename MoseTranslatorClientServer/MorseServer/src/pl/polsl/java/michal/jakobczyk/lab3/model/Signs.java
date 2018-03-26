package pl.polsl.java.michal.jakobczyk.lab3.model;

import java.util.*;

/**
 * Abstract class for applying sign systems
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public abstract class Signs {
    
    /**
     * Data storage for sings of the language
     */
    protected ArrayList<String> language;
    
    private final int elementNotExisting = -1;
    
    /**
     * Check if the value exists in data
     * @param value to search for
     * @return true if the value exists
     */
    public boolean searchValue(String value) {
        for (String temp :  this.language) {
            if (temp.equals(value))
                return true;
        }
        return false;
    }
    
    /**
     * Get index of the value if exists
     * @param value to search for
     * @return index of the searched value
     */
    public int getIndexOfValue(String value) {
        if (this.searchValue(value))
            return this.language.indexOf(value);
        
        return this.elementNotExisting;
    }
    
    /**
     * Get value from the index if exists
     * @param index to get value from
     * @return the value from the index
     */
    public String getValue(int index) {
        return this.language.get(index);
    }
}