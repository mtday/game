package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

/**
 * Request registration of a bot on the server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterBotRequest extends Message {
    @Nonnull
    private final String userId;

    @Nonnull
    private final String botName;

    @Nonnull
    private final String botVersion;

    /**
     * Default constructor required for Jackson deserialization.
     */
    @VisibleForTesting
    RegisterBotRequest() {
        this("", "", "");
    }

    /**
     * Request the registration of a bot with the server.
     *
     * @param userId the unique id of the user that owns the bot
     * @param botName the name of the bot being registered
     * @param botVersion the version number of the bot being registered
     */
    public RegisterBotRequest(
            @Nonnull final String userId, @Nonnull final String botName, @Nonnull final String botVersion) {
        this.userId = userId;
        this.botName = botName;
        this.botVersion = botVersion;
    }

    /**
     * Retrieve the unique id of the user that owns this bot.
     *
     * @return the unique id of the user that owns this bot
     */
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    /**
     * Retrieve the name of the bot being registered.
     *
     * @return the name of the bot being registered
     */
    @Nonnull
    public String getBotName() {
        return this.botName;
    }

    /**
     * Retrieve the version of the bot being registered.
     *
     * @return the version of the bot being registered
     */
    @Nonnull
    public String getBotVersion() {
        return this.botVersion;
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("userId", getUserId());
        str.append("botName", getBotName());
        str.append("botVersion", getBotVersion());
        return str.toString();
    }
}
