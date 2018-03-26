package lab6.jakobczyk.michal.java.polsl.pl.morseapp.controller;

import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import lab6.jakobczyk.michal.java.polsl.pl.morseapp.exception.IncorrectConversionTypeException;
import lab6.jakobczyk.michal.java.polsl.pl.morseapp.model.ConverterModel;
import lab6.jakobczyk.michal.java.polsl.pl.morseapp.model.MainMenuModel;

/**
 * Converter controller to handle translations
 *
 * @author Michał Jakóbczyk
 * @version 6.0
 */
public class Converter {

    /**
     * Main menu model object that validates user input
     */
    private MainMenuModel mainMenuModel;

    /**
     * Converter model object that performs a conversion process
     */
    private ConverterModel converterModel;

    /**
     * Public constructor of the object
     */
    public Converter() {
        this.mainMenuModel = new MainMenuModel();
        this.converterModel = new ConverterModel("", "", "");
    }

    /**
     * Method perfming an appropriate conversion
     */
    public String performConversion(String text, String conversionType) throws IOException, IncorrectConversionTypeException {

        // Validate conversion type correctness
        if (!(this.mainMenuModel.validateConversionType(conversionType))) {
            String message = "Incorrect conversion type! Not supported by application!";

            // Inform the user about an error
            Toast.makeText(ContextHolder.getCurrectContext(), message, Toast.LENGTH_SHORT).show();
            return "Conversion went wrong!";
        }

        // Set parameters in the converter model
        this.converterModel.setParameters("", "", conversionType);

        // Set the original text passed from the js function
        ArrayList<String> temp = new ArrayList<>();
        temp.add(text);
        this.converterModel.setOriginalText(temp);

        // Perform the conversion
        this.converterModel.convert();

        // Return converted text as a string
        return this.converterModel.getTextModel().getConvertedTextString();
    }
}
