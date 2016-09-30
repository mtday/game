package ants.common.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the base class representing a single cell in a map.
 */
public class Cell implements Comparable<Cell> {
    private int row;
    private int col;
    @Nonnull
    private TerrainType terrainType;
    @Nonnull
    private ObjectType objectType;

    /**
     * Sets the location of this cell along with the type of terrain it represents.
     *
     * @param row the map row where this cell is located
     * @param col the map column where this cell is located
     * @param terrainType the type of terrain within the cell
     * @param objectType the type of object within the cell
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public Cell(
            final int row, final int col, @Nonnull final TerrainType terrainType,
            @Nonnull final ObjectType objectType) {
        Preconditions.checkArgument(row >= 0, "Invalid negative row");
        Preconditions.checkArgument(col >= 0, "Invalid negative column");
        this.row = row;
        this.col = col;
        this.terrainType = terrainType;
        this.objectType = objectType;
    }

    /**
     * Retrieve the map row where this cell is located, as a positive integer.
     *
     * @return the map row where this cell is located
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Set the new row where this cell is located, as a positive integer.
     *
     * @param row the new map row where this cell is located
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * Retrieve the map column where this cell is located, as a positive integer.
     *
     * @return the map column where this cell is located
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Set the new col where this cell is located, as a positive integer.
     *
     * @param col the new map column where this cell is located
     */
    public void setCol(final int col) {
        this.col = col;
    }

    /**
     * Retrieve the type of terrain represented in this cell.
     *
     * @return the type of terrain represented in this cell
     */
    @Nonnull
    public TerrainType getTerrainType() {
        return this.terrainType;
    }

    /**
     * Set the new terrain type represented in this cell.
     *
     * @param terrainType the new terrain type represented in this cell
     */
    public void setTerrainType(@Nonnull final TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    /**
     * Retrieve the type of object contained within this cell.
     *
     * @return the type of object contained within this cell
     */
    @Nonnull
    public ObjectType getObjectType() {
        return this.objectType;
    }

    /**
     * Set the new type of object contained within this cell.
     *
     * @param objectType the new type of object contained within this cell
     */
    public void setObjectType(@Nonnull final ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public int compareTo(@Nullable final Cell other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getRow(), other.getRow());
        cmp.append(getCol(), other.getCol());
        cmp.append(getTerrainType(), other.getTerrainType());
        cmp.append(getObjectType(), other.getObjectType());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Cell && compareTo((Cell) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getRow());
        hash.append(getCol());
        // Using the name on these enumerations so the hash code is deterministic.
        hash.append(getTerrainType().name());
        hash.append(getObjectType().name());
        return hash.toHashCode();
    }

    /**
     * Create a populated {@link ToStringBuilder} containing a description of the fields in this class.
     *
     * @return a populated {@link ToStringBuilder} to be used by child classes when generating a string
     * representation of this object
     */
    @Nonnull
    protected ToStringBuilder getToStringBuilder() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("row", getRow());
        str.append("col", getCol());
        str.append("terrainType", getTerrainType());
        str.append("objectType", getObjectType());
        return str;
    }

    @Override
    @Nonnull
    public String toString() {
        return getToStringBuilder().toString();
    }
}
