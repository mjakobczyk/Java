package pl.polsl.java.michal.jakobczyk.lab5.test;

import pl.polsl.java.michal.jakobczyk.lab5.model.ConverterModel;
import java.io.*;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;
import pl.polsl.java.michal.jakobczyk.lab5.exception.IncorrectConversionTypeException;

/**
 * Test for class ConvertedModel
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class ConverterModelTest {
   
    /**
     * Input and output file variables
     */
    private File inputFile, outputFile;
    
    /**
     * ArrayList containers for input and output texts
     */
    private ArrayList<String> inputText, outputText;
    
    /**
     * String variables for container to string conversion
     */
    private String inputTextString, outputTextString;
    
    /**
     * Instance of the folder to keep temporary files
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    /**
     * Initialization of testing files and examplary content
     * @throws IOException when files are corrupted
     */
    @Before
    public void initialize() throws IOException {
        this.inputFile = folder.newFile("testInputFile.txt");
        this.inputFile.setWritable(true);
        this.inputFile.setReadable(true);
        
        try (FileWriter fileWriter = new FileWriter(inputFile)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            this.inputText = new ArrayList<>();
            this.inputText.add("a");
            this.inputText.add("b");
            this.inputText.add("\te");
            
            for (String temp : this.inputText) {
                bufferedWriter.write(temp);
            }
            
            this.inputTextString = String.join("", inputText);
            
            bufferedWriter.close();
            fileWriter.close(); 
            
        }
        catch (IOException e) {
            fail("Files initialization failed before conversion failed!");
        }
        
        this.outputFile = folder.newFile("testOutputFile.txt");
        this.outputFile.setWritable(true);
        this.outputFile.setReadable(true);
        
        try (FileWriter fileWriterz = new FileWriter(outputFile)) {
            BufferedWriter bufferedWriterz = new BufferedWriter(fileWriterz);
            
            this.outputText = new ArrayList<>();
            this.outputText.add("-. -. -. -. -. -. -. ---- \n");
            this.outputText.add("");
            this.outputText.add("-... \n");
            this.outputText.add(".....\n ");
            this.outputText.add("");
            
            for (String temp : this.outputText) {
                bufferedWriterz.write(temp);
            }
            
            this.outputTextString = String.join("", outputText);
            
            bufferedWriterz.close();
            fileWriterz.close(); 
              
        }
        catch (IOException e) {
            fail("Files initialization failed before conversion failed!");
        }
       
    }
    
    /**
     * Test for latin to morse conversion when parameteers are valid
     * @throws IOException when files are corrupted
     */
    @Test
    public void testConvertLatin() throws IOException{
        try {
            ConverterModel instance = new ConverterModel(
                inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), "l2m");
            
            instance.convert();
            
            assertTrue(instance.getTextModel().getConvertedText().contains(".-"));
            assertTrue(instance.getTextModel().getConvertedText().contains("-..."));
            assertFalse(instance.getTextModel().getConvertedText().contains("-----"));
            assertFalse(instance.getTextModel().getConvertedText().contains("....."));
            
        }
        catch (IOException | IncorrectConversionTypeException e) {
            fail ("Exception has occured during latin to morse conversion!");
        }
    }
    
    /**
     * Test for morse to latin conversion when parameters are valid
     * @throws IOException when files are corrupted
     * @throws IncorrectConversionTypeException
     */
    @Test
    public void testConvertMorse() throws IOException, IncorrectConversionTypeException {
        try {
            ConverterModel instance = new ConverterModel(
                outputFile.getAbsolutePath(), inputFile.getAbsolutePath(), "m2l");
            
            instance.convert();
            
            assertTrue(instance.getTextModel().getConvertedText().contains("n"));
            assertTrue(instance.getTextModel().getConvertedText().contains("b"));
            assertFalse(instance.getTextModel().getConvertedText().contains("-----"));
            assertFalse(instance.getTextModel().getConvertedText().contains("....."));
        }
        catch (IOException | IncorrectConversionTypeException e) {
            fail ("Exception has occured during morse to latin conversion!");
        }
    }
    
    /**
     * Test for conversion when files are corrupted and conversion is valid
     * @throws IOException when files are corrupted
     * @throws IncorrectConversionTypeException when conversion type does not exist
     */
    @Test
    public void testConvertWithIncorrectInputFile() throws IOException, IncorrectConversionTypeException {
        try {
            inputFile.delete();
            outputFile.delete();
            inputFile.setWritable(false);
            outputFile.setWritable(false);
            ConverterModel instance = new ConverterModel(
                inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), "m2l");
            instance.convert();
            fail ("Exception with incorrect input file conversion did not occur!");
        }
        catch (IOException | IncorrectConversionTypeException e) {
        }
    }
    
    /**
     * Test for conversion when files are valid and conversion is corrupted
     * @throws IOException
     * @throws IncorrectConversionTypeException 
     */
    @Test
    public void testConvertWithIncorrectConversionType() throws IOException, IncorrectConversionTypeException {
        try {
            ConverterModel instance = new ConverterModel(
                inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), "m2m");
            instance.convert();
            fail ("Exception with incorrect conversion type convertion did not occur!");
        }
        catch (IOException | IncorrectConversionTypeException e) {
        }
    }
    
    /**
     * Test for adding new parameters to the original parameters history
     */
    @Test
    public void testAddParametersToHistory() {
        ConverterModel instance = new ConverterModel(
                inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), "m2m");
        
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("A");
        parameters.add("b");
        parameters.add("1");
        instance.addParametersToHistory(parameters);
        
        assertTrue(instance.getParametersHistory().get(0).get(0).equals("A"));
        assertTrue(instance.getParametersHistory().get(0).get(1).equals("b"));
        assertTrue(instance.getParametersHistory().get(0).get(2).equals("1"));
        assertFalse(instance.getParametersHistory().get(0).get(0).equals("57887"));
        assertFalse(instance.getParametersHistory().get(0).get(1).equals("bvqqqqqqq"));
        assertFalse(instance.getParametersHistory().get(0).get(2).equals("2"));
        
    }
}
