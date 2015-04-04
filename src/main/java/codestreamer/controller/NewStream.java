package codestreamer.controller;

import codestreamer.model.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

/**
 * A servlet that creates a new stream and adds it to the HashMap of streams.
 */
@WebServlet(name = "codestreamer.controller.NewStream", urlPatterns = {"/new"})
public class NewStream extends HttpServlet {

    /**
     * The number of characters the ID should be.
     */
    static final int ID_SIZE = 5;

    /**
     * Creates a new stream and redirects the user to that stream.
     *
     * TODO: Get the current state of the stream from the database when a new user loads a stream.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the Map of current streams.
        HashMap<String, Stream> streams = (HashMap<String, Stream>) getServletContext().getAttribute("streams");

        String streamID;
        // Generate a random string to be used for the streamID, and ensure that it's not already in the Map of streams.
        do {
            // A base 2^n number can encode n bits for each char so grab ID_SIZE * n random bits for the stream ID.
            // In our case, we are using a base 32 number, so we should grab 5 bits for every character.
            streamID = new BigInteger(ID_SIZE * 5, new SecureRandom()).toString(32);
        } while (streams.containsKey(streamID));

        // Remember the owner, so that they can update the stream
        request.getSession().setAttribute("ownerID", streamID);

        // Add the stream to the Map of streams, with the streamID as it's key
        streams.put(streamID, new Stream(request.getSession().getId()));

        // Redirect the user to the new stream
        response.sendRedirect("stream/" + streamID);
    }
}
