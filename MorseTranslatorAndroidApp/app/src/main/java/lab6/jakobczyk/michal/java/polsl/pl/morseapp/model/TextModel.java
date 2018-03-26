package lab6.jakobczyk.michal.java.polsl.pl.morseapp.model;

import java.util.*;
import java.io.*;

/**
 * Cotains text that user reads from a file
 * and text that is converted to another language
 *
 * @author Michał Jakóbczyk
 * @version 4.0
 */
public class TextModel {

    /**
     * Stores text from the user
     */
    private ArrayList<String> originalText;

    /**
     * Stores converted version of the original text
     */
    private ArrayList<String> convertedText;

    /**
     * File input stream to read the data
     */
    private BufferedReader fileInput;

    /**
     * Name of the input file
     */
    private final String fileInputName;

    /**
     * File output stream to write the data
     */
    private BufferedWriter fileOutput;

    /**
     * Name of the output file
     */
    private final String fileOutputName;

    /**
     * Default constuctor of the class
     */
    public TextModel() {
        this.originalText = new ArrayList<>();
        this.convertedText = new ArrayList<>();
        this.fileInputName = null;
        this.fileOutputName = null;
    }

    /**
     * Files constructor of the class
     * @param fileInputName for reading input data from the user
     * @param fileOutputName for writing output data for the user
     */
    public TextModel(String fileInputName, String fileOutputName) {
        this.originalText = new ArrayList<>();
        this.convertedText = new ArrayList<>();
        this.fileInputName = fileInputName;
        this.fileOutputName = fileOutputName;
    }

    /**
     * Getter for original text container
     * @return original text container
     */
    public ArrayList<String> getOriginalText() {
        return this.originalText;
    }

    /**
     * Converts ArrayList of Strings to single String
     * @return original text in String variable
     */
    public String getOriginalTextString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String temp : originalText) {
            stringBuilder.append(temp);
        }

        return stringBuilder.toString();
    }

    /**
     * Setter for original text container
     * @param originalText read from file
     */
    public void setOriginalText(ArrayList<String> originalText) {
        this.originalText.clear();
        this.originalText = originalText;
    }

    /**
     * Getter for converted text container
     * @return converted text in container
     */
    public ArrayList<String> getConvertedText() {
        return this.convertedText;
    }

    /**
     * Converts ArrayList of Strings to single String
     * @return converted text in String variable
     */
    public String getConvertedTextString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String temp : convertedText) {
            stringBuilder.append(temp);
        }

        return stringBuilder.toString();
    }

    /**
     * Setter for converted text container
     * @param convertedText created by converter
     */
    public void setConvertedText(ArrayList<String> convertedText) {
        this.convertedText.clear();
        this.convertedText = convertedText;
    }

    /**
     * Reads data from file and stores in originalText container
     * @throws IOException when there was a problem with i/o
     */
    @Deprecated
    public void readDataFromFile() throws  IOException {
        String line;
        ArrayList<String> dataFromFile = new ArrayList<>();

        try {
            this.fileInput = new BufferedReader(new FileReader(fileInputName));

            while ((line = fileInput.readLine()) != null ) {
                dataFromFile.add(line);
                dataFromFile.add("\n");
            }

            this.setOriginalText(dataFromFile);

            fileInput.close();
        }
        catch (IOException e) {
            throw new IOException();
        }

    }

    /**
     * Writes to file data from convertedText container
     * @throws IOException when there was a problem with i/o
     */
    @Deprecated
    public void writeDataToFile() throws IOException {
        try {
            this.fileOutput = new BufferedWriter(new FileWriter(fileOutputName));

            for (String line : this.getConvertedText()) {
                fileOutput.write(line);
            }

            fileOutput.close();
        }
        catch (IOException e) {
            throw new IOException();
        }
    }
}
