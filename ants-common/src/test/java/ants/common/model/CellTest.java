package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Cell} class.
 */
public class CellTest {
    @Test
    public void testConstructor() {
        final Cell cell = new Cell(1, 2, TerrainType.LAND, ObjectType.EMPTY);
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
        assertEquals(TerrainType.LAND, cell.getTerrainType());
        assertEquals(ObjectType.EMPTY, cell.getObjectType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeRow() {
        new Cell(-1, 0, TerrainType.LAND, ObjectType.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeCol() {
        new Cell(0, -1, TerrainType.LAND, ObjectType.EMPTY);
    }

    @Test
    public void testSetters() {
        final Cell cell = new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY);
        cell.setRow(1);
        cell.setCol(2);
        cell.setTerrainType(TerrainType.ANT_HILL);
        cell.setObjectType(ObjectType.FRIENDLY_ANT);

        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
        assertEquals(TerrainType.ANT_HILL, cell.getTerrainType());
        assertEquals(ObjectType.FRIENDLY_ANT, cell.getObjectType());
    }

    @Test
    public void testCompareTo() {
        final Cell a = new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY);
        final Cell b = new Cell(0, 0, TerrainType.LAND, ObjectType.ENEMY_ANT);
        final Cell c = new Cell(0, 0, TerrainType.WALL, ObjectType.EMPTY);
        final Cell d = new Cell(0, 1, TerrainType.LAND, ObjectType.EMPTY);
        final Cell e = new Cell(1, 0, TerrainType.LAND, ObjectType.EMPTY);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-2, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(2, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final Cell a = new Cell(0, 0, TerrainType.LAND, ObjectType.EMPTY);
        final Cell b = new Cell(0, 0, TerrainType.LAND, ObjectType.ENEMY_ANT);
        final Cell c = new Cell(0, 0, TerrainType.WALL, ObjectType.EMPTY);
        final Cell d = new Cell(0, 1, TerrainType.LAND, ObjectType.EMPTY);
        final Cell e = new Cell(1, 0, TerrainType.LAND, ObjectType.EMPTY);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
    }

    @Test
    public void testHashCode() {
        assertEquals(1855780855, new Cell(1, 2, TerrainType.LAND, ObjectType.ENEMY_ANT).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Cell[row=1,col=2,terrainType=LAND,objectType=ENEMY_ANT]",
                new Cell(1, 2, TerrainType.LAND, ObjectType.ENEMY_ANT).toString());
    }
}
