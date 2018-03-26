package pl.polsl.java.michal.jakobczyk.lab3.network;

import pl.polsl.java.michal.jakobczyk.lab3.view.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Controller class of the TCP client
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class ClientController {
    
    /** 
     * Client socket for communication with server
     */
    private final Socket clientSocket;
    
    /** 
     * Port number for communication with server
     */
    private static int port;
    
    /** 
     * Server addres
     */
    private final InetAddress hostAddress;
    
    /**
     * Client view class for communication with the user
     */
    private final ClientView clientView;
    
    /**
     * Buffered input character stream
     */    
    private final BufferedReader input;
    
    /**
     * Formatted output character stream
     */
    private final PrintWriter output;
    
    /** 
     * Default constructor of the TCP client 
     * Properties file provides server host and port
     * @throws UnknownHostException when host is invalid
     * @throws IOException when i/o stream is corrupted
     */
    public ClientController() 
            throws UnknownHostException, IOException {
        // Declare a file
        File file = new File("properties.txt");
        
        // Use scanner to read the data from the file
        Scanner scanner = new Scanner(file);
        ClientController.port = Integer.parseInt(scanner.nextLine());
        String serverHost = scanner.nextLine();
        this.hostAddress = InetAddress.getByName(serverHost);
        this.clientSocket = new Socket(serverHost, port);
        
        // Close the scanner after the successful read
        scanner.close();    

        // Initialize input stream, output stream and view
        this.input = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
        this.output = new PrintWriter (new OutputStreamWriter(clientSocket.getOutputStream()), true);
        this.clientView = new ClientView();
        System.out.println("Connected with:" + clientSocket.getInetAddress() );  
    }
      
    /**
     * Communicates with server through input and output streams
     * @throws IOException Input/Output exception
     */
    public void connectToServer() 
            throws IOException {
        clientView.getAnswer(input);
        
        while(!clientSocket.isClosed()) { 
            clientView.askServer(output);
            clientView.getAnswer(input);
        }
    }
    
    /**
     * Closes input stream, output stream and socket
     * @throws IOException Input/Output exception
     */
    public void disconnectFromServer() 
            throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }
    
}
