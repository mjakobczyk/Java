package pl.polsl.java.michal.jakobczyk.lab3.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * View class of the TCP client
 * Communicates with the user through the console
 * @author Michał Jakóbczyk
 * @version 3.0
 */
public class ClientView {
    
      /**
      * Reads the data from the user
      * @return data sent by user
      */
     private String readData(){
         Scanner scanner = new Scanner(System.in);       
         String inputData = scanner.nextLine();
         return inputData;
     }
     
     /**
      * Method print answer from server
      * @param input Stream sending information
      * @throws IOException Input/Output exception 
      */
     public void getAnswer(BufferedReader input) 
             throws IOException {
        
        String response = input.readLine();
        System.out.println(response);   
     }
     
     /**
      * Sends information to the server
      * @param output Stream which recive the information
      * @throws IOException Input/Output exception
      */
     public void askServer(PrintWriter output) 
             throws IOException {
        String text = this.readData();
        output.println(text);
        output.flush();
     }
}
