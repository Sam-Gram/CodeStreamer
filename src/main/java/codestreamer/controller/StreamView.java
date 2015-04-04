package codestreamer.controller;

import codestreamer.model.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Displays the requested stream to the user.
 */
@WebServlet(name = "codestreamer.controller.StreamView", urlPatterns = {"/stream/*"})
public class StreamView extends HttpServlet {

    /**
     * A Map of current streams.
     */
    HashMap<String, Stream> streams;

    /**
     * Assigns streams, to the servlet container Map containing all the streams.
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        streams = (HashMap<String, Stream>) getServletContext().getAttribute("streams");
    }

    /**
     * Ensures that the requested stream actually exists, and returns a JSP to render it if it does.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        // If the user requests a stream, with no stream ID, tell them they can't do that. Otherwise, render
        // the stream.
        if (path.equals("/")) {
            response.getOutputStream().print("Must have a stream name.");
        } else {
            // Remove the slash at the beginning of the path, so that we are left with just the stream ID.
            String streamID = path.substring(1);

            // Store the stream ID in an attribute for easy retrieval later.
            request.setAttribute("streamID", streamID);

            // Check the Map of streams, to see if the requested stream actually exists. If it does, render it.
            if (streams.containsKey(streamID)) {
                request.getRequestDispatcher("/streamview.jsp").include(request, response);
            } else {
                response.getOutputStream().print("There is no stream with the id " + streamID);
            }
        }
    }
}
