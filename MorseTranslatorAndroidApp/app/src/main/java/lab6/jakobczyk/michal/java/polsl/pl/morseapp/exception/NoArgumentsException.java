package lab6.jakobczyk.michal.java.polsl.pl.morseapp.exception;

/**
 * Exception for lack of the arguments passed to the application
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class NoArgumentsException extends Exception {

    /**
     * Message viewer for the exception
     * @return adequate message
     */
    @Override
    public String getMessage() {
        return "No arguments given!";
    }
}
