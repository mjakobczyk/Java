package pl.polsl.java.michal.jakobczyk.lab3.exception;

/**
 * Exception for an incorrect type of conversion through texts
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public final class IncorrectConversionTypeException extends Exception {
    
    /**
     * Message viewer for the exception
     * @return adequate message
     */
    @Override
    public String getMessage() {
        return "Incorrect conversion type! Possible types are: m2l or l2m";
    }
   
}