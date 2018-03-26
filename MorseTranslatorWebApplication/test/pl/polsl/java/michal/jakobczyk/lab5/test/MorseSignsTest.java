package pl.polsl.java.michal.jakobczyk.lab5.test;

import pl.polsl.java.michal.jakobczyk.lab5.model.MorseSigns;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test for class MorseSigns
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class MorseSignsTest {
    
    /**
     * Instance of the MorseSigns class
     */
    private MorseSigns instance;
    
    /**
     * Initialization of the instance before testing
     */
    @Before
    public void init() {
        instance = new MorseSigns();
    }
    
    /**
     * Test of search value method. Tests whether the
     * given sign exists in the sign system.
     */
    @Test
    public void testSearchValue() {
        assertTrue(instance.searchValue(".-"));
        assertFalse(instance.searchValue(". -"));
        
        assertTrue(instance.searchValue("....-"));
        assertTrue(instance.searchValue(".--.-."));
        assertTrue(instance.searchValue("....."));
        
        assertFalse(instance.searchValue("......    "));
        assertFalse(instance.searchValue(" ....."));
        assertFalse(instance.searchValue("      ....."));
        assertFalse(instance.searchValue(""));
        assertFalse(instance.searchValue("hnuq67y8hui25r8g72"));
    }
    
    /**
     * Test of getIndexOfValue method. Tests whether the
     * index of the given value is as it should be in the
     * original system
     */
    @Test
    public void testGetIndexOfValue() {
        assertEquals(0, instance.getIndexOfValue(".-"));
        assertEquals(1, instance.getIndexOfValue("-..."));
        assertEquals(51, instance.getIndexOfValue(".--.-."));
        
        assertNotEquals(-1, instance.getIndexOfValue(".-"));
        assertNotEquals(52, instance.getIndexOfValue( ".--.-."));
        assertNotEquals(100, instance.getIndexOfValue("uywvelywgvr"));
    }
    
    /**
     * Test of getValue method. Tests whether the returning
     * value from the given index is the same as the value
     * in the original system
     */
    @Test
    public void testGetValue() {
        assertEquals(".-", instance.getValue(0));
        assertEquals("-...", instance.getValue(1));
        assertEquals(".--.-.", instance.getValue(51));
        
        assertNotEquals(".-", instance.getValue(51));
        assertNotEquals("-...", instance.getValue(0));
        assertNotEquals(".--.-.", instance.getValue(35));
    }
}
