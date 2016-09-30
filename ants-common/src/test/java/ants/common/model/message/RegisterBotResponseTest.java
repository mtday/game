package ants.common.model.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link RegisterBotResponse} class.
 */
public class RegisterBotResponseTest {
    @Test
    public void testDefaultConstructor() {
        final RegisterBotResponse message = new RegisterBotResponse();
        assertFalse(message.isRegistered());
    }

    @Test
    public void testParameterConstructor() {
        final RegisterBotResponse message = new RegisterBotResponse(true);
        assertTrue(message.isRegistered());
    }

    @Test
    public void testToString() {
        assertEquals("RegisterBotResponse[registered=true]", new RegisterBotResponse(true).toString());
    }
}
