package lab6.jakobczyk.michal.java.polsl.pl.morseapp;

import org.junit.*;

import lab6.jakobczyk.michal.java.polsl.pl.morseapp.model.LatinSigns;

import static org.junit.Assert.*;

/**
 * Test for class LatinSigns
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class LatinSignsTest {

    /**
     * Instance of the MorseSigns class
     */
    private LatinSigns instance;

    /**
     * Initialization of the instance before testing
     */
    @Before
    public void init() {
        instance = new LatinSigns();
    }

    /**
     * Test of searchValue method of the class LatinSigns.
     * Tests whether the given sign exists in the sign system.
     *
     */
    @Test
    public void testSearchValue() {
        assertTrue(instance.searchValue("a"));
        assertFalse(instance.searchValue("aa"));

        assertTrue(instance.searchValue("b"));
        assertTrue(instance.searchValue("3"));
        assertTrue(instance.searchValue("/"));

        assertFalse(instance.searchValue("ab"));
        assertFalse(instance.searchValue("      /"));
        assertFalse(instance.searchValue("ą"));
        assertFalse(instance.searchValue(" ."));
        assertFalse(instance.searchValue("***"));
    }

    /**
     * Test of getIndexOfValue method of the class LatinsSigns.
     * Tests whether the index of the given value is as it should
     * be in the original system.
     */
    @Test
    public void testGetIndexOfValue() {
        assertEquals(0, instance.getIndexOfValue("a"));
        assertEquals(1, instance.getIndexOfValue("b"));
        assertEquals(51, instance.getIndexOfValue("@"));

        assertNotEquals(-1, instance.getIndexOfValue("a"));
        assertNotEquals(52, instance.getIndexOfValue(".--.-."));
        assertNotEquals(100, instance.getIndexOfValue("("));
    }

    /**
     * Test of getValue method of the class LatinSigns. Tests
     * whether the returning value from the given index is the
     * same as the value in the original system.
     */
    @Test
    public void testGetValue() {
        assertEquals("a", instance.getValue(0));
        assertEquals("b", instance.getValue(1));
        assertEquals("@", instance.getValue(51));

        assertNotEquals("a", instance.getValue(51));
        assertNotEquals("@", instance.getValue(0));
        assertNotEquals("   ", instance.getValue(44));
    }
}
