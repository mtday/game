package ants.server.action;

import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.websockets.core.WebSocketChannel;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

import javax.annotation.Nonnull;

/**
 * The base class for our action processors.
 */
public abstract class Action {
    @Nonnull
    private final ObjectMapper objectMapper;

    /**
     * Create an action instance used to process messages.
     *
     * @param objectMapper the {@link ObjectMapper} used to process JSON data
     */
    public Action(@Nonnull final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve an instance of the {@link ObjectMapper} used to process JSON data.
     *
     * @return an instance of the {@link ObjectMapper} used to process JSON data
     */
    @Nonnull
    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    /**
     * Determines whether this action class is responsible for handling the specified message type.
     *
     * @param messageType the message type to check
     * @return whether this action is willing to handle messages of the specified type
     */
    public abstract boolean handles(@Nonnull MessageType messageType);

    /**
     * Process the provided command from the specified web socket channel.
     *
     * @param channel the web socket channel from which the message was received
     * @param message a {@link Pair} containing the {@link MessageType} and {@link Message} to be processed
     * @throws IOException if there is a problem writing response data to the web socket channel
     */
    public abstract void process(@Nonnull WebSocketChannel channel, @Nonnull Pair<MessageType, Message> message)
            throws IOException;
}
