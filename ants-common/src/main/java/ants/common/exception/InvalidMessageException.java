package ants.common.exception;

import javax.annotation.Nonnull;

/**
 * Represents an invalid message received by the server.
 */
public class InvalidMessageException extends Exception {
    private static final long serialVersionUID = 74380914310L;

    /**
     * Parameter constructor.
     *
     * @param message an error message describing how the provided message object was invalid
     */
    public InvalidMessageException(@Nonnull final String message) {
        super(message);
    }
}
