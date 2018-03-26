package pl.polsl.java.michal.jakobczyk.lab5.test;

import pl.polsl.java.michal.jakobczyk.lab5.model.MainMenuModel;
import java.io.*;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;
import pl.polsl.java.michal.jakobczyk.lab5.exception.IncorrectConversionTypeException;

/**
 * Test for class MainMenuModel
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class MainMenuModelTest {
    
    /**
     * Instance of MainMenuModel class
     */
    MainMenuModel mainMenuModel;
    
    /**
     * Instance of the folder to keep temporary files
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    /**
     * Initializaiton of the clss before each test
     */
    @Before
    public void init() {
        mainMenuModel = new MainMenuModel();
    }
    
    /**
     * Checks if the name of the input file is correct
     * @throws IOException 
     */
    @Test
    public void testValidateInputFileName() throws IOException {
        try {
            File firstFile = folder.newFile("firstFile.txt");
            assertNotNull(this.mainMenuModel.validateInputFileName(firstFile.getAbsolutePath()));

            File secondFile = folder.newFile("secondFile.txt");
            assertTrue(this.mainMenuModel.validateInputFileName(secondFile.getAbsolutePath()));   

            File thirdFile = folder.newFile("a.txt");
            thirdFile.delete();
            assertFalse(this.mainMenuModel.validateInputFileName(thirdFile.getAbsolutePath()));     

            File fourthFile = folder.newFile("qwerty.txt");
            fourthFile.delete();
            
            if (this.mainMenuModel.validateInputFileName(fourthFile.getAbsolutePath()) == false)
                throw new IOException();
            
            fail ("Exception not thrown!");
        }
        catch (IOException e) {
        }
        
    }
    
    /**
     * Checks if the name of the output file is correct
     * @throws IOException 
     */
    @Test
    public void testValidateOutputFileName() throws IOException {
        try {
            File firstFile = folder.newFile("firstFile.txt");
            assertNotNull(this.mainMenuModel.validateOutputFileName(firstFile.getAbsolutePath()));

            File secondFile = folder.newFile("secondFile.txt");
            assertTrue(this.mainMenuModel.validateOutputFileName(secondFile.getAbsolutePath()));

            File thirdFile = folder.newFile("a.txt");
            thirdFile.delete();
            assertFalse(this.mainMenuModel.validateOutputFileName(thirdFile.getAbsolutePath()));

            File fourthFile = folder.newFile("bbbbb.txt");
            fourthFile.delete();

            if (this.mainMenuModel.validateOutputFileName(fourthFile.getAbsolutePath()) == false)
                    throw new IOException();

            fail ("Exception not thrown!");
        }
        catch (IOException e) {
        }
    }
    
    /**
     * Checks if the conversion type parameter is correct
     * @throws IncorrectConversionTypeException 
     */
    @Test
    public void testValidateConversionType() throws IncorrectConversionTypeException {
        
        try { 
            assertTrue(this.mainMenuModel.validateConversionType("l2m"));
            assertTrue(this.mainMenuModel.validateConversionType("m2l"));

            assertFalse(this.mainMenuModel.validateConversionType("m2m"));
            assertFalse(this.mainMenuModel.validateConversionType("l2l"));
            assertFalse(this.mainMenuModel.validateConversionType("l2m "));
            assertFalse(this.mainMenuModel.validateConversionType("m2l  "));

            if (this.mainMenuModel.validateConversionType(" ") == false)
                throw new IncorrectConversionTypeException();

            fail("Exception not thrown!");
        }
        catch (IncorrectConversionTypeException e) {   
        }
    }
}
