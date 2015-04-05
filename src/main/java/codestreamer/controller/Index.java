package codestreamer.controller;

import codestreamer.model.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A simple servlet that will act as the index.
 */
@WebServlet(name = "Index", urlPatterns = {"/Index"})
public class Index extends HttpServlet {

    /**
     * Gets the list of current streams, and passes it to "index.jsp".
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, List<Stream>> streams = (HashMap<String, List<Stream>>) getServletContext().getAttribute("streams");
        Set<String> keys = streams.keySet();
        request.setAttribute("keys", new ArrayList<String>(keys));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
