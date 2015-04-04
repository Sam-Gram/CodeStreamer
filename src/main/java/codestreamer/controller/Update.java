package codestreamer.controller;

import codestreamer.model.Stream;
import codestreamer.StreamIDNotFoundException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Handles requests destined for '/Update'.
 *
 * There are two types of clients: a host and a viewer.
 *
 * Host:
 *      A type of client that can send POST requests to this servlet. The host is the one
 *      that can type on the webpage, while viewers watch. When the host types something,
 *      it is sent to this servlet. This servlet then sees who is waiting for updates,
 *      and pushes the updates out to all the viewers.
 *
 * Viewer:
 *      A type of client that can send GET requests to this servlet. The requests are sent
 *      using Server-Sent Events, so that the client doesn't have to poll for updates.
 *      The client makes a new EventSource, with this servlet as the event, and makes a GET
 *      request. The server will not fulfill the GET request until the host POSTS an action.
 *      Once the POST occurs, the GET request is fulfilled for the viewer, and the viewer
 *      immediately makes a new GET requests, and the above is repeated.
 */
@WebServlet(name = "Update", urlPatterns = {"/Update"}, asyncSupported = true)
public class Update extends HttpServlet {

    /**
     * The length of time that a AsyncContext should wait before cancelling the
     * request. Currently, it's 5 minutes.
     */
    private static final long TIMEOUT = 5 * 60 * 1000;

    /**
     * A map containing different streams. Each stream is a list of that will hold the hanging GET requests,
     * made asynchronous in doGet(). Currently, a LinkedList is used, because insertion time is constant (and we
     * mainly just insert.)
     */
    private HashMap<String, Stream> streams;

    /**
     * Gets the Map of streams upon initialization.
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        streams = (HashMap<String, Stream>) getServletContext().getAttribute("streams");
    }

    /**
     * Parses the "referer" header and gets the stream ID. It does this by getting the referer header
     * @param request A request containing a "referer" header.
     * @return The ID of the stream.
     * @throws StreamIDNotFoundException Thrown when the function cannot find stream's ID. This should only happen
     * when a request with no referer or a referer not containing a stream ID is received.
     */
    private String getStreamID(HttpServletRequest request) throws StreamIDNotFoundException {
        String referer = request.getHeader("referer");

        // If there is no referer header, we cannot get the ID.
        if (referer == null)
            throw new StreamIDNotFoundException("No referer header in request.");

        // Split the URL by '/'.
        StringTokenizer st = new StringTokenizer(referer, "/");
        String token;

        // Keep going to the next token until the token is "stream". The token after "stream" will be the ID.
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (token.equals("stream"))
                return st.nextToken();
        }

        // Unable to find the ID, probably because the "stream" token was not found.
        throw new StreamIDNotFoundException("Cannot get stream id from the URL" + referer);
    }

    /**
     * Takes the request, and asynchronously stores it in contexts for later processing.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String streamId = getStreamID(request);
            AsyncContext asyncContext = request.startAsync();
            asyncContext.setTimeout(TIMEOUT);
            streams.get(streamId).getRequests().add(asyncContext);
        } catch (StreamIDNotFoundException e) {
            response.getOutputStream().print("Cannot call /Update from a non-stream.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates all asynchronous contexts (the hanging GET requests) with the data it receives.
     *
     * TODO: Check owner with Facebook, not session ID.
     * TODO: Have this also update a table in the database, so that the database has a complete version of the file.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Attempt to get the stream ID.
            String streamID = getStreamID(request);

            // If the ID is not in the streams HashMap, then the stream does not exist.
            if (!streams.containsKey(streamID)) {
                response.getOutputStream().print("Invalid id: " + streamID);
                return;
            }

            // Get the change parameter. The change parameter should be a JSON object containing information
            // about what the user changed.
            String change = request.getParameter("change");

            // Grab the Stream object representing the stream this POST is for.
            Stream stream = streams.get(streamID);

            // If the user who is making the POST is not the owner, they are not allowed to make this request.
            if (stream.getOwner() != request.getSession().getId()) {
                response.getOutputStream().print("Must own the stream to POST to it.");
                return;
            }

            // Loop through all the GET requests and respond to them.
            for (AsyncContext context : stream.getRequests()) {
                HttpServletResponse aResponse = (HttpServletResponse) context.getResponse();

                aResponse.setContentType("text/event-stream");
                aResponse.setCharacterEncoding("UTF-8");
                aResponse.setHeader("Cache-Control", "no-cache");

                PrintWriter writer = aResponse.getWriter();
                writer.write("retry: 0\n");  // tell the viewer to immediately make a new request after this response
                writer.write("data: " + change + "\n\n");
                writer.flush();

                // We're done with this context now
                context.complete();
            }

            // Remove all the contexts, so that only unfulfilled requests are in it.
            stream.getRequests().clear();

        } catch (StreamIDNotFoundException e) {
            response.getOutputStream().print("Cannot call /Update from a non-stream.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
