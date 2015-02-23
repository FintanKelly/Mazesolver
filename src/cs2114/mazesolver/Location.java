package cs2114.mazesolver;

/**
 * This class acts as the cells for the Maze, the x and y coordinates.
 *
 * @author Fintan Kelly (fintank)
 * @version Sep 23, 2014
 */
public class Location
        implements ILocation {

    /**
     * Private variables for this class describing x and y
     */
    private int x;
    private int y;

    /**
     * Create a new Location object.
     *
     * @param x X coordinate of the Location
     * @param y Y coordinate of the Location
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks to see if two objects are equal using obj as the comparator
     *
     * @param obj Object that is being used to compare
     * @return True or false depending on if objects are equal
     */
    public boolean equals(Object obj) {
        return (obj instanceof Location) ? ((this.x() == ((Location) obj).x())
                && (this.y() == ((Location) obj).y()) ? true : false) : false;
    }

    /**
     * Returns a String representation of the class
     *
     * @return String representation of Location
     */
    @Override
    public String toString() {
        return "(" + x() + ", " + y() + ")";
    }

    /**
     * Returns a Location object that contains the cell east of the object
     *
     * @return East cell
     */
    @Override
    public ILocation east() {
        return new Location((x() + 1), y());
    }

    /**
     * Returns a Location object that contains the cell north of the object
     *
     * @return North cell
     */
    @Override
    public ILocation north() {
        return (y() > 0) ? new Location(x(), (y() - 1)) : null;
    }

    /**
     * Returns a Location object that contains the cell south of the object
     *
     * @return South cell
     */
    @Override
    public ILocation south() {
        return new Location(x(), (y() + 1));
    }

    /**
     * Returns a Location object that contains the cell west of the object
     *
     * @return West cell
     */
    @Override
    public ILocation west() {
        return (x() > 0) ? new Location((x() - 1), y()) : null;
    }

    /**
     * Returns the x coordinate
     *
     * @return X coordinate
     */
    @Override
    public int x() {
        return x;
    }

    /**
     * Returns the y coordinate
     *
     * @return Y coordinate
     */
    @Override
    public int y() {
        return y;
    }

}
