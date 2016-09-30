package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

/**
 * Server response containing an error message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends Message {
    @Nonnull
    private final String message;

    /**
     * Default constructor required for Jackson deserialization.
     */
    @VisibleForTesting
    ErrorResponse() {
        this("");
    }

    /**
     * Create a server response indicating an error.
     *
     * @param message the error message describing the problem
     */
    public ErrorResponse(@Nonnull final String message) {
        super(MessageType.ERROR_RESPONSE);

        this.message = message;
    }

    /**
     * Retrieve the error message supplied by the server.
     *
     * @return the error message supplied by the server
     */
    @Nonnull
    public String getMessage() {
        return this.message;
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = getToStringBuilder();
        str.append("message", getMessage());
        return str.toString();
    }
}
