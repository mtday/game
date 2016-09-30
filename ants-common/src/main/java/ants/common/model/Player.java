package ants.common.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents an individual player in a game.
 */
public class Player implements Comparable<Player> {
    @Nonnull
    private final String userId;

    @Nonnull
    private final String id;

    /**
     * Create a new unique player.
     *
     * @param userId the unique id of this user account that owns this player
     * @param id the unique id of this player
     */
    public Player(@Nonnull final String userId, @Nonnull final String id) {
        this.userId = userId;
        this.id = id;
    }

    /**
     * Retrieve the unique id of this user account that owns this player.
     *
     * @return the unique id of the user account that owns this player
     */
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    /**
     * Retrieve the unique id of this player.
     *
     * @return the unique id of this player
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    @Override
    public int compareTo(@Nullable final Player other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getUserId(), other.getUserId());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Player && compareTo((Player) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getUserId());
        hash.append(getId());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("userId", getUserId());
        str.append("id", getId());
        return str.toString();
    }
}
