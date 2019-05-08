package bearmaps;
import java.util.List;
import java.util.Comparator;

public class KDTree{
    private Node root;
    private int size;

    public KDTree(List<Point> points) {
        for (Point point: points) {
            if (root == null) {
                this.root = new Node(point, 0);
                size = 1;
            }
            else {
                insert(root, point);
            }
        }
    }

    private void insert(Node parent, Point point) {
        if (point == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (parent == null) {
            return new Node(point, )
        }
//        decide which dimension used to compare
        while (parent != null) {
            int cmp = pointCompare(parent, point);

        }
        int cmp = pointCompare(parent, point);
        if (cmp > 0) {

        }

    }

    Comparator<Double> intComparator = (i, j) -> {
       if (i - j > 0) {
           return 1;
       } else if (i - j < 0) {
           return -1;
       } else {
           return 0;
       }
    };

    private int pointCompare (Node parent, Point point) {
        if (parent.dimension == 0) {
            return intComparator.compare((double)parent.point.getX(), (double)point.getX());
        }
        else {
            return intComparator.compare((double)parent.point.getX(), (double)point.getX());
        }
    }

    private class Node{
        Point point;
        Node left, right;
        int dimension;
        public Node(Point point, int dimension) {
            this.point = point;
            this.dimension = dimension;
        }
    }
}


