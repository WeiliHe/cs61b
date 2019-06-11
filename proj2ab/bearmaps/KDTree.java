package bearmaps.proj2ab;
import bearmaps.proj2c.utils.Tuple;

import java.lang.Math;
import java.util.List;
import java.util.Comparator;

public class KDTree implements PointSet {
    private Node root;
    private int size;
    private int dimension = 2;

    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("points null");
        }
        for (Point point: points) {
            insert(point);
            size += 1;
        }
    }

    private void insert(Point point) {
        if (point == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = insert(root, point, 0);
    }

    private Node insert(Node x, Point point, int dim) {
        if (x == null) {
            x = new Node(point, dim);
        } else if (x.point.equals(point)) {
            x.point = point; //  decide which dimension used to compare
        } else if (pointCompare(point, x) < 0) {
            x.left = insert(x.left, point, (x.dimension + 1) % this.dimension);
        } else {
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

    private int pointCompare(Point point, Node parent) {
        if (parent.dimension == 0) {
            return intComparator.compare((double) point.getX(), (double) parent.point.getX());
        } else {
            return intComparator.compare((double) point.getY(), (double) parent.point.getY());
        }
    }

//    helper function for getting the best possible point from badside,
//    just use one dimension comparison
    private double distanceFromBadside(Node n, Point goal) {
        if (n.dimension == 0) {
            return Math.abs(goal.getX() - n.point.getX());
        } else {
            return Math.abs(goal.getY() - n.point.getY());
        }
    }

    private Tuple<Node, Double> nearest(Node n, Point goal, Tuple<Node, Double> bestNodeAnddist) {
        if (n == null) {
            return bestNodeAnddist;
        }
        double currDist = n.point.distance(n.point, goal);
        if (currDist <= bestNodeAnddist.getSecond()) {
            bestNodeAnddist.setFirst(n);
            bestNodeAnddist.setSecond(currDist);
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
        bestNodeAnddist = nearest(goodSide, goal, bestNodeAnddist);
        if (badSide != null) {
            if (distanceFromBadside(badSide, goal) <= bestNodeAnddist.getSecond()) {
                bestNodeAnddist = nearest(badSide, goal, bestNodeAnddist);
            }
        }
        return bestNodeAnddist;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
//        Node nearestNode = nearest(root, goal, root, goal.distance(root.point, goal));
        Tuple<Node, Double> nearestTuple = nearest(root, goal, new Tuple<Node, Double>(root, goal.distance(root.point, goal)));
        return nearestTuple.getFirst().point;

    }

    private class Node {
        Point point;
        Node left, right;
        int dimension;

        Node(Point point, int dimension) {
            this.point = point;
            this.dimension = dimension;
        }
    }
}



