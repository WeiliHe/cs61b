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
    public Room(Point upperLeft, int w, int h) {
        int x1 = upperLeft.x();
        int y1 = upperLeft.y();
        this.upperLeft = new Point (x1, y1);
        this.lowerRight = new Point (x1 + w - 1, y1 - h + 1);
        this.w = w;
        this.h = h;
        center = new Point ( (2 * x1 + w) / 2, (2 * y1 - h) / 2);
    }

    public int upperLeftx() {
        return upperLeft.x();
    }

    public int upperLefty(){
        return upperLeft.y();
    }

    public int lowerRightx() {
        return lowerRight.x();
    }

    public int lowerRighty(){
        return lowerRight.y();
    }

    public Point center() {
        return center;
    }

    // return true if the room intersects with another
    public boolean overlap(Room room) {
        return (this.upperLeftx() <= room.lowerRightx() && this.lowerRightx() >= room.upperLeftx()
        && this.upperLefty() >= room.lowerRighty() && this.lowerRighty() <= room.upperLefty());
    }
}
