package ants.bots.example;

import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import ants.common.model.message.RegisterBotRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Nonnull;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * Provides an example bot implementation.
 */
@ClientEndpoint
public class ExampleBot {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBot.class);

    private static final String DEFAULT_END_POINT = "ws://localhost:8080/ants";

    private static final CountDownLatch LATCH = new CountDownLatch(1);

    /**
     * Initialize this bot.
     *
     * @param uri the remote Ants server to connect to and communicate with
     * @throws RuntimeException if there is a problem connecting with the server
     */
    public ExampleBot(@Nonnull final URI uri) {
        final ClientManager clientManager = ClientManager.createClient();
        try {
            clientManager.connectToServer(ExampleBotEndpoint.class, uri);
            LATCH.await();
        } catch (final DeploymentException | IOException clientFailure) {
            throw new RuntimeException("Failed to connect to Ants server", clientFailure);
        } catch (final InterruptedException interrupted) {
            LOG.warn("Interrupted, exiting");
        }
    }

    /**
     * The client end-point that will handle all the web socket messages.
     */
    @ClientEndpoint
    public static class ExampleBotEndpoint {
        private final ObjectMapper objectMapper = new ObjectMapper();

        /**
         * Invoked when a connection to the server has been established.
         *
         * @param session the session associated with the server connection
         * @throws IOException if there is a JSON serialization problem
         */
        @OnOpen
        public void onOpen(@Nonnull final Session session) throws IOException {
            LOG.info("Bot connected");
            // The first thing we need to do once connected is register the bot.
            session.getBasicRemote().sendText(
                    this.objectMapper.writeValueAsString(new RegisterBotRequest("user-id", "example-bot", "0.0.0")));
        }

        /**
         * Invoked when we get disconnected from the server.
         *
         * @param session the session associated with the server connection
         * @param closeReason the reason describing why the connection was closed
         */
        @OnClose
        public void onClose(@Nonnull final Session session, @Nonnull final CloseReason closeReason) {
            LOG.info("Bot server connection closed: {}", closeReason.getReasonPhrase());
        }

        /**
         * Invoked when a message is received from the server.
         *
         * @param session the session associated with the server connection
         * @param json the JSON message received from the server
         */
        @OnMessage
        public void onMessage(@Nonnull final Session session, @Nonnull final String json) {
            LOG.info("Received message: {}", json);

            try {
                final Message message = this.objectMapper.readValue(json, Message.class);

                // When the game has ended, shutdown this process. This logic could be modified to continue
                // playing games but not doing that for this example.
                if (MessageType.GAME_OVER_RESPONSE == message.getType()) {
                    LATCH.countDown();
                }

                // TODO: Process the real message class.
                final Message realMessage = this.objectMapper.readValue(json, message.getType().getMessageClass());
            } catch (final IOException ioException) {
                // TODO: Problem, hard-quit the game
            }
        }
    }

    /**
     * The entry-point into this command-line application.
     *
     * @param args the command-line parameters
     * @throws URISyntaxException if there is a problem with the provided remote server end-point URI
     */
    public static void main(@Nonnull final String[] args) throws URISyntaxException {
        new ExampleBot(new URI(Optional.ofNullable(args.length > 0 ? args[0] : null).orElse(DEFAULT_END_POINT)));
    }
}
