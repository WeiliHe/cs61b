package bearmaps;
import java.lang.Math;
import java.util.List;
import java.util.Comparator;

public class KDTree implements PointSet{
    private Node root;
    private int size;
    private int dimension =2;

    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("points null");
        }
        for (Point point: points) {
            insert(point);
            size += 1;
        }
    }

    private void insert(Point point){
        if (point == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = insert(root, point, 0);
    }

    private Node insert(Node x, Point point, int dim) {
        if (x == null) {
            x = new Node(point, dim);
        }
//        decide which dimension used to compare
        else if (x.point.equals(point)) {
            x.point = point;
        }
        else if (pointCompare(point, x) < 0) {
            x.left = insert(x.left, point, (x.dimension + 1) % this.dimension);
        }
        else {
            x.right = insert(x.right, point, (x.dimension + 1) % this.dimension);
        }
        return x;
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
            return intComparator.compare((double)point.getX(), (double)parent.point.getX());
        }
        else {
            return intComparator.compare((double)point.getY(), (double)parent.point.getY());
        }
    }

//    helper function for getting the best possible point from badside
    private Point getRightRect(Node n, Point goal, Node parent){
        if (n.dimension == 0) {
            double x = n.point.getX();
            if (goal)
            double x = Math.max(goal.getX())
        }
        double y = n.point.getY();
        double x = ()
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (n.point.distance(n.point, goal) < best.point.distance(best.point, goal)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (pointCompare(goal, n) < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        if (badSide != null) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node nearestNode = nearest(root, goal, root);
        return nearestNode.point;
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



