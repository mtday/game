package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link TerrainType}.
 */
public class TerrainTypeTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(TerrainType.values().length > 0);
        assertEquals(TerrainType.LAND, TerrainType.valueOf(TerrainType.LAND.name()));
    }
}
