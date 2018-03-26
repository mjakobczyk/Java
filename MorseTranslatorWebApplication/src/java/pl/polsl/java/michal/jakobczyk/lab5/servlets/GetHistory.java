package pl.polsl.java.michal.jakobczyk.lab5.servlets;

import pl.polsl.java.michal.jakobczyk.lab5.database.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that presents information about the performed translations
 *
 * @author Michał Jakóbczyk
 * @version 5.0
 */
public class GetHistory extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            // Preparing HTML site to be shown to user
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetHistory</title>");
            out.println("</head>");
            out.println("<body>");
            
            //Printing session history
            out.println("<hr><h1>Results got in this session:</h1>");
            
            // Get servlet context from the request
            ServletContext context = request.getServletContext();
            
            // Get the http session from the request
            HttpSession session = request.getSession(true);

            // Try-catch construciton if the connection with database goes wrong
            try {
                        
                // Get the connection from the Connector class
                Connection connection = Connector.getConnection();

                // Check if the connection with database is possible
                if (connection == null) {
                    String message = "<h1>You need to first translate something!</h1>";
                    out.println(message);
                    return;
                }
                
                // Create the statement to add the data to the database
                Statement statement = connection.createStatement();
                
                // Send a request to the database
                ResultSet rs = statement.executeQuery("SELECT * FROM Data");
                
                // Show resulsts to the user
                out.println("<h1>XYZ. " + "INPUT --- " + "OUTPUT --- " + "TYPE --- " + "DATE AND TIME</h1>");
                
                // Define a counter to list every row of the database
                int counter = 0;
                
                // Iterate through the result set as long as it contains stuff
                while (rs.next()) {
                    if (++counter < 10) {
                        out.println("<h1>" + (counter) + ". : ");
                    }
                    else {
                        out.println("<h1>" + (counter) + ".: ");
                    }
                    out.println(rs.getString("inp") + " --- ");
                    out.println(rs.getString("otp") + "  --- ");
                    out.println(rs.getString("type")  + " --- ");
                    out.println(rs.getString("dnt"));
                    out.println("</h1>");
                    out.println("\n");
                }
                
                out.println("</h1>");
                
                // Close the result set after reading all information
                rs.close();
                
            }
            catch (SQLException e) {
                String message = "Exception has occured: " + e.getMessage();
            }
            
            // Getting cookies information
            Cookie[] cookies = request.getCookies();
            Integer totalTranslationsDone = 0;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("totalTranslationsDone")) {
                    totalTranslationsDone = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
            
            // Printing information stored in cookie
            out.println("<hr><h1>In your history you have performed: " + (totalTranslationsDone) + " translations<h1>");
            out.println("<a href='index.html'>Back to Main Page</a>");
            out.println("</body>");
            out.println("</html>");
        }
}

    /**
     * Handles the HTTP <code>GET</code> method
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Show historical information.
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Redirecting to GET method.
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
        public String getServletInfo() {
            return "Short description";
    }
}
