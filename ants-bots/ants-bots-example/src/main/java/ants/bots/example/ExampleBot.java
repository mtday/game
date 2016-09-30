package ants.bots.example;

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
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

/**
 * Provides an example bot implementation.
 */
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
            final ExampleBotEndpoint endpoint = new ExampleBotEndpoint(LATCH, new ObjectMapper());
            final ClientEndpointConfig endpointConfig = ClientEndpointConfig.Builder.create().build();
            clientManager.connectToServer(endpoint, endpointConfig, uri);
            LATCH.await();
        } catch (final DeploymentException | IOException clientFailure) {
            throw new RuntimeException("Failed to connect to Ants server", clientFailure);
        } catch (final InterruptedException interrupted) {
            LOG.warn("Interrupted, exiting");
        }
    }

    /**
     * The entry-point into this command-line application.
     *
     * @param args the command-line parameters
     * @throws URISyntaxException if there is a problem with the provided remote server end-point URI
     */
    public static void main(@Nonnull final String[] args) throws URISyntaxException {
        final String serverUri = args.length > 0 ? args[0] : null;
        new ExampleBot(new URI(Optional.ofNullable(serverUri).orElse(DEFAULT_END_POINT)));
    }
}
