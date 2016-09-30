package ants.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link ObjectType}.
 */
public class ObjectTypeTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(ObjectType.values().length > 0);
        assertEquals(ObjectType.FRIENDLY_ANT, ObjectType.valueOf(ObjectType.FRIENDLY_ANT.name()));
    }
}
