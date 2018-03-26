package pl.polsl.java.michal.jakobczyk.lab5.test;

import pl.polsl.java.michal.jakobczyk.lab5.model.TextModel;
import java.io.*;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

/**
 * Test for class TextModel
 * @author Michał Jakóbczyk
 * @version 2.0
 */
public class TextModelTest {
    
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
     * Initializes temporary input and output files for method.
     * Uses TemporaryFolder class and newFile method to create
     * new files for testing purposes.
     * @throws IOException 
     */
    @Before
    public void initializeFiles() throws IOException {
        // Input initialization
        this.inputFile = folder.newFile("testInputFile.txt");
        this.inputFile.setWritable(true);
        this.inputFile.setReadable(true);
        
        try (FileWriter fileWriter = new FileWriter(inputFile)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            this.inputText = new ArrayList<>();
            this.inputText.add("Line one");
            this.inputText.add("Line \ttwo");
            this.inputText.add("...and even line three");
            
            for (String temp : this.inputText) {
                bufferedWriter.write(temp);
            }
            
            bufferedWriter.close();
            fileWriter.close();
            
            this.inputTextString = String.join("", inputText);
        }
        catch (IOException e) {   
        }
        
        // Output initialization
        this.outputFile = folder.newFile("testOutputFile.txt");
        this.outputFile.setWritable(true);
        this.outputFile.setReadable(true);
        
        try (FileWriter fileWriterz = new FileWriter(outputFile)) {
            BufferedWriter bufferedWriterz = new BufferedWriter(fileWriterz);
            
            this.outputText = new ArrayList<>();
            this.outputText.add("Something shall");
            this.outputText.add("be\t written");
            this.outputText.add("\tinthis f   i   l   e");
            
            for (String temp : this.outputText) {
                bufferedWriterz.write(temp);
            }
            
            bufferedWriterz.close();
            fileWriterz.close();
            
            this.outputTextString = String.join("", outputText);      
        }
        catch (IOException e) {   
        }
       
    }
    
    @Test
    public void testGetOriginalTextString() throws IOException {
        StringBuilder tempBuilder = new StringBuilder();
        for (String tempString : inputText) {
            tempBuilder.append(tempString);
        }
        
        String finalResult = tempBuilder.toString();
        
        TextModel instance = new TextModel(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
        instance.readDataFromFile();    
        
        assertTrue(instance.getOriginalTextString().contains(finalResult));
    }
    
    /**
     * Checks if the method reads data from file properly. Matches the text to be read
     * to the text read straight from the file
     * @throws IOException 
     */
    @Test
    public void testReadDataFromFile() throws IOException {
        try {
            TextModel instance = new TextModel(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
            instance.readDataFromFile();      
            assertTrue("Data read from file", instance.getOriginalText().contains(inputTextString));
            assertFalse("Data read from file", instance.getOriginalText().contains("uwjbr2wqjntqw"));
            inputFile.setReadable(false);
            inputFile.setWritable(false);
            inputFile.delete();
            instance.readDataFromFile(); 
            fail("Exception has not occured when reading from file.");
        }
        catch (IOException e) {
        }
    }
    
   /**
    * Checks if the method writes data to the file properly. Matches the text to be written
    * and what file contains
    * @throws IOException 
    */
    @Test
    public void testWriteDataToFile() throws IOException {
        try {
            TextModel instance = new TextModel(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
            instance.writeDataToFile();
            instance.setConvertedText(this.outputText);
            assertTrue("Data wrote to file", instance.getConvertedTextString().equals(this.outputTextString));
            assertFalse("Data wrote to file", instance.getConvertedTextString().equals(this.inputTextString));
            outputFile.setWritable(false);
            outputFile.setReadable(false);
            instance.writeDataToFile();
            fail("Exception has not occured when writing to file.");
        }
        catch (IOException e) {
        }
        
    }
}
