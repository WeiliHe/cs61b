package byow.Core;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }
    public int x(){
        return this.x;
    }
    public int y() {
        return this.y;
    }
}
