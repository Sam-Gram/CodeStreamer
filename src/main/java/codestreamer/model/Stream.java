package codestreamer.model;

import javax.servlet.AsyncContext;
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
}
