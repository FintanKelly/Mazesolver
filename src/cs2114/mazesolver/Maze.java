package cs2114.mazesolver;

import sofia.util.Observable;
import java.util.Stack;

/**
 * Creates the board of Locations and solves the Maze.
 *
 * @author Fintan Kelly (fintank)
 * @version Sep 23, 2014
 */
public class Maze
        extends Observable
        implements IMaze {

    /**
     * Private variables that will be used in the Maze
     */
    private MazeCell[][] board;
    private ILocation goal;
    private ILocation start;
    private int size;
    private Stack<ILocation> stack;

    /**
     * Create a new Maze object.
     *
     * @param size size of the square board
     */
    public Maze(int size) {
        board = new MazeCell[size][size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = MazeCell.UNEXPLORED;
            }
        }
        goal = new Location(size - 1, size - 1);
        start = new Location(0, 0);
    }

    /**
     * Returns the MazeCell that is located in the indicated x and y coordinate
     *
     * @param location Location object containing the x and y coordinates
     * @return returns the MazeCell located in indicated Location
     */
    @Override
    public MazeCell getCell(ILocation location) {
        return ((location.x() < size && location.x() >= 0)
                && (location.y() < size && location.y() >= 0))
                        ? board[location.x()][location.y()]
                        : MazeCell.INVALID_CELL;
    }

    /**
     * Returns the end of the maze, or the "goal"
     *
     * @return Location object of the goal
     */
    @Override
    public ILocation getGoalLocation() {
        return goal;
    }

    /**
     * Returns the start of the maze
     *
     * @return Location object of the start
     */
    @Override
    public ILocation getStartLocation() {
        return start;
    }

    /**
     * Sets the value of the MazeCell in the Location
     *
     * @param location Location object of the cell
     * @param cell MazeCell object in the indicated Location
     */
    @Override
    public void setCell(ILocation location, MazeCell cell) {
        if ((location.equals(start) || location.equals(goal))
                && cell.equals(MazeCell.WALL)) {
            return;
        }
        if (getCell(location) != MazeCell.INVALID_CELL) {
            board[location.x()][location.y()] = cell;
        }

        notifyObservers(location);
    }

    /**
     * Sets the new goal location
     *
     * @param location Location object of new goal
     */
    @Override
    public void setGoalLocation(ILocation location) {
        if (getCell(location).equals(MazeCell.WALL)) {
            setCell(location, MazeCell.UNEXPLORED);
        }
        goal = location;
    }

    /**
     * Sets the new start location
     *
     * @param location Location object of new start
     */
    @Override
    public void setStartLocation(ILocation location) {
        if (getCell(location).equals(MazeCell.WALL)) {
            setCell(location, MazeCell.UNEXPLORED);
        }
        start = location;
    }

    /**
     * Returns the size of the Maze
     *
     * @return Integer value of the size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Finds all of the squares adjacent to the location location
     *
     * @param location location location
     * @return Array of MazeCells that are around the Location
     */
    public ILocation[] getAdjacentCells(ILocation location) {
        ILocation[] adjacentSquares = new ILocation[4];
        adjacentSquares[0] = location.north();
        adjacentSquares[1] = location.east();
        adjacentSquares[2] = location.south();
        adjacentSquares[3] = location.west();

        return adjacentSquares;
    }

    /**
     * Runs through the Maze and records the movements to be sent to the
     * solution method
     *
     * @param location ILocation of the current cell
     * @return String that contains the solution to the Maze
     */
    public boolean findPath(ILocation location) {
        ILocation temp = location;
        setCell(temp, MazeCell.CURRENT_PATH);

        if (location.equals(goal)) {
            return true;
        }

        ILocation[] adjacentSquares = getAdjacentCells(temp);

        for (ILocation possibleLocation : adjacentSquares) {
            if (possibleLocation != null) {
                if (getCell(possibleLocation).equals(MazeCell.UNEXPLORED)) {
                    temp = stack.push(possibleLocation);
                    setCell(temp, MazeCell.CURRENT_PATH);

                    if (findPath(temp)) {
                        return true;
                    }

                    setCell(temp, MazeCell.FAILED_PATH);
                    stack.pop();

                }
            }
        }

        return false;
    }

    /**
     * Wrapper method for the findPath method
     *
     * @return String of the movements
     */
    public String solve() {
        stack = new Stack<ILocation>();
        stack.push(start);

        return (findPath(start)) ? solution() : null;
    }

    /**
     * Constructs and returns the solution for the Maze
     *
     * @return String of the solution of movements
     */
    public String solution() {
        StringBuilder string = new StringBuilder();
        Stack<ILocation> stack2 = new Stack<ILocation>();

        while (!stack.isEmpty()) {
            stack2.push(stack.pop());
        }
        while (!stack2.isEmpty()) {
            if (stack2.size() == 1) {
                string.append(stack2.pop().toString());
            } else {
                string.append(stack2.pop().toString() + " ");
            }
        }
        return string.toString();
    }
}
