package controllers;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

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
 *
 * TODO: Allow for multiple streaming sessions.
 */
@WebServlet(name = "Update", urlPatterns = {"/Update"}, asyncSupported = true)
public class Update extends HttpServlet {

    /**
     * The length of time that a AsyncContext should wait before cancelling the
     * request. Currently, it's 5 minutes.
     */
    private static final long TIMEOUT = 5 * 60 * 1000;

    /**
     * A list that will hold the hanging GET requests, made asynchronous in doGet().
     * Currently, a LinkedList is used, because insertion time is constant (and we mainly just
     * insert.) This will probably change when we add the ability to handle multiple streaming
     * sessions.
     */
    private List<AsyncContext> contexts = new LinkedList<>();

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
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(TIMEOUT);
        contexts.add(asyncContext);
    }

    /**
     * Updates all asynchronous contexts (the hanging GET requests) with the data it receives.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the change parameter. The change parameter should be a JSON object containing information
        // about what the user changed.
        String change = request.getParameter("change");

        // Loop through all the GET requests and respond to them.
        for (AsyncContext context : contexts) {
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
        this.contexts.clear();
    }
}
