package ants.common.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link InvalidMessageException} class.
 */
public class InvalidMessageExceptionTest {
    @Test
    public void testConstructor() {
        final InvalidMessageException ex = new InvalidMessageException("message");
        assertEquals("message", ex.getMessage());
    }
}
