/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byui.cs306.firstproject;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sam
 */
@WebServlet(name = "LoadPosts", urlPatterns = {"/LoadPosts"})
public class LoadPosts extends HttpServlet {

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
        List<Post> posts = new ArrayList<Post>();
        
        try
        {
            String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR");
            String where = dataDirectory != null ? dataDirectory + "posts.txt" : "posts.txt";
            File myFile = new File(where);
            System.out.println("File exits: " + myFile.exists());
            if (!myFile.exists())
            {
                System.out.println(myFile.createNewFile());
            }
            InputStream file = new FileInputStream(myFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            String strLine;
            System.out.println("Loading Posts");
            while ((strLine = br.readLine()) != null)
            {
                String[] temp = strLine.split(":");
                System.out.println(temp);
                if (temp.length == 2)
                {
                    posts.add(new Post(temp[0], temp[1]));
                }
            }
            br.close();
            file.close();
        }
        catch (IOException e)
        {
            
        }
        
        request.setAttribute("posts", Lists.reverse(posts));
        
        
        request.getRequestDispatcher("discussion.jsp").forward(request, response);
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
