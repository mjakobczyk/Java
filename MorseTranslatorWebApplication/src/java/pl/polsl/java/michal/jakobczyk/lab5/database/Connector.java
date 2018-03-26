package pl.polsl.java.michal.jakobczyk.lab5.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 * Class responsible for setting up and sharing the
 * connection with the database
 *
 * @author MichałJakóbczyk
 * @version 5.0
 */
public class Connector {
    
    /**
     * Connection object that is initially null
     */
    private static Connection connection = null;
    
    /**
     * Creates the connection to the database
     * 
     * @param context is the server context with its parameters
     * @return true if succeeded, null if unable to connect
     */
    public static boolean setConnection(ServletContext context) {
        if (connection == null) {
            try {
                // Load the database driver
                Class.forName(context.getInitParameter("driver"));
                
                // Connect with the database
                connection = DriverManager.getConnection(context.getInitParameter("url"),
                        context.getInitParameter("user"), context.getInitParameter("password"));
            } catch (ClassNotFoundException | SQLException | NullPointerException e) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Getter for connection object
     * 
     * @return the connection or null if not initialized
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Disconnects from the database
     */
    public static void disconnect() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
