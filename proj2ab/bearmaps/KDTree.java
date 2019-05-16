package bearmaps;
import java.util.List;

public class KDTree{
    private Node root;
    private int size;

    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException("points null")
        }
        for (Point point: points) {
            if (root == null) {
                root = new Node(point, 0);
            }
            else {

            }
        }
    }

    private void inser

    private void insert(Point point){

    }



    private class Node {
        int partitionDimention;
        Point point;
        Node left, right;
        int size;

        public Node(Point point, int size){
            this.point = point;
            this.left = null;
            this.right = null;
            this.partitionDimention = 0;
            this.size = size;
        }


    }


}

