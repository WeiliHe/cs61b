package byow.Core;

/**
 * a room class in the world
 */
public class Room {
    // these values determine the room location
    private Point upperLeft;
    private Point lowerRight;
    private int w;
    private int h;
    private Point center;

    // constructor for the new rooms
    public Room(Point upperLeft, Point lowerRight, int w, int h) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.w = w;
        this.h = h;
        center = new Point (this.upperLeft.x() + this.lowerRight.x() / 2, this.upperLeft.y() + this.lowerRight.y() / 2);
    }

    public Point upperLeft() {
        return upperLeft;
    }

    public Point lowerRight() {
        return lowerRight;
    }

    public Point center() {
        return center;
    }

    // return true if the room intersects with another
    public boolean overlapped(Room room) {
        return (upperLeft.x() <= room.lowerRight().x() && lowerRight.x() >= room.upperLeft().x()
        && upperLeft.y() <= room.lowerRight().y() && lowerRight.y() >= room.upperLeft().y());
    }
}
