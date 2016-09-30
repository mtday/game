package ants.server.io;

import ants.common.exception.InvalidMessageException;
import ants.common.io.MessageParser;
import ants.common.model.message.ErrorResponse;
import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import ants.server.action.Action;
import ants.server.action.RegisterBotAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;

/**
 * Responsible for processing messages received from clients.
 */
public class AntsReceiveListener extends AbstractReceiveListener {
    private static final Logger LOG = LoggerFactory.getLogger(AntsReceiveListener.class);

    private final Collection<Action> actions;
    private final ObjectMapper objectMapper;
    private final MessageParser messageParser;

    /**
     * Default constructor.
     */
    public AntsReceiveListener() {
        this.objectMapper = new ObjectMapper();
        this.messageParser = new MessageParser(this.objectMapper);

        this.actions = new ArrayList<>();
        this.actions.add(new RegisterBotAction(this.objectMapper));
    }

    /**
     * Retrieve the internal {@link Collection} of {@link Action actions} used to process client messages.
     *
     * @return the internal {@link Collection} of {@link Action actions} used to process client messages
     */
    @Nonnull
    protected Collection<Action> getActions() {
        return this.actions;
    }

    /**
     * Retrieve the internal Jackson {@link ObjectMapper} used to write json back to the client.
     *
     * @return the internal Jackson {@link ObjectMapper} used to write json back to the client
     */
    @Nonnull
    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    /**
     * Retrieve the internal {@link MessageParser} used to parse client message data.
     *
     * @return the internal {@link MessageParser} used to parse client message data
     */
    @Nonnull
    protected MessageParser getMessageParser() {
        return this.messageParser;
    }

    @Override
    protected void onFullTextMessage(
            @Nonnull final WebSocketChannel channel, @Nonnull final BufferedTextMessage bufferedTextMessage)
            throws JsonProcessingException {
        try {
            final Pair<MessageType, Message> message = getMessageParser().parseMessage(bufferedTextMessage.getData());

            LOG.info("Received message from peer {}: {}:{}", channel.getPeerAddress(), message.getKey(),
                    message.getValue());

            boolean handled = false;
            for (final Action action : getActions()) {
                if (action.handles(message.getKey())) {
                    action.process(channel, message);
                    handled = true;
                }
            }

            if (!handled) {
                LOG.error("Unrecognized message type: {}", message.getKey());
                sendError("Unrecognized message type: " + message.getKey(), channel);
            }
        } catch (final InvalidMessageException invalidMessage) {
            sendError(invalidMessage.getMessage(), channel);
        } catch (final IOException ioException) {
            sendError("Failed to process message: " + ioException.getMessage(), channel);
        }
    }

    private void sendError(@Nonnull final String message, @Nonnull final WebSocketChannel channel)
            throws JsonProcessingException {
        WebSockets.sendText(
                String.format("%s:%s", MessageType.ERROR_RESPONSE,
                        getObjectMapper().writeValueAsString(new ErrorResponse(message))), channel, null);
    }
}
