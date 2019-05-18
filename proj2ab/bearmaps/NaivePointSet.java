package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
	private List<Point> points;

	public NaivePointSet(List<Point> points) {
		this.points = points;
	}

	public Point nearest(double x, double y) {
		double smallestDistance = -1;
		Point nearestPoint = new Point(x, y);
		Point m = new Point(x, y);
		for (Point p : points) {
			double dis = p.distance(p, m);
			if (dis < smallestDistance || smallestDistance < 0) {
				smallestDistance = dis;
				nearestPoint = p;
			}
		}
		return nearestPoint;
	}
}