package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Player} class.
 */
public class PlayerTest {
    @Test
    public void testConstructor() {
        final Player player = new Player("user-id", "id");
        assertEquals("user-id", player.getUserId());
        assertEquals("id", player.getId());
    }

    @Test
    public void testCompareTo() {
        final Player a = new Player("user-id-1", "id-1");
        final Player b = new Player("user-id-2", "id-1");
        final Player c = new Player("user-id-1", "id-2");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(1, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(-1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final Player a = new Player("user-id-1", "id-1");
        final Player b = new Player("user-id-2", "id-1");
        final Player c = new Player("user-id-1", "id-2");

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
    }

    @Test
    public void testHashCode() {
        assertEquals(-1150701707, new Player("user-id", "id").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Player[userId=user-id,id=id]", new Player("user-id", "id").toString());
    }
}
