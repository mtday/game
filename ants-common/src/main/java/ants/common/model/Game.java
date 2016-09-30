package ants.common.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a game in this system.
 */
public class Game implements Comparable<Game> {
    @Nonnull
    private final String id;

    @Nonnull
    private final List<Player> players;

    /**
     * Create a new unique game.
     *
     * @param id the unique id of this game
     * @param players the players that are participating in this game
     * @throws IllegalArgumentException if the provided list of players is invalid
     */
    public Game(@Nonnull final String id, @Nonnull final List<Player> players) {
        Preconditions.checkArgument(players.size() > 1, "Invalid number of players");
        this.id = id;
        this.players = new ArrayList<>(players); // Defensive copy.
    }

    /**
     * Retrieve the unique id of this game.
     *
     * @return the unique id of this game
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the players participating in this game.
     *
     * @return an unmodifiable list of players participating in this game
     */
    @Nonnull
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    @Override
    public int compareTo(@Nullable final Game other) {
        if (other == null) {
            return 1;
        }

        return getId().compareTo(other.getId());
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Game && compareTo((Game) other) == 0;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("players", getPlayers());
        return str.toString();
    }
}
