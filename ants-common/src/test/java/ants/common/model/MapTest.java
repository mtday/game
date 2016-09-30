package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javax.annotation.Nonnull;

/**
 * Perform testing on the {@link Map} class.
 */
public class MapTest {
    @Nonnull
    private Map getSimpleMap() {
        final Map map = new Map(3, 4);
        for (int r = 0; r < map.getRows(); r++) {
            for (int c = 0; c < map.getCols(); c++) {
                map.getCells().add(new Cell(r, c, TerrainType.LAND, ObjectType.EMPTY));
            }
        }
        return map;
    }

    @Test
    public void testConstructor() {
        final Map map = new Map(3, 4);
        assertEquals(3, map.getRows());
        assertEquals(4, map.getCols());
        assertTrue(map.getCells().isEmpty());
    }

    @Test
    public void testGetCell() {
        final Map map = getSimpleMap();

        for (int r = 0; r < map.getRows(); r++) {
            for (int c = 0; c < map.getRows(); c++) {
                final Cell cell = map.getCell(r, c);
                assertEquals(r, cell.getRow());
                assertEquals(c, cell.getCol());
            }
        }

        assertEquals(new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY), map.getCell(0, 0));
        assertEquals(new Cell(map.getRows() - 1, map.getCols() - 1, TerrainType.LAND, ObjectType.EMPTY),
                map.getCell(map.getRows() - 1, map.getCols() - 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellNegativeRow() {
        getSimpleMap().getCell(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellNegativeCol() {
        getSimpleMap().getCell(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellInvalidRow() {
        final Map map = getSimpleMap();
        map.getCell(map.getRows(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellInvalidCol() {
        final Map map = getSimpleMap();
        map.getCell(0, map.getCols());
    }

    @Test
    public void testSetCell() {
        final Map map = getSimpleMap();
        map.setCell(0, 0, new Cell(0, 0, TerrainType.WALL, ObjectType.EMPTY));
        map.setCell(1, 1, new Cell(1, 1, TerrainType.LAND, ObjectType.ENEMY_ANT));
        map.setCell(map.getRows() - 1, map.getCols() - 1,
                new Cell(map.getRows() - 1, map.getCols() - 1, TerrainType.ANT_HILL, ObjectType.ENEMY_ANT));

        assertEquals(new Cell(0, 0, TerrainType.WALL, ObjectType.EMPTY), map.getCell(0, 0));
        assertEquals(new Cell(1, 1, TerrainType.LAND, ObjectType.ENEMY_ANT), map.getCell(1, 1));
        assertEquals(new Cell(map.getRows() - 1, map.getCols() - 1, TerrainType.ANT_HILL, ObjectType.ENEMY_ANT),
                map.getCell(map.getRows() - 1, map.getCols() - 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellNegativeRow() {
        getSimpleMap().setCell(-1, 0, new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellNegativeCol() {
        getSimpleMap().setCell(0, -1, new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellInvalidRow() {
        final Map map = getSimpleMap();
        map.setCell(map.getRows(), 0, new Cell(map.getRows(), 0, TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellInvalidCol() {
        final Map map = getSimpleMap();
        map.setCell(0, map.getCols(), new Cell(0, map.getCols(), TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellRowMismatch() {
        final Map map = getSimpleMap();
        map.setCell(0, 0, new Cell(1, 0, TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCellColMismatch() {
        final Map map = getSimpleMap();
        map.setCell(0, 0, new Cell(0, 1, TerrainType.LAND, ObjectType.EMPTY));
    }

    @Test
    public void testToString() {
        assertEquals("Map[rows=3,cols=4]", new Map(3, 4).toString());
    }
}
