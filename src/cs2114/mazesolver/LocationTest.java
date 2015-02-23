package cs2114.mazesolver;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test class for the Location class
 *
 * @author Fintan Kelly (fintank)
 * @version Sep 24, 2014
 */
public class LocationTest
        extends TestCase {

    private Location location;
    private Location location2;
    private Location location3;
    private Location location4;
    private String string;

    /**
     * Initial setup for the test class
     */
    @Before
    public void setUp()
            throws Exception {
        location = new Location(1, 1);
        location2 = new Location(1, 1);
        location4 = new Location(0, 0);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void testLocation() {
        assertEquals("(1, 1)", location.toString());
    }

    /**
     * Tests the equals method
     */
    @Test
    public void testEquals() {
        assertTrue(location.equals(location2));
        assertFalse(location.equals(location3));
        assertFalse(location.equals(location4));
        assertFalse(location.equals(string));

        location3 = new Location(1, 0);
        assertFalse(location.equals(location3));
    }

    /**
     * Tests the toString method
     */
    @Test
    public void testToString() {
        assertEquals("(1, 1)", location.toString());
    }

    /**
     * Tests the east method
     */
    @Test
    public void testEast() {
        assertEquals(new Location(2, 1), location.east());
    }

    /**
     * Tests the north method
     */
    @Test
    public void testNorth() {
        assertEquals(new Location(1, 0), location.north());
    }

    /**
     * Tests the south method
     */
    @Test
    public void testSouth() {
        assertEquals(new Location(1, 2), location.south());
    }

    /**
     * Tests the west method
     */
    @Test
    public void testWest() {
        assertEquals(new Location(0, 1), location.west());
    }

    /**
     * Tests the x method
     */
    @Test
    public void testX() {
        assertEquals(1, location.x());
    }

    /**
     * Tests the y method
     */
    @Test
    public void testY() {
        assertEquals(1, location.y());
    }

}
