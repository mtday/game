package ants.server;

import ants.server.io.AntsReceiveListener;
import io.undertow.Handlers;
import io.undertow.Undertow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * The entry-point into this server process.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new AntsServer();
    }
}
