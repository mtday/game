package ants.common.model.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Message} class.
 */
public class MessageTest {
    @Test
    public void testDefaultConstructor() {
        final Message message = new Message();
        assertEquals(MessageType.ERROR_RESPONSE, message.getType());
    }

    @Test
    public void testParameterConstructor() {
        final Message message = new Message(MessageType.ERROR_RESPONSE);
        assertEquals(MessageType.ERROR_RESPONSE, message.getType());
    }

    @Test
    public void testToString() {
        assertEquals("Message[type=ERROR_RESPONSE]", new Message(MessageType.ERROR_RESPONSE).toString());
    }
}
