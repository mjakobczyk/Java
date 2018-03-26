package pl.polsl.java.michal.jakobczyk.lab3.network;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * The entry point of the client application
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class MorseTranslatorTCPClient {
    
    /**
     * Initializes client controller, connects to the server
     * and disconnets before the shutdown of the application
     * @param args the command line arguments
     */   
    public static void main(String[] args) {  
        
        try {
            ClientController clientController = new ClientController();
            clientController.connectToServer();
            clientController.disconnectFromServer();
        }
        catch (UnknownHostException e) {
            System.err.println("Unknown server!");
        } 
        catch (IOException e) {
            System.err.println("Could not connect to the server!");
        }  
    }
}
