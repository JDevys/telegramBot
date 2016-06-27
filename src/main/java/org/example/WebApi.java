/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.repackaged.com.fasterxml.jackson.core.JsonGenerationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.models.Update;

/**
 *
 * @author JDevys
 */
public class WebApi extends HttpServlet {
    private static final Logger logger = Logger.getLogger(WebApi.class.getName());
    private static final String telegramUri = "https://api.telegram.org/bot211159227:AAGHIU36aeTupEhczsRjqC--PcbiMnq6zYg/sendMessage?chat_id=%1$s&text=%2$s";

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
            throws ServletException, IOException {
        logger.warning(_getRequestInfo(request));
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("It worked!");
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
        logger.warning(_getRequestInfo(request));
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Update update = new Update();
        ObjectMapper mapper = new ObjectMapper();
        
        try {
          // read from file, convert it to user class
          out.println(mapper.writeValueAsString(update));
        } catch (JsonMappingException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        out.close();
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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        try {
          // read from file, convert it to user class
          Update update = mapper.readValue(request.getReader(), Update.class);
          // display to console
          out.println(update);
          logger.warning(mapper.writeValueAsString(update));
          _sendGreeting(update.message.chat.id, update.message.from.first_name);
        } catch (JsonGenerationException e) {
          e.printStackTrace();
          logger.severe(e.getMessage());
        } catch (JsonMappingException e) {
          e.printStackTrace();
          logger.severe(e.getMessage());
        } catch (IOException e) {
          e.printStackTrace();
          logger.severe(e.getMessage());
        }
        
        out.close();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "TelegramBot request handler";
    }// </editor-fold>

    private String _getRequestInfo(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getMethod());
        builder.append(request.getQueryString());
        
        if ("POST".equals(request.getMethod())) {
            try {
                StringBuilder payloadReader = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    payloadReader.append(line);
                }
                builder.append(payloadReader.toString());
            } catch (IOException e) {
                logger.severe(e.getMessage());
                return "";
            }
        }
        
        return builder.toString();
    }

    private void _sendGreeting(int chatId, String userFirstName) {
        String greeting = String.format("Hi %1$s", userFirstName);
        String url = String.format(telegramUri, chatId, greeting);
        try {
            URL parsedUrl = new URL(url);
            URLConnection connection = parsedUrl.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            StringBuilder builder = new StringBuilder();
            String line = "";
            while((line = br.readLine()) != null) {
                builder.append(line);
            } 
            logger.info(builder.toString());
        } catch (MalformedURLException ex) {
            logger.severe(ex.getMessage());
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
    }

}
