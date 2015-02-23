package cs2114.mazesolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Maze class
 *
 * @author Fintan Kelly (fintank)
 * @version Sep 23, 2014
 */
public class MazeTest {

    /**
     * Private variables used in testing the Maze class
     */
    private Maze maze;
    private Location location;
    private Location location2;

    /**
     * Initial setup of test cases
     */
    @Before
    public void setUp() {
        maze = new Maze(5);
    }

    /**
     * Tests the constructor of the Maze
     */
    @Test
    public void testConstructor() {
        location = new Location(4, 4);

        assertEquals(maze.size(), 5);
        assertEquals(maze.getGoalLocation(), location);

        location2 = new Location(0, 0);
        assertEquals(maze.getStartLocation(), location2);

        for (int k = 0; k < maze.size(); k++) {
            for (int i = 0; i < maze.size(); i++) {
                location = new Location(k, i);
                assertEquals(maze.getCell(location), MazeCell.UNEXPLORED);
            }
        }
    }

    /**
     * Combination test method for setCell, setStartLocation, and
     * setGoalLocation
     */
    @Test
    public void testSetLocations() {
        location = new Location(2, 2);
        maze.setCell(location, MazeCell.CURRENT_PATH);
        assertEquals(maze.getCell(location), MazeCell.CURRENT_PATH);

        maze.setStartLocation(location);
        assertEquals(maze.getStartLocation(), location);

        maze.setGoalLocation(location);
        assertEquals(maze.getGoalLocation(), location);
    }

    /**
     * Second test method for setGoalLocation and setStartLocation
     */
    @Test
    public void testSetLocation2() {
        location = new Location(2, 2);
        maze.setCell(location, MazeCell.WALL);

        maze.setGoalLocation(location);
        assertEquals(maze.getCell(location), MazeCell.UNEXPLORED);
        assertEquals(maze.getGoalLocation(), location);

        maze.setGoalLocation(location);
        maze.setCell(location, MazeCell.WALL);
        assertNotSame(maze.getCell(location), MazeCell.WALL);

        maze.setCell(location, MazeCell.WALL);
        maze.setStartLocation(location);
        maze.setCell(location, MazeCell.WALL);
        assertNotSame(maze.getCell(location), MazeCell.WALL);
    }

    /**
     * Third test for setCell, setGoalLocation, and setStartLocation
     */
    @Test
    public void testSetLocations3() {
        location = new Location(3, 3);
        maze.setCell(location, MazeCell.WALL);

        maze.setStartLocation(location);
        assertEquals(maze.getCell(location), MazeCell.UNEXPLORED);
        assertEquals(maze.getStartLocation(), location);
    }

    /**
     * Tests the getCell method
     */
    @Test
    public void testGetCell() {
        location = new Location(10, 0);
        maze.setCell(location, MazeCell.UNEXPLORED);
        assertNotSame(maze.getCell(location), MazeCell.UNEXPLORED);

        location = new Location(0, 10);
        maze.setCell(location, MazeCell.UNEXPLORED);
        assertNotSame(maze.getCell(location), MazeCell.UNEXPLORED);

        location = new Location(-10, 0);
        maze.setCell(location, MazeCell.UNEXPLORED);
        assertNotSame(maze.getCell(location), MazeCell.UNEXPLORED);

        location = new Location(0, -10);
        maze.setCell(location, MazeCell.UNEXPLORED);
        assertNotSame(maze.getCell(location), MazeCell.UNEXPLORED);
    }

    /**
     * Tests the solve method
     */
    @Test
    public void testSolve() {
        Maze maze2 = new Maze(2);
        location = new Location(0, 1);
        maze2.setCell(location, MazeCell.WALL);

        location2 = new Location(1, 0);
        maze2.setCell(location2, MazeCell.WALL);
        assertEquals(maze2.solve(), null);

        Maze maze3 = new Maze(5);
        location = new Location(1, 1);
        maze3.setCell(location, MazeCell.WALL);

        location = new Location(1, 3);
        maze3.setCell(location, MazeCell.WALL);

        location = new Location(2, 2);
        maze3.setCell(location, MazeCell.WALL);

        location = new Location(2, 4);
        maze3.setCell(location, MazeCell.WALL);

        location = new Location(4, 1);
        maze3.setCell(location, MazeCell.WALL);

        assertEquals(
                maze3.solve(),
                "(0, 0) (1, 0) (2, 0) (3, 0) (3, 1) (3, 2) (4, 2) (4, 3) (4, 4)");

        Maze maze4 = new Maze(5);

        location = new Location(1, 0);
        maze4.setCell(location, MazeCell.WALL);

        location = new Location(1, 1);
        maze4.setCell(location, MazeCell.WALL);

        location = new Location(2, 1);
        maze4.setCell(location, MazeCell.WALL);

        location = new Location(2, 3);
        maze4.setCell(location, MazeCell.WALL);

        location = new Location(4, 3);
        maze4.setCell(location, MazeCell.WALL);

        assertEquals(
                maze4.solve(),
                "(0, 0) (0, 1) (0, 2) (1, 2) (2, 2) (3, 2) (3, 3) (3, 4) (4, 4)");

        Maze maze6 = new Maze(3);

        maze6.setStartLocation(new Location(1, 1));
        maze6.setGoalLocation(new Location(0, 0));

        maze6.setCell(new Location(2, 2), MazeCell.WALL);
        maze6.setCell(new Location(0, 1), MazeCell.WALL);

        assertEquals(maze6.solve(), "(1, 1) (1, 0) (0, 0)");
    }
}
