/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byui.cs306.firstproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sam
 */
@WebServlet(name = "AddDiscussionPost", urlPatterns = {"/AddDiscussionPost"})
public class AddDiscussionPost extends HttpServlet {

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
        
        String author = (String) request.getParameter("author");
        String comment = (String) request.getParameter("comment");
        try
        {
            String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR");
            String where = dataDirectory != null ? dataDirectory + "posts.txt": "posts.txt";
            File dir = new File(where);
            if (!dir.exists())
            {
                dir.createNewFile();
            }
  

            FileWriter fstream = new FileWriter(dir, true);
            BufferedWriter out = new BufferedWriter(fstream);

            out.write(author + ":" + comment);
            out.newLine();

            //close buffer writer
            out.close();
            
            System.out.println("Post Added " + author + " " + comment);
        }
        catch (Exception e)
        {
            System.out.println("#######################################");
            System.out.println("Error in adding discussion post");
            System.out.println(e.getMessage());
            System.out.println("#######################################");
        }
       
       request.getRequestDispatcher("LoadPosts").forward(request, response);
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

}
