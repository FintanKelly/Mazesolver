package cs2114.mazesolver;

import sofia.graphics.Color;
import sofia.graphics.ShapeView;

/**
 * This is the test class for MazeSolverScreen.
 *
 * @author Fintan Kelly (fintank)
 * @version 2014.10.15
 */
public class MazeSolverScreenTests
        extends student.AndroidTestCase<MazeSolverScreen> {

    private Maze maze;
    private MazeSolverScreen mazeScreen;
    private ILocation loc;
    private ShapeView shapeView;
    private int cellSize;

    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MazeSolverScreenTests() {
        super(MazeSolverScreen.class);
    }

    /**
     * Initializes the text fixtures.
     */
    public void setUp() {
        float viewSize = Math.min(shapeView.getWidth(), shapeView.getHeight());

        mazeScreen = getScreen();

        cellSize = (int) (viewSize / 7);

        loc = new Location(1, 1);

        mazeScreen.initialize();

        maze = mazeScreen.getMaze();
    }

    /**
     * Tests the drawWalls method
     */
    public void testDrawWalls() {
        click(getScreen().findViewById(R.id.drawWalls));

        maze.setCell(new Location(3, 1), MazeCell.INVALID_CELL);

        touchDownCell(loc.x(), loc.y());
        touchMoveCell(1, 2);
        touchUp();
        clickCell(3, 1);

        assertEquals(
                mazeScreen.getCell(loc.x(), loc.y()).getColor(),
                Color.black);
        assertEquals(MazeCell.WALL, maze.getCell(loc));
        assertEquals(MazeCell.WALL, maze.getCell(new Location(1, 2)));
        assertEquals(MazeCell.INVALID_CELL, maze.getCell(new Location(3, 1)));
    }

    /**
     * Tests the Erase button and its function
     */
    public void testErase() {
        click(getScreen().findViewById(R.id.drawWalls));
        clickCell(loc.x(), loc.y());

        click(getScreen().findViewById(R.id.eraseWalls));
        clickCell(loc.x(), loc.y());

        assertEquals(maze.getCell(loc), MazeCell.UNEXPLORED);
    }

    /**
     * Tests the setStart method
     */
    public void testSetStart() {
        click(getScreen().findViewById(R.id.setGoal));
        clickCell(loc.x(), loc.y());

        assertEquals(maze.getGoalLocation(), loc);

        click(getScreen().findViewById(R.id.setStart));
        clickCell(loc.x(), loc.y());

        assertEquals(maze.getStartLocation(), loc);
    }

    /**
     * Tests the setGoal method
     */
    public void testSetGoal() {
        click(getScreen().findViewById(R.id.setStart));
        clickCell(loc.x(), loc.y());

        assertEquals(maze.getStartLocation(), loc);

        click(getScreen().findViewById(R.id.setGoal));
        clickCell(loc.x(), loc.y());

        assertEquals(maze.getGoalLocation(), loc);
    }

    /**
     * Tests the solve method
     */
    public void testSolve() {
        click(getScreen().findViewById(R.id.setStart));
        clickCell(loc.x(), loc.y());

        click(getScreen().findViewById(R.id.setGoal));
        clickCell(loc.x(), loc.y());

        click(getScreen().findViewById(R.id.solve));

        assertEquals(
                getScreen().getCell(loc.x(), loc.y()).getFillColor(),
                Color.blue);
        assertEquals(getScreen().getInfoLabel().getText(), "(1, 1)");

        click(getScreen().findViewById(R.id.setStart));
        clickCell(loc.x(), loc.y());

        click(getScreen().findViewById(R.id.setGoal));
        clickCell((loc.x() + 1), (loc.y() + 1));

        click(getScreen().findViewById(R.id.drawWalls));
        clickCell(1, 0);
        clickCell(0, 1);
        clickCell(1, 2);
        clickCell(2, 1);

        click(getScreen().findViewById(R.id.solve));
        assertEquals(
                getScreen().getInfoLabel().getText(),
                "No solution was possible.");
    }

    /**
     * Tests for a failed solve
     */
    public void testSolve2() {
        click(getScreen().findViewById(R.id.setStart));
        clickCell(0, 0);

        click(getScreen().findViewById(R.id.setGoal));
        clickCell(5, 2);

        click(getScreen().findViewById(R.id.drawWalls));
        clickCell(0, 1);
        clickCell(1, 1);
        clickCell(2, 1);
        clickCell(3, 1);
        clickCell(5, 1);
        clickCell(6, 1);

        assertEquals(
                maze.solve(),
                "(0, 0) (1, 0) (2, 0) (3, 0) (4, 0) (4, 1) (4, 2) (5, 2)");
    }

    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     */
    private void touchDownCell(int x, int y) {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }

    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     */
    private void touchMoveCell(int x, int y) {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }

    /**
     * Simulates clicking the middle of the specified cell in the maze. This is
     * equivalent to calling: touchDownCell(x, y); touchUp();
     */
    private void clickCell(int x, int y) {
        touchDownCell(x, y);
        touchUp();
    }
}
