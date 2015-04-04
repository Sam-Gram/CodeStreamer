package codestreamer;

/**
 * A simple Exception class whose name explains the problem.
 * It's simply for more readable code.
 */
public class StreamIDNotFoundException extends Exception {
    public StreamIDNotFoundException() {
        // intentionally empty
    }

    public StreamIDNotFoundException(String message) {
        super(message);
    }
}
