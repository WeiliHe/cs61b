package bearmaps;
import java.util.List;
import java.util.Comparator;

public class KDTree implements PointSet{
    private Node root;
    private int size;
    private int dimension = 2;

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
//        decide which dimension used to compare
        int cmp = pointCompare(point, parent);
        if (cmp < 0) {
            if (parent.left == null) {
                parent.left = new Node(point, (parent.dimension + 1) % this.dimension);
            }
            else {
                insert(parent.left, point);
            }
        }
        if (parent.point.equals(point)) {
            parent.point = point;
        }
        else {
            if (parent.right == null) {
                parent.right = new Node(point, (parent.dimension + 1) % this.dimension);
            }
            else {
                insert(parent.right, point);
            }
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

    private int pointCompare (Point point, Node parent) {
        if (parent.dimension == 0) {
            return intComparator.compare((double)parent.point.getX(), (double)point.getX());
        }
        else {
            return intComparator.compare((double)parent.point.getX(), (double)point.getX());
        }
    }

    @Override
    public Point nearest(double x, double y) {

    }

    private class Node {
        Point point;
        Node left, right;
        int dimension;

        public Node(Point point, int dimension) {
            this.point = point;
            this.dimension = dimension;
        }
    }
}



