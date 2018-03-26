package pl.polsl.java.michal.jakobczyk.lab3.network;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import pl.polsl.java.michal.jakobczyk.lab3.exception.IncorrectConversionTypeException;
import pl.polsl.java.michal.jakobczyk.lab3.model.MainMenuModel;

/**
 * Class for port, socket and service initialization
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class ServerController {
    
    /**
     * Port number
     */
    final private int port;
    
    /**
     * field represents the socket waiting for client connections
     */
    final private ServerSocket serverSocket;  
    
     /**
     * Main menu model object that validates user input
     */
    private final MainMenuModel mainMenuModel;
    
    /**
     * Constructor which create new socket.
     * @throws IOException Input/Output exception
     */
    public ServerController() 
            throws IOException {
         // Declare a file
        File file = new File("properties.txt");
        
        // Use scanner to read the data from the file
        Scanner scanner = new Scanner(file);
        this.port = Integer.parseInt(scanner.nextLine());
        
        serverSocket = new ServerSocket(port);
        System.out.println("Server started!");
        this.mainMenuModel = new MainMenuModel();
    }
        
    /**
     * Method creating new AnalyzerService and calling its realize method
     * @throws IOException Input/Output exception
     * @throws IncorrectConversionTypeException when conversion type is invalid
     */
    public void connectToService() 
            throws IOException, IncorrectConversionTypeException
    {
        while(true) {
            try {
                Socket socket = this.serverSocket.accept();
                SingleService singleService = new SingleService(socket, this.getMainMenuModel());
                singleService.realize();
            }
            catch(IOException e){
                serverSocket.close();
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Getter method for server socket
     * @return socket
     */
    public ServerSocket getSocket() {
        return serverSocket;
    }
    
    /**
     * Getter method for main menu model
     * @return main menu model
     */
    public MainMenuModel getMainMenuModel() {
        return this.mainMenuModel;
    }
    
}
