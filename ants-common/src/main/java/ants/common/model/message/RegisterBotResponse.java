package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

/**
 * Server response to a bot registration request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterBotResponse extends Message {
    private final boolean registered;

    /**
     * Default constructor required for Jackson deserialization.
     */
    @VisibleForTesting
    RegisterBotResponse() {
        this(false);
    }

    /**
     * Request the registration of a bot with the server.
     *
     * @param registered whether the bot has been registered successfully
     */
    public RegisterBotResponse(final boolean registered) {
        this.registered = registered;
    }

    /**
     * Retrieve whether the bot registration request was handled successfully.
     *
     * @return whether the bot registration request was handled successfully
     */
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("registered", isRegistered());
        return str.toString();
    }
}
