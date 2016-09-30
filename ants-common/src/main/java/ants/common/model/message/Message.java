package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

/**
 * The base class for commands sent back and forth between the client and server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @Nonnull
    private final MessageType type;

    /**
     * Default constructor required for Jackson deserialization.
     */
    @VisibleForTesting
    Message() {
        this(MessageType.ERROR_RESPONSE);
    }

    /**
     * Create a new command of the specified type.
     *
     * @param type the type of this command
     */
    public Message(@Nonnull final MessageType type) {
        this.type = type;
    }

    /**
     * Retrieve the type of this command.
     *
     * @return the type of this command
     */
    @Nonnull
    public MessageType getType() {
        return this.type;
    }

    /**
     * Retrieve a {@link ToStringBuilder} populated with the fields from this class.
     *
     * @return a populated {@link ToStringBuilder}
     */
    @Nonnull
    protected ToStringBuilder getToStringBuilder() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("type", getType());
        return str;
    }

    @Override
    @Nonnull
    public String toString() {
        return getToStringBuilder().toString();
    }
}
