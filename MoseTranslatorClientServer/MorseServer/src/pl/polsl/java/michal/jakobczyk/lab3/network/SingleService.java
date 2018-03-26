package pl.polsl.java.michal.jakobczyk.lab3.network;

import pl.polsl.java.michal.jakobczyk.lab3.exception.*;
import pl.polsl.java.michal.jakobczyk.lab3.model.*;

import java.io.*;
import java.net.Socket;

/**
 * The server class servicing a single connection
 * @author Michał Jakóbczyk
 * @version 3.0
 */
class SingleService {

    /**
     * Socket representing connection to the client
     */
    private final Socket socket;
    
    /**
     * Buffered input character stream
     */
    private final BufferedReader input;
    
    /**
     * Formatted output character stream
     */
    private final PrintWriter output;
    
    /**
     * Main menu model object that validates user input
     */
    private final MainMenuModel mainMenuModel;
    
    /**
     * Converter model object that performs a conversion process
     */
    private ConverterModel converterModel;

    /**
     * The constructor of instance of the SingleService class. 
     * Use the socket as a parameter.
     * @param socket socket representing connection to the client
     * @param mainMenuModel passed from the server controller
     * @throws IOException when either input or output streams are corrupted
     */
    public SingleService(Socket socket, MainMenuModel mainMenuModel) throws IOException {
        this.socket = socket;
        output = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        
        this.mainMenuModel = mainMenuModel;
        this.converterModel = null;
    }

    /**
     * Realizes the service
     * @throws IncorrectConversionTypeException when conversion type is invalid
     */
    public void realize() 
            throws IncorrectConversionTypeException {
        try {
            this.sendMessage("[000]Commands: START (enters the conversion mode), HELP (shows the list of commands), EXIT (quits the application");
            boolean exit = false;

            while(exit != true) {
                // Ask the user to insert a command
                String inputLine = input.readLine();
                
                // Convert the read line to the upper case for easier validation
                inputLine = inputLine.toUpperCase();

                switch (inputLine) {
                    // Conversion process
                    case "START":
                        this.startCommand();
                        exit = true;
                        break;
                    // Help command to inform the user about possible commands
                    case "HELP":
                        this.helpCommand();
                        break;
                    // Exit command to quit the application
                    case "EXIT":
                        exit = true;
                        break;
                    default:
                        this.sendMessage("[100]Unknown command! Type help for the list of commands!");
                        break;
                }
            }
            
            // Inform the user about the application shutdown
            this.sendMessage("[999]Application is turning off. Press any key...");
        } 
        catch (IOException e) {
            System.err.println(e.getMessage());
        } 
        finally {
            try {
                this.socket.close();
            } 
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Command to start the conversion
     * @throws IOException when i/o stream is corrupted
     * @throws IncorrectConversionTypeException when conversion type is invalid
     */
    private void startCommand() 
            throws IOException, IncorrectConversionTypeException {
        
        UserInputOption userInputOption = UserInputOption.INSERT_INPUT_FILE_NAME;
        StringBuilder fileInputName = new StringBuilder();
        StringBuilder fileOutputName = new StringBuilder();
        StringBuilder conversionType = new StringBuilder();

        try {
            //userInputOption = UserInputOption.INSERT_INPUT_FILE_NAME
            while (userInputOption != UserInputOption.EXIT) {
                switch (userInputOption) {
                    // When user is asked for input file name
                    case INSERT_INPUT_FILE_NAME:
                        while (UserInputOption.INSERT_INPUT_FILE_NAME.getInputState() == false) {
                            fileInputName.setLength(0);
                            output.println("[100] Enter full input file name with directory: ");
                            fileInputName.append(this.input.readLine());
                            if (this.mainMenuModel.validateInputFileName(fileInputName.toString())) {
                                UserInputOption.INSERT_INPUT_FILE_NAME.setInputState();
                                userInputOption = UserInputOption.INSERT_OUTPUT_FILE_NAME;
                            } 
                            else {
                                this.sendMessage("[101] Incorrect input file name! Example: C:\\\\Dokumenty\\\\Pliki\\\\plikwej.txt Click Enter to continue.");
                                this.input.readLine();
                            }
                        }
                        break;

                    // When user is asked for output file name
                    case INSERT_OUTPUT_FILE_NAME:
                        while (UserInputOption.INSERT_OUTPUT_FILE_NAME.getInputState() == false) {
                            fileOutputName.setLength(0);
                            output.println("[200]Enter full output file name with directory: ");
                            fileOutputName.append(this.input.readLine());
                            if (this.mainMenuModel.validateOutputFileName(fileOutputName.toString())) {
                                UserInputOption.INSERT_OUTPUT_FILE_NAME.setInputState();
                                userInputOption = UserInputOption.INSERT_CONVERSION_TYPE_NAME;
                            }
                            else {
                                  this.sendMessage("[201]Incorrect output file name! Example: C:\\\\Dokumenty\\\\Pliki\\\\plikwej.txt Click Enter to continue.");
                                  this.input.readLine();
                            }
                        }
                        break;

                    // When user is asked for conversion type name
                    case INSERT_CONVERSION_TYPE_NAME:
                        while (UserInputOption.INSERT_CONVERSION_TYPE_NAME.getInputState() == false) {
                            conversionType.setLength(0);
                            output.println("[300]Choose conversion type (either 'm2l' or 'l2m'): ");
                            conversionType.append(this.input.readLine());
                            if (this.mainMenuModel.validateConversionType(conversionType.toString())) {
                                UserInputOption.INSERT_CONVERSION_TYPE_NAME.setInputState();
                                userInputOption = UserInputOption.EXIT;
                            } 
                            else {
                                this.sendMessage("[301]Incorrect conversion type! Hint: Possible conversion types: m2l (Morse to Latin) or l2m (Latin to Morse) Click Enter to continue.");
                                this.input.readLine();
                            }
                        }
                        break;

                    case EXIT:
                        break;

                    default:
                        break;
                }

            }

            // Create converter object
            this.converterModel = new ConverterModel(
                    fileInputName.toString(), fileOutputName.toString(), conversionType.toString());

            // Perform conversion
            try {
                this.converterModel.convert();
            } catch (IncorrectConversionTypeException e) {
                output.println(e.getMessage());
            }

            // Inform the user about the conversion end
            this.sendMessage("[400]Conversion ended succesfully! Application is turning off.");
        } catch (IOException e) {
            throw new IOException();
        }
    }
    
    /**
     * Help command to inform the user about possible commands used in the application
     */
    private void helpCommand() {
        this.sendMessage("[000]Commands: START (enters the conversion mode), HELP (shows the list of commands), EXIT (quits the application)");
    }
    
    /**
     * Sends message to the client using defined output buffer and flushing it later
     * @param toSend message to the client
     */
    private void sendMessage(String toSend) {
        output.println(toSend);
        output.flush();
    }
}
