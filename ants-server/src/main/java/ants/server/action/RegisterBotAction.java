package ants.server.action;

import ants.common.model.message.Message;
import ants.common.model.message.MessageType;
import ants.common.model.message.RegisterBotRequest;
import ants.common.model.message.RegisterBotResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.annotation.Nonnull;

/**
 * Handle a bot registration request.
 */
public class RegisterBotAction extends Action {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterBotAction.class);

    /**
     * Create an action instance used to process messages.
     *
     * @param objectMapper the {@link ObjectMapper} used to process JSON data
     */
    public RegisterBotAction(@Nonnull final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean handles(@Nonnull final MessageType messageType) {
        return MessageType.REGISTER_BOT_REQUEST == messageType;
    }

    @Override
    public void process(@Nonnull final WebSocketChannel channel, @Nonnull final Pair<MessageType, Message> message)
            throws IOException {
        if (message.getValue() instanceof RegisterBotRequest) {
            final RegisterBotRequest registerBotRequest = (RegisterBotRequest) message.getValue();
            LOG.info("Registering bot: {}", registerBotRequest);

            final RegisterBotResponse response = new RegisterBotResponse(true);
            WebSockets.sendText(getObjectMapper().writeValueAsString(response), channel, null);
        } else {
            throw new IllegalArgumentException("Invalid message type: " + message.getValue());
        }
    }
}
