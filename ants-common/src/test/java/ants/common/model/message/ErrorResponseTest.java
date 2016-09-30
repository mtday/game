package ants.common.model.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ErrorResponse} class.
 */
public class ErrorResponseTest {
    @Test
    public void testDefaultConstructor() {
        final ErrorResponse message = new ErrorResponse();
        assertEquals("", message.getMessage());
    }

    @Test
    public void testParameterConstructor() {
        final ErrorResponse message = new ErrorResponse("Error message goes here");
        assertEquals("Error message goes here", message.getMessage());
    }

    @Test
    public void testToString() {
        assertEquals("ErrorResponse[message=Error message]", new ErrorResponse("Error message").toString());
    }
}
