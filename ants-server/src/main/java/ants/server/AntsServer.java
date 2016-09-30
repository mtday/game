package ants.server;

import ants.common.model.message.ErrorResponse;
import ants.common.model.message.Message;
import ants.server.action.Action;
import ants.server.action.RegisterBotAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides the entry point into the ants server.
 */
public class AntsServer {
    private static final Logger LOG = LoggerFactory.getLogger(AntsServer.class);

    /**
     * Default constructor stands up a web socket server to process ant games.
     */
    public AntsServer() {
        final Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler(Handlers.path().addPrefixPath("/ants", Handlers.websocket((exchange, channel) -> {
                    channel.getReceiveSetter().set(new AntsReceiveListener());
                    channel.resumeReceives();
                }))).build();
        server.start();
        LOG.info("AntsServer Started");
    }

    private static class AntsReceiveListener extends AbstractReceiveListener {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private final Collection<Action> actions = new ArrayList<>();

        public AntsReceiveListener() {
            this.actions.add(new RegisterBotAction(this.objectMapper));
        }

        @Override
        protected void onFullTextMessage(
                @Nonnull final WebSocketChannel channel, @Nonnull final BufferedTextMessage bufferedTextMessage)
                throws JsonProcessingException {
            final String messageData = bufferedTextMessage.getData();

            try {
                final Message message = this.objectMapper.readValue(messageData, Message.class);
                final Message realMessage =
                        this.objectMapper.readValue(messageData, message.getType().getMessageClass());
                LOG.info("Received message from peer {}: {}", channel.getPeerAddress(), realMessage);

                boolean handled = false;
                for (final Action action : this.actions) {
                    if (action.handles(message.getType())) {
                        action.process(channel, realMessage);
                        handled = true;
                    }
                }

                if (!handled) {
                    LOG.error("Unrecognized message type: {}", message.getType());
                    WebSockets.sendText(this.objectMapper.writeValueAsString(
                            new ErrorResponse("Unrecognized message type: " + message.getType())), channel, null);
                }
            } catch (final IOException ioException) {
                LOG.error("Failed to process message", ioException);
                WebSockets.sendText(this.objectMapper.writeValueAsString(
                        new ErrorResponse("Failed to process message: " + ioException.getMessage())), channel, null);
            }
        }
    }

    /**
     * The entry-point into this server process.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new AntsServer();
    }
}
