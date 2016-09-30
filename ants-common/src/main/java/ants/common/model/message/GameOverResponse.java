package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

/**
 * Server response indicating that the game is over.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameOverResponse extends Message {
    @Nonnull
    private final String gameId;

    /**
     * Default constructor required for Jackson deserialization.
     */
    @VisibleForTesting
    GameOverResponse() {
        this("");
    }

    /**
     * Create a server response indicating the game is over.
     *
     * @param gameId the unique id of the game that has ended
     */
    public GameOverResponse(@Nonnull final String gameId) {
        this.gameId = gameId;
    }

    /**
     * Retrieve the unique id of the game that has ended.
     *
     * @return the unique id of the game that has ended
     */
    @Nonnull
    public String getGameId() {
        return this.gameId;
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("gameId", getGameId());
        return str.toString();
    }
}
