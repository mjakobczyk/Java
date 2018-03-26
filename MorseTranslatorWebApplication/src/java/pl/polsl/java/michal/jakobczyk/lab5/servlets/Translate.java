package pl.polsl.java.michal.jakobczyk.lab5.servlets;

import pl.polsl.java.michal.jakobczyk.lab5.model.*;
import pl.polsl.java.michal.jakobczyk.lab5.exception.*;
import pl.polsl.java.michal.jakobczyk.lab5.database.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that controls the translation process
 *
 * @author Michał Jakóbczyk
 * @version 5.0
 */
public class Translate extends HttpServlet {

     /**
     * Main menu model object that validates user input
     */
    private MainMenuModel mainMenuModel;
    
    /**
     * Converter model object that performs a conversion process
     */
    private ConverterModel converterModel;
    
    /**
     * Create connection variable
     */
    private Connection connection = null;
    
     /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Reading parameters from web-form
            StringBuilder fileInputName = new StringBuilder();
            StringBuilder fileOutputName = new StringBuilder();
            StringBuilder conversionType = new StringBuilder();

            fileInputName.setLength(0);
            fileInputName.append(request.getParameter("inputFile"));

            fileOutputName.setLength(0);
            fileOutputName.append(request.getParameter("outputFile"));

            conversionType.setLength(0);
            conversionType.append(request.getParameter("conversionType"));

            // Valite input file name correctness
            if (!(this.mainMenuModel.validateInputFileName(fileInputName.toString()))) {
                String message = "Incorrect input file name! Cannot find the file!";
                this.processException(request, response, message);
                return;
            }

            // Validate output file name correctness
            if (!(this.mainMenuModel.validateOutputFileName(fileOutputName.toString()))) {
                String message = "Incorrect output file name! Cannot find the file!";
                this.processException(request, response, message);
                return;
            }
            
            // Validate if files are the same
            if (fileInputName.toString().equals(fileOutputName.toString())) {
                String message = "The name of input and output files can not be the same!";
                this.processException(request, response, message);
                return;
            }

            // Validate conversion type correctness
            if (!(this.mainMenuModel.validateConversionType(conversionType.toString()))) {
                String message = "Incorrect conversion type! Not supported by application!";
                this.processException(request, response, message);
                return;
            }

            // Set parameters in the converter model
            this.converterModel.setParameters(fileInputName.toString(), fileOutputName.toString(), conversionType.toString());
            
            // Perform the conversion
            this.converterModel.convert();

            // Get servlet context from the request
            ServletContext context = request.getServletContext();
            
            // Get the http session from the request
            HttpSession session = request.getSession(true);

            // Try-catch construciton if the connection with database goes wrong
            try {
                
                // Connect with databse using url, user and password from the XML file
                this.connection = Connector.getConnection();
                
                // Check if the connection with database is possible
                if (connection == null) {
                    String message = "<h1>First start any translation!</h1>";
                    this.processException(request, response, message);
                    return;
                }
                
                // Create the statement to add the data to the database
                Statement statement = this.connection.createStatement();

                // Get the current data and time
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
                
                // Perform an action to add information to the database
                statement.executeUpdate("INSERT INTO Data VALUES ('" + fileInputName.toString() 
                        + "', '" + fileOutputName.toString() + "', '" + conversionType.toString() 
                        + "', '" + timeStamp + "')");
             
            } catch (SQLException e) {
                String message = "Exception has occured: " + e.getMessage();
                this.processException(request, response, message);
            }

            // Expecting exceptions with session, cookies and calculations
            try (PrintWriter out = response.getWriter()) {

                // Reading cookies information
                Cookie[] cookies = request.getCookies();
                Integer totalTranslationsDone = 0;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("totalTranslationsDone")) {
                            totalTranslationsDone = Integer.parseInt(cookie.getValue());
                        }
                    }
                }

                // Updating cookies information
                Integer incrementedCounter = totalTranslationsDone + 1;
                String newSum = incrementedCounter.toString();
                Cookie cookie = new Cookie("totalTranslationsDone", newSum);
                response.addCookie(cookie);

                // Showing results to user
                processRequest(request, response, incrementedCounter);

            // Session and cookies exceptions service
            } catch (ServletException e) {
                processException(request, response, e.getMessage());
            }
            
        } catch (IncorrectConversionTypeException e) {
            processException(request, response, e.getMessage());
        }
              
    }
    

    /**
     * Handles the HTTP <code>POST</code> method
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
     /**
     * Preparation of the model and connection
     */
    @Override
    public void init() {
        this.mainMenuModel = new MainMenuModel();
        this.converterModel = new ConverterModel("", "", "");
        ServletConfig config = this.getServletConfig();
        
        if (Connector.getConnection() == null) {
            if (Connector.setConnection(config.getServletContext())) {
                this.connection = Connector.getConnection();
            }
        }
    }
    
     /**
     * Showing the result of successfull calculations to app user
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @param translations amount that were performed
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Integer translations)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Preparing HTML site with results.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Results</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Conversion ended successfully!<br /></h1>");
            out.println("<hr><h1>In your history you have performed: " + (translations) + " translations<h1>");
            out.println("<a href='index.html'>Back to Main Page</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Showing the information about Exception to app user
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @param exception Exception that occured during request execution.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    protected void processException(HttpServletRequest request, HttpServletResponse response, String exception)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // Preparing HTML site with Exception error informations
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>An error occured during calculations!</h3>");
            out.println("<h3>" + exception + "<br /><h3>");
            out.println("<a href='index.html'>Back to Main Page</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Returns a short description of the servlet
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
