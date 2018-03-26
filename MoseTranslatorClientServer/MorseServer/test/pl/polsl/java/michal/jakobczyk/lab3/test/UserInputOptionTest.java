package pl.polsl.java.michal.jakobczyk.lab3.test;
        
import pl.polsl.java.michal.jakobczyk.lab3.model.UserInputOption;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test for class UserInputOption
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class UserInputOptionTest {
    
    /**
     * Test for user input get state
     */
    @Test
    public void testGetInputState() {
        UserInputOption userInputOption = UserInputOption.INSERT_INPUT_FILE_NAME;
        assertEquals(userInputOption.getInputState(), false);
        assertNotEquals(userInputOption.getInputState(), true);
        
        userInputOption = UserInputOption.INSERT_OUTPUT_FILE_NAME;
        assertEquals(userInputOption.getInputState(), false);
        assertNotEquals(userInputOption.getInputState(), true);
        
        userInputOption = UserInputOption.INSERT_CONVERSION_TYPE_NAME;
        assertEquals(userInputOption.getInputState(), false);
        assertNotEquals(userInputOption.getInputState(), true);
        
        userInputOption = UserInputOption.EXIT;
        assertEquals(userInputOption.getInputState(), false);
        assertNotEquals(userInputOption.getInputState(), true);
    }
    
    
    /**
     * Test for user input set state
     */
    @Test
    public void testSetInputState() {
        UserInputOption userInputOption = UserInputOption.INSERT_INPUT_FILE_NAME;
        UserInputOption.INSERT_INPUT_FILE_NAME.setInputState();
        assertEquals(userInputOption.getInputState(), true);
        assertNotEquals(userInputOption.getInputState(), false);
        
        userInputOption = UserInputOption.INSERT_OUTPUT_FILE_NAME;
        UserInputOption.INSERT_OUTPUT_FILE_NAME.setInputState();
        assertEquals(userInputOption.getInputState(), true);
        assertNotEquals(userInputOption.getInputState(), false);
        
        userInputOption = UserInputOption.INSERT_CONVERSION_TYPE_NAME;
        UserInputOption.INSERT_CONVERSION_TYPE_NAME.setInputState();
        assertEquals(userInputOption.getInputState(), true);
        assertNotEquals(userInputOption.getInputState(), false);
        
        userInputOption = UserInputOption.EXIT;
        UserInputOption.EXIT.setInputState();
        assertEquals(userInputOption.getInputState(), true);
        assertNotEquals(userInputOption.getInputState(), false);
    }
}
