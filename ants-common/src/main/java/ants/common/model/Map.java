package ants.common.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Defines a map used to describe the layout of a game.
 */
public class Map {
    private final int rows;
    private final int cols;

    @Nonnull
    private final List<Cell> cells;

    /**
     * Create a map of the specified size.
     *
     * @param rows the number of rows describing the size of this map
     * @param cols the number of columns describing the size of this map
     */
    public Map(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new ArrayList<>(rows * cols);
    }

    /**
     * Retrieve the number of rows describing the size of this map.
     *
     * @return the number of rows describing the size of this map
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Retrieve the number of columns describing the size of this map.
     *
     * @return the number of columns describing the size of this map
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Retrieve the cells contained within this map.
     *
     * @return the cells contained within this map
     */
    @Nonnull
    public List<Cell> getCells() {
        return this.cells; // Not a defensive copy.
    }

    /**
     * Retrieve a specific cell at the specified location.
     *
     * @param row the row indicating the location of the cell to retrieve
     * @param col the column indicating the location of the cell to retrieve
     * @return the cell at the specified location
     * @throws IllegalArgumentException if the row or column is out of range for this map
     */
    @Nonnull
    public Cell getCell(final int row, final int col) {
        Preconditions.checkArgument(row >= 0, "Invalid negative row");
        Preconditions.checkArgument(col >= 0, "Invalid negative column");
        Preconditions.checkArgument(row < getRows(), "Invalid row, max value is: " + (getRows() - 1));
        Preconditions.checkArgument(col < getCols(), "Invalid column, max value is: " + (getCols() - 1));
        return getCells().get(row * getCols() + col);
    }

    /**
     * Update a cell within this map.
     *
     * @param row the row indicating the cell to be updated
     * @param col the column indicating the cell to be updated
     * @param cell the new cell value
     * @throws IllegalArgumentException if the row or column is out of range for this map
     */
    public void setCell(final int row, final int col, @Nonnull final Cell cell) {
        Preconditions.checkArgument(row >= 0, "Invalid negative row");
        Preconditions.checkArgument(col >= 0, "Invalid negative column");
        Preconditions.checkArgument(row < getRows(), "Invalid row, max value is: " + (getRows() - 1));
        Preconditions.checkArgument(col < getCols(), "Invalid column, max value is: " + (getCols() - 1));
        Preconditions.checkArgument(row == cell.getRow(), "Row mismatch");
        Preconditions.checkArgument(col == cell.getCol(), "Column mismatch");
        this.cells.set(row * getCols() + col, cell);
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("rows", getRows());
        str.append("cols", getCols());
        // Intentionally leaving the cells out here.
        return str.toString();
    }
}
