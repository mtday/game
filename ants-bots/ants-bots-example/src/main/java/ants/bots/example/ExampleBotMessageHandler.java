package ants.bots.example;

import ants.common.exception.InvalidMessageException;
import ants.common.io.MessageParser;
import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Nonnull;
import javax.websocket.ClientEndpoint;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 * The message handler responsible for processing messages from the server.
 */
@ClientEndpoint
public class ExampleBotMessageHandler implements MessageHandler.Whole<String> {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBotMessageHandler.class);

    private final Session session;
    private final CountDownLatch countDownLatch;
    private final ObjectMapper objectMapper;

    private final MessageParser messageParser;

    /**
     * Create an instance of this message handler.
     *
     * @param session the web socket {@link Session} associated with the messages received by this handler
     * @param countDownLatch the latch used to count down when this end-point is done handling messages
     * @param objectMapper the Jackson {@link ObjectMapper} used to process json data
     */
    public ExampleBotMessageHandler(
            @Nonnull final Session session, @Nonnull final CountDownLatch countDownLatch,
            @Nonnull final ObjectMapper objectMapper) {
        this.session = session;
        this.countDownLatch = countDownLatch;
        this.objectMapper = objectMapper;

        this.messageParser = new MessageParser(this.objectMapper);
    }

    /**
     * Retrieve the the web socket {@link Session} associated with the messages received by this handler.
     *
     * @return the the web socket {@link Session} associated with the messages received by this handler
     */
    @Nonnull
    protected Session getSession() {
        return this.session;
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
    public void onMessage(@Nonnull final String messageData) {
        LOG.info("Received message: {}", messageData);

        /*
        try {
            // The first thing we need to do once connected is register the bot.
            session.getBasicRemote().sendText(String.format("%s:%s", MessageType.REGISTER_BOT_REQUEST,
                    getObjectMapper().writeValueAsString(new RegisterBotRequest("user-id", "example-bot", "0.0.0"))));
        } catch (final IOException ioException) {
            throw new RuntimeException("Failed to generate json to send to the server", ioException);
        }
        */

        try {
            final Pair<MessageType, Message> message = getMessageParser().parseMessage(messageData);

            // When the game has ended, shutdown this process. This logic could be modified to continue
            // playing games but not doing that for this example.
            if (MessageType.GAME_OVER_RESPONSE == message.getKey()) {
                getCountDownLatch().countDown();
            }

            // TODO: Process the real message class.
        } catch (final InvalidMessageException invalidMessage) {
            // TODO: Problem, hard-quit the game
        }
    }
}
