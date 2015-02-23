package cs2114.mazesolver;

import android.widget.TextView;
import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.app.ShapeScreen;

/**
 * This class acts as the visible front of the Maze; it handles all of the
 * interaction between the mechanics and the user.
 *
 * @author Fintan Kelly (fintank)
 * @version 2014.10.15
 */
public class MazeSolverScreen
        extends ShapeScreen {

    /**
     * All private variables that are needed to run the class
     */
    private Maze maze;
    private RectangleShape[][] board;
    private float cellSize;
    private TextView infoLabel;
    private Mode mode;

    /**
     * Initialize any necessary variables and/or methods
     */
    public void initialize() {
        maze = new Maze(7);
        board = new RectangleShape[maze.size()][maze.size()];

        cellSize = (Math.min(getWidth(), getHeight()) / maze.size());

        RectangleShape rect = null;

        for (int i = 0; i < 7; i++) {
            for (int k = 0; k < 7; k++) {
                float x = (cellSize * i);
                float y = (cellSize * k);

                rect = new RectangleShape(x, y, (x + cellSize), (y + cellSize));
                board[i][k] = rect;

                rect.setColor(Color.black);
                rect.setFillColor(Color.white);

                add(rect);
            }
        }

        board[0][0].setFillColor(Color.maroon);
        board[6][6].setFillColor(Color.orange);

        maze.addObserver(this);

        mode = Mode.Draw;
    }

    /**
     * Tells the maze to update the colors of the cells
     *
     * @param maze2 maze that is being edited
     * @param loc location of cell that is being edited
     */
    public void changeWasObserved(Maze maze2, ILocation loc) {
        switch (maze.getCell(loc)) {
            case WALL: {
                board[loc.x()][loc.y()].setFillColor(Color.black);
                break;
            }
            case UNEXPLORED: {
                board[loc.x()][loc.y()].setFillColor(Color.white);
                break;
            }
            case FAILED_PATH: {
                board[loc.x()][loc.y()].setFillColor(Color.yellow);
                break;
            }
            case CURRENT_PATH: {
                board[loc.x()][loc.y()].setFillColor(Color.blue);
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Turns the Erase Mode on if button is hit
     */
    public void eraseWallsClicked() {
        mode = Mode.Erase;

    }

    /**
     * Turns the Draw Mode on if button is hit
     */
    public void drawWallsClicked() {
        mode = Mode.Draw;
    }

    /**
     * Turns the Goal Mode on if button is hit
     */
    public void setGoalClicked() {
        mode = Mode.Goal;
    }

    /**
     * Turns the Start Mode on if button is hit
     */
    public void setStartClicked() {
        mode = Mode.Start;
    }

    /**
     * Solves the maze when the Solve button is hit
     */
    public void solveClicked() {
        String solution = maze.solve();

        if (solution != null) {
            infoLabel.setText(solution);
        } else {
            infoLabel.setText("No solution was possible.");
        }
    }

    /**
     * Changes the tiles on the screen according to which Mode is enabled i.e:
     * If the Draw Mode is enabled, it will change cells into walls
     *
     * @param x x coordinate of the user's touch
     * @param y y coordinate of the user's touch
     */
    public void onTouchDown(float x, float y) {
        int x2 = (int) (x / cellSize);
        int y2 = (int) (y / cellSize);

        ILocation loc = new Location(x2, y2);

        switch (mode) {
            case Goal: {
                if (!maze.getGoalLocation().equals(maze.getStartLocation())) {
                    board[maze.getGoalLocation().x()][maze.getGoalLocation()
                            .y()].setFillColor(Color.white);
                } else {
                    board[maze.getGoalLocation().x()][maze.getGoalLocation()
                            .y()].setFillColor(Color.maroon);
                }

                maze.setGoalLocation(loc);

                board[x2][y2].setFillColor(Color.orange);
                break;
            }
            case Start: {
                if (!maze.getGoalLocation().equals(maze.getStartLocation())) {
                    board[maze.getStartLocation().x()][maze.getStartLocation()
                            .y()].setFillColor(Color.white);
                } else {
                    board[maze.getStartLocation().x()][maze.getStartLocation()
                            .y()].setFillColor(Color.orange);
                }

                maze.setStartLocation(loc);

                board[x2][y2].setFillColor(Color.maroon);
                break;
            }
            case Draw: {
                maze.setCell(loc, MazeCell.WALL);
                board[x2][y2].setFillColor(Color.black);
                break;
            }
            default: {
                maze.setCell(loc, MazeCell.UNEXPLORED);
                board[x2][y2].setFillColor(Color.white);
                break;
            }
        }
    }

    /**
     * Wrapper for the onTouchDown method so that it will work when the user
     * holds their finger down and moves it around the screen
     *
     * @param x x coordinate of the user's touch
     * @param y y coordinate of the user's touch
     */
    public void onTouchMove(float x, float y) {
        onTouchDown(x, y);
    }

    /**
     * Returns the maze that is currently being used
     *
     * @return Maze object of current maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Returns the RectangleShape at the x,y coordinate
     *
     * @param x x coordinate to be checked
     * @param y y coordinate to be checked
     * @return RectangleShape at the indicated position
     */
    public RectangleShape getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Returns the infoLabel
     *
     * @return infoLabel
     */
    public TextView getInfoLabel() {
        return infoLabel;
    }

    /**
     * Enum that will keep track of which button is currently being used
     */
    public enum Mode {

        /**
         * The goal button has been pressed
         */
        Goal,
        /**
         * The erase button has been pressed
         */
        Erase,
        /**
         * The start button has been pressed
         */
        Start,
        /**
         * The draw button has been pressed
         */
        Draw
    }
}
