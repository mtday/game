package ants.common.model.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link RegisterBotRequest} class.
 */
public class RegisterBotRequestTest {
    @Test
    public void testDefaultConstructor() {
        final RegisterBotRequest message = new RegisterBotRequest();
        assertEquals(MessageType.REGISTER_BOT_REQUEST, message.getType());
        assertEquals("", message.getUserId());
        assertEquals("", message.getBotName());
        assertEquals("", message.getBotVersion());
    }

    @Test
    public void testParameterConstructor() {
        final RegisterBotRequest message = new RegisterBotRequest("user", "bot", "0.0");
        assertEquals(MessageType.REGISTER_BOT_REQUEST, message.getType());
        assertEquals("user", message.getUserId());
        assertEquals("bot", message.getBotName());
        assertEquals("0.0", message.getBotVersion());
    }

    @Test
    public void testToString() {
        assertEquals(
                "RegisterBotRequest[type=REGISTER_BOT_REQUEST,userId=user,botName=bot,botVersion=0.0]",
                new RegisterBotRequest("user", "bot", "0.0").toString());
    }
}
