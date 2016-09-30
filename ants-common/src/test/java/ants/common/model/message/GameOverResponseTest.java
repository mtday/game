package ants.common.model.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link GameOverResponse} class.
 */
public class GameOverResponseTest {
    @Test
    public void testDefaultConstructor() {
        final GameOverResponse message = new GameOverResponse();
        assertEquals("", message.getGameId());
    }

    @Test
    public void testParameterConstructor() {
        final GameOverResponse message = new GameOverResponse("game-id");
        assertEquals("game-id", message.getGameId());
    }

    @Test
    public void testToString() {
        assertEquals("GameOverResponse[gameId=game-id]", new GameOverResponse("game-id").toString());
    }
}
