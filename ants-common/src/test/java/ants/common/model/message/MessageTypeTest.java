package ants.common.model.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link MessageType}.
 */
public class MessageTypeTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(MessageType.values().length > 0);
        assertEquals(MessageType.ERROR_RESPONSE, MessageType.valueOf(MessageType.ERROR_RESPONSE.name()));
    }
}
