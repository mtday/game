package ants.common.model.message;

import javax.annotation.Nonnull;

/**
 * Defines the available types of commands in this system.
 */
public enum MessageType {
    /**
     * Represents an error on the server in response to a client message.
     */
    ERROR_RESPONSE(ErrorResponse.class),

    /**
     * Request from a client to register a bot for action.
     */
    REGISTER_BOT_REQUEST(RegisterBotRequest.class),

    /**
     * Response from the server to the client's bot registration request.
     */
    REGISTER_BOT_RESPONSE(RegisterBotResponse.class),

    /**
     * Response from the server indicating the game is over.
     */
    GAME_OVER_RESPONSE(GameOverResponse.class);

    @Nonnull
    private final Class<? extends Message> messageClass;

    /**
     * Create the message type along with the implementation class.
     *
     * @param messageClass the message implementation class
     */
    MessageType(@Nonnull final Class<? extends Message> messageClass) {
        this.messageClass = messageClass;
    }

    /**
     * Retrieve the implementation class associated with this message type.
     *
     * @return the implementation class associated with this message type
     */
    @Nonnull
    public Class<? extends Message> getMessageClass() {
        return this.messageClass;
    }
}
