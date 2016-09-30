package ants.common.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ants.common.exception.InvalidMessageException;
import ants.common.model.message.ErrorResponse;
import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 * Perform testing on the {@link MessageParser} class.
 */
public class MessageParserTest {
    @Test
    public void testConstructor() {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        assertNotNull(messageParser.getObjectMapper());
    }

    @Test(expected = InvalidMessageException.class)
    public void testEmptyString() throws InvalidMessageException {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        messageParser.parseMessage("");
    }

    @Test(expected = InvalidMessageException.class)
    public void testInvalidType() throws InvalidMessageException {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        messageParser.parseMessage("abcd");
    }

    @Test(expected = InvalidMessageException.class)
    public void testNoColon() throws InvalidMessageException {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        messageParser.parseMessage(MessageType.ERROR_RESPONSE.name());
    }

    @Test(expected = InvalidMessageException.class)
    public void testInvalidTypeWithJson() throws InvalidMessageException {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        messageParser.parseMessage("TYPE:{ }");
    }

    @Test(expected = InvalidMessageException.class)
    public void testInvalidJsonMessage() throws InvalidMessageException {
        final MessageParser messageParser = new MessageParser(new ObjectMapper());
        messageParser.parseMessage(String.format("%s:%s", MessageType.ERROR_RESPONSE.name(), "abcd"));
    }

    @Test
    public void testValid() throws InvalidMessageException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ErrorResponse errorResponse = new ErrorResponse("message");

        final MessageParser messageParser = new MessageParser(objectMapper);
        final Pair<MessageType, Message> pair = messageParser.parseMessage(
                String.format("%s:%s", MessageType.ERROR_RESPONSE.name(),
                        objectMapper.writeValueAsString(errorResponse)));

        assertNotNull(pair);
        assertEquals(MessageType.ERROR_RESPONSE, pair.getKey());
        assertTrue(pair.getValue() instanceof ErrorResponse);
        assertEquals("message", ((ErrorResponse) pair.getValue()).getMessage());
    }
}
