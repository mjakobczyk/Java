package pl.polsl.java.michal.jakobczyk.lab3.model;

import java.util.ArrayList;
import java.io.*;
import pl.polsl.java.michal.jakobczyk.lab3.exception.IncorrectConversionTypeException;

/**
 * Contains converion algorithms
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class ConverterModel {
    
    /**
     * Morse signs system
     */
    private final MorseSigns morseSigns;
    
    /**
     * Latin signs system
     */
    private final LatinSigns latinSigns;
    
    /**
     * Conversion type info
     * Either "l2m" or "m2l"
     */
    private final String conversionType;
    
         /**
     * Text controller 
     */
    private final TextModel textModel;
    
    /**
     * Constant value of -1 to indicate that element does not exist
     */
    private final int elementNotFound = -1;
    
    /**
     * Default delimiter during conversion
     */
    private final String delimiter = " ";
    
    
    /**
     * Constructor of the converter model
     * @param fileInputName for reading data
     * @param fileOutputName for writing data
     * @param conversionType to choose between m2l and l2m
     */
    public ConverterModel(String fileInputName, String fileOutputName, String conversionType) {
        this.morseSigns = new MorseSigns();
        this.latinSigns = new LatinSigns();
        this.conversionType = conversionType;
        this.textModel = new TextModel(fileInputName, fileOutputName);
    }
    
    /**
     * Converts string into a list of characters using a specific delimiter
     * @param delimiter which is usually a space
     * @param content which is a string to explode
     * @param morseCharacters list of morse characters exploded from content by delimiter
     */
    private void explodeString(String delimiter, String content, ArrayList<String> morseCharacters) {
        ArrayList<Integer> matchesFound = new ArrayList<>();
        matchesFound.add(-1);
        int delimiterPosition = content.indexOf(delimiter);
        if (delimiterPosition == elementNotFound)
            return;
        
        // Loop through the content as long as delimiters are found
        do {
            // Add every found delimiter position to the container
            matchesFound.add(delimiterPosition);
            
            // Calculate the next possible delimiter position
            delimiterPosition = content.indexOf(delimiter, delimiterPosition + delimiter.length());
        } while (delimiterPosition != elementNotFound);
        
        // Loop through delimiters positions as long as they exist  
        for (int i = 0; i < matchesFound.size() - 1; ++i) {
            StringBuilder textTruncate = new StringBuilder();
            
            // Append to the string builder an adequate part of the passed string
            textTruncate.append(content.substring(matchesFound.get(i) + 1, matchesFound.get(i + 1)));
            
            // Convert string builder into a string
            String explodedContent = textTruncate.toString();
            
            // Add string to the container of morse characters
            morseCharacters.add(explodedContent);
        }
    }
    
    /**
     * Converts morse code to latin
     */
    private void morseToLatin() { 
         // Converted text variable
        ArrayList<String> converted = new ArrayList<>();
        
        // Loop through all the text lines
        for (int i = 0 ; i < this.textModel.getOriginalText().size(); ++i) {
           // Single line is being taken from the input container
           // String singleLine = this.textController.getTextModel().getOriginalText().get(i);
           
           ArrayList<String> morseStrings = new ArrayList<>();
           this.explodeString(delimiter, this.textModel.getOriginalText().get(i), morseStrings);
            
            // Loop through each of the text line
            for (int j = 0; j < morseStrings.size(); ++j) {
                /*
                Single character from a single line is being converted from the character type
                o the string type and converted into a lower case because Morse Translator
                is not able to detect the size of the signs
                */
                
                int singleCharPosition = this.morseSigns.getIndexOfValue(morseStrings.get(j));
                if (singleCharPosition >= 0) {
                    converted.add(this.latinSigns.getValue(singleCharPosition));
                }
                else {
                    if (morseStrings.get(j).equals("")) {
                        converted.add(" ");
                    }
                    else {
                        converted.add(morseStrings.get(j));
                    }
                }
            }
            
            // If the current line is not the last a new line sign is being appended
            // Add a new line after converting a whole line (the 3rd attempt
            // because of morse tabbing system)
            if (i % 3 == 2 ) converted.add(System.lineSeparator());
        }

        // Update the converted text
        this.textModel.setConvertedText(converted);
    }
    
    /** 
     * Converts latin code to morse
     */
    private void latinToMorse() {
        // Converted text variable
        ArrayList<String> converted = new ArrayList<>();
        
        // Loop through all the text lines
        for (int i = 0 ; i < this.textModel.getOriginalText().size(); ++i) {
            // Single line is being taken from the input container
            String singleLine = this.textModel.getOriginalText().get(i);
            
            // Loop through each of the text line
            for (int j = 0; j < singleLine.length(); ++j) {
                /*
                Single character from a single line is being converted from the character type
                o the string type and converted into a lower case because Morse Translator
                s not able to detect the size of the signs
                */
                String singleChar = (Character.toString(singleLine.charAt(j))).toLowerCase();
                int singleCharPosition = this.latinSigns.getIndexOfValue(singleChar);
                if (singleCharPosition >= 0) {
                    converted.add(this.morseSigns.getValue(singleCharPosition));
                    converted.add(" ");
                }
                else {
                    converted.add(singleChar);
                }
            }
            
            // If the current line is not the last a new line sign is being appended
            // Add a new line after converting a whole line (the 2nd attempt
            // because of morse tabbing system)
            if (i % 2 == 1) 
                converted.add(System.lineSeparator());
            
        }
        
        // Update the converted text
        this.textModel.setConvertedText(converted);
    }
    
    /**
     * Runs the conversion of given type
     * @throws IOException if there was a problem with a stream
     * @throws IncorrectConversionTypeException if conversion type is invalid
     */
    public void convert() throws IOException, IncorrectConversionTypeException {
        
            // First use controller to read data
            this.textModel.readDataFromFile();

            // Adjust conversion type
            if (this.conversionType.equals("m2l")) {
                this.morseToLatin();
            }
            else if (this.conversionType.equals("l2m")) {
                this.latinToMorse();
            }
            else {
                throw new IncorrectConversionTypeException();
            }

            // Last step is to write converted data
            this.textModel.writeDataToFile();
    }
    
    /**
     * Getter for the TextModel
     * @return TextModel instance
     */
    public TextModel getTextModel() {
        return this.textModel;
    }
}
