package codestreamer.model;

import javax.servlet.AsyncContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single Stream. A stream is represented on the website as anything with the URL <base>/stream/*
 */
public class Stream {
    /**
     * A list of GET requests that are waiting for updates. The viewers of the stream.
     */
    private List<AsyncContext> requests = new LinkedList<>();

    /**
     * The session id of the browser that created the stream. This browser is allowed to make POSTs to the stream.
     */
    private String owner;

    /**
     * A list of all the actions the host has performed on this stream.
     */
    private List<String> actionsList = new ArrayList<>();

    /**
     * Constructs a Stream attached to an owner.
     * @param owner
     */
    public Stream(String owner) {
        this.owner = owner;
    }

    public List<AsyncContext> getRequests() {
        return requests;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getActionsList() {
        return actionsList;
    }

    /**
     * Gets a list of all the actions in a JSON array String.
     * @param actionNum The action to start at.
     * @return A String containing a JSON array of all the actions.
     */
    public String getActionsFrom(int actionNum) {
        StringBuilder sb = new StringBuilder();
        String action;
        for (int i = actionNum; i < actionsList.size(); i++) {
            action = actionsList.get(i);
            sb.append(action);

            // Only append the comma if it's not the last element in the array
            if (i + 1 != actionsList.size())
                sb.append(',');
        }

        // Wrap the list in brackets to make it a JSON array.
        sb.insert(0, '[');
        sb.insert(sb.length(), ']');

        return sb.toString();
    }
}
