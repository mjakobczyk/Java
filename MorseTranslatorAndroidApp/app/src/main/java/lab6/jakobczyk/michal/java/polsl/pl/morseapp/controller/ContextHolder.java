package lab6.jakobczyk.michal.java.polsl.pl.morseapp.controller;

import android.content.Context;

/**
 * Class for holding the application content
 *
 * @author Michał Jakóbczyk
 * @version 6.0
 */
public class ContextHolder {

    /**
     * Application context variable
     */
    private static Context context;

    /**
     * Public setter for application context
     * @param newContext to set
     */
    public static void setCurrentContext(Context newContext) {
        context = newContext;
    }

    /**
     * Public getter for application context
     * @return current application context
     */
    public static Context getCurrectContext() {
        return context;
    }
}
