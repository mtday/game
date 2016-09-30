package ants.common.io;

import ants.common.exception.InvalidMessageException;
import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.annotation.Nonnull;

/**
 * Responsible for parsing messages received from clients.
 */
public class MessageParser {
    private static final Logger LOG = LoggerFactory.getLogger(MessageParser.class);

    @Nonnull
    private final ObjectMapper objectMapper;

    /**
     * Create a message parser to handle client messages.
     *
     * @param objectMapper the Jackson {@link ObjectMapper} used to parse client json data
     */
    public MessageParser(@Nonnull final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve the internal Jackson {@link ObjectMapper} used to parse client json.
     *
     * @return the internal Jackson {@link ObjectMapper} used to parse client json
     */
    @Nonnull
    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    /**
     * Attempt to parse the message information.
     *
     * @param messageData the message data text to parse
     * @return a {@link Pair} containing the {@link MessageType} and the {@link Message}
     * @throws InvalidMessageException if the received message is invalid
     */
    @Nonnull
    public Pair<MessageType, Message> parseMessage(@Nonnull final String messageData) throws InvalidMessageException {
        final int firstColon = messageData.indexOf(":");
        if (firstColon < 0) {
            // There was no space separator, treat this message as being invalid.
            throw new InvalidMessageException(
                    "Invalid message format, expecting message type followed by message json");
        }

        final String messageType = messageData.substring(0, firstColon);
        final String jsonData = messageData.substring(firstColon + 1);

        try {
            final MessageType parsedMessageType = MessageType.valueOf(messageType);
            final Message message = getObjectMapper().readValue(jsonData, parsedMessageType.getMessageClass());

            return Pair.of(parsedMessageType, message);
        } catch (final IOException badJsonObject) {
            throw new InvalidMessageException("Failed to parse message json: " + badJsonObject.getMessage());
        } catch (final IllegalArgumentException badMessageType) {
            throw new InvalidMessageException("Unrecognized message type: " + messageType);
        }
    }
}
