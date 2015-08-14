package csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author thomas
 */
@WebServlet(name = "CsvUpload", urlPatterns = {"/CsvUpload"})
@MultipartConfig
public class CsvUpload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try {
            // get file from request
            Part filePart = request.getPart("file");
            InputStream fileStream = filePart.getInputStream();
            
            // create instance of Model
            Csv2Database c2d = new Csv2Database();
            
            // parse csv
            ArrayList<Person> parsedPersons = c2d.readCsv(fileStream);

            // close file resource
            fileStream.close();
            
            // add persons to db
            ArrayList<Person> addedPersons = c2d.addPersons(parsedPersons);
            
            // set attribute for JSP
            int parsedCount = parsedPersons.size();
            int addedCount = addedPersons.size();
            request.setAttribute("parsedCount", parsedCount);
            request.setAttribute("addedCount", addedCount);
            request.setAttribute("addedPersons", addedPersons);
            
            // forward to JSP
            RequestDispatcher rd = request.getRequestDispatcher("csvResult.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<p>[ERROR] " + e.getMessage() + "</p>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    /*
    private void test(HttpServletResponse response, InputStream is) throws Exception {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // create file reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        // read stream
        String line;
        int count = 1;
        while ((line = reader.readLine()) != null) {
            out.println("<p>" + count + ": " + line + "</p>\n");
            ++count;
        }
        
    }*/
    
    ///////////////////////////////////////////////////////////////////////////
    // helper methods to print HTML content
    ///////////////////////////////////////////////////////////////////////////
    private void printHeader(PrintWriter out, String title, String css) {
        String header = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "<title>" +
                        title +
                        "</title>\n" +
                        css +
                        "</head>\n" +
                        "<body>\n";
        out.println(header);
    }

    private void printFooter(PrintWriter out) {
        out.println("\n</body>\n</html>");
    }
}
