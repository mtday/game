package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Perform testing on the {@link Game} class.
 */
public class GameTest {
    @Test
    public void testConstructor() {
        final Game game = new Game("id", Arrays.asList(new Player("user-1", "id-1"), new Player("user-1", "id-2")));
        assertEquals("id", game.getId());
        final List<Player> players = game.getPlayers();
        assertEquals(2, players.size());
        assertTrue(players.contains(new Player("user-1", "id-1")));
        assertTrue(players.contains(new Player("user-1", "id-2")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNoPlayers() {
        new Game("id", Collections.emptyList());
    }

    @Test
    public void testCompareTo() {
        final Game a = new Game("id-1", Arrays.asList(new Player("user-1", "id-1"), new Player("user-1", "id-2")));
        final Game b = new Game("id-1", Arrays.asList(new Player("user-2", "id-1"), new Player("user-2", "id-2")));
        final Game c = new Game("id-2", Arrays.asList(new Player("user-2", "id-1"), new Player("user-2", "id-2")));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(0, a.compareTo(b)); // only game id is compared
        assertEquals(-1, a.compareTo(c));
        assertEquals(0, b.compareTo(a)); // only game id is compared
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final Game a = new Game("id-1", Arrays.asList(new Player("user-1", "id-1"), new Player("user-1", "id-2")));
        final Game b = new Game("id-1", Arrays.asList(new Player("user-2", "id-1"), new Player("user-2", "id-2")));
        final Game c = new Game("id-2", Arrays.asList(new Player("user-2", "id-1"), new Player("user-2", "id-2")));

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertEquals(a, b); // only game id is compared
        assertNotEquals(a, c);
        assertEquals(b, a); // only game id is compared
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
    }

    @Test
    public void testHashCode() {
        assertEquals(
                3225599,
                new Game("id-1", Arrays.asList(new Player("user-1", "id-1"), new Player("user-1", "id-2"))).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Game[id=id-1,players=[Player[userId=user-1,id=id-1], Player[userId=user-1,id=id-2]]]",
                new Game("id-1", Arrays.asList(new Player("user-1", "id-1"), new Player("user-1", "id-2"))).toString());
    }
}
