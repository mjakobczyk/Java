package pl.polsl.java.michal.jakobczyk.lab3.model;

/**
 * Enum class for the user input option 
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public enum UserInputOption {
    
    /**
     * 1st parameter, input file name
     */
    INSERT_INPUT_FILE_NAME(false),
    
    /**
     * 2nd parameter, output file name
     */
    INSERT_OUTPUT_FILE_NAME(false),
    
    /**
     * 3rd parameter, conversion type name
     */
    INSERT_CONVERSION_TYPE_NAME(false),
    
    /**
     * Exit option
     */
    EXIT(false);
    
    /**
     * Indicates whether an input was either correct or not
     */
    private boolean correctInsert;
    
    /**
     * Constructor of the enum, by default sets all inserts to false
     * @param state of the insert option
     */
    UserInputOption(boolean state) {
        this.correctInsert = state;
    }
    /**
     * Gets input state of the user insert
     * @return true if the insert was correct, false otherwise
     */
    public boolean getInputState() {
        return this.correctInsert;
    }
    
    /**
     * Sets input state of the user insert
     */
    public void setInputState() {
        this.correctInsert = true;
    }
    
}
