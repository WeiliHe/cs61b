package bearmaps;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import java.util.List;

public class KDTreeTest {
    @Test
    public void testBasic(){
        List<Point> pointList = new ArrayList<Point>(List.of(new Point(1,2),
                new Point(-2,1), new Point(0,-2), new Point(3,1),
                new Point(3,-2)));
        KDTree tree = new KDTree(pointList);
        assertEquals(new Point(3, 1), tree.nearest(4, 0));
        assertEquals(new Point(0, -2), tree.nearest(0, -1));
    }
}
