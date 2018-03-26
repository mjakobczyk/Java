package pl.polsl.java.michal.jakobczyk.lab3.network;

import java.io.*;
import pl.polsl.java.michal.jakobczyk.lab3.exception.*;

/**
 * The entry point of the server application
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class MorseTranslatorTCPServer {
    
     /**
     * Initializes server controller and enables connection
     * @param args the command line arguments
     * @throws IncorrectConversionTypeException when conversion type is invalid
     */ 
    public static void main(String[] args) 
            throws IncorrectConversionTypeException {

        ServerController serverController = null;
        
        try {
            serverController = new ServerController();
            serverController.connectToService();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } 
        finally {
            if (serverController != null){
                if (serverController.getSocket() != null) {
                    try {
                        serverController.getSocket().close();
                    } 
                    catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }  
    }  

}
