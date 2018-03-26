package pl.polsl.java.michal.jakobczyk.lab3.exception;

/**
 * Exception for lack of the arguments passed to the application
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public final class NoArgumentsException extends Exception {
    
    /**
     * Message viewer for the exception
     * @return adequate message
     */
    @Override
    public String getMessage() {
        return "No arguments given!";
    }
    
}