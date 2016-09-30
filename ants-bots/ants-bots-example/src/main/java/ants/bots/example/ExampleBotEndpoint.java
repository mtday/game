package ants.bots.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Nonnull;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * The client end-point that will handle all the web socket messages.
 */
@ClientEndpoint
public class ExampleBotEndpoint extends Endpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBotEndpoint.class);

    private final CountDownLatch countDownLatch;
    private final ObjectMapper objectMapper;

    /**
     * Create an instance of the web socket end-point.
     *
     * @param countDownLatch the latch used to count down when this end-point is done handling messages
     * @param objectMapper the Jackson {@link ObjectMapper} used to process json data
     */
    public ExampleBotEndpoint(@Nonnull final CountDownLatch countDownLatch, @Nonnull final ObjectMapper objectMapper) {
        this.countDownLatch = countDownLatch;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve the latch used to count down when this end-point is done handling messages.
     *
     * @return the latch used to count down when this end-point is done handling messages
     */
    @Nonnull
    protected CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }

    /**
     * Retrieve the Jackson {@link ObjectMapper} used to process json data.
     *
     * @return the Jackson {@link ObjectMapper} used to process json data
     */
    @Nonnull
    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public void onOpen(@Nonnull final Session session, @Nonnull final EndpointConfig endpointConfig) {
        LOG.info("Bot connected");
        session.addMessageHandler(new ExampleBotMessageHandler(session, getCountDownLatch(), getObjectMapper()));
    }

    @Override
    public void onClose(@Nonnull final Session session, @Nonnull final CloseReason closeReason) {
        LOG.info("Bot connection closed: {}", closeReason.getReasonPhrase());
    }

    @Override
    public void onError(@Nonnull final Session session, @Nonnull final Throwable throwable) {
        LOG.error("Bot error", throwable);
    }
}
