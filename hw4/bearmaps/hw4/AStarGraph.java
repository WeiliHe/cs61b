package bearmaps.hw4;

import java.util.List;

/**
 * Represents a graph of vertices.
 * Created by hug.
 */
//forget about the .vertices. only use the method in the inteface
public interface AStarGraph<Vertex>{
    List<WeightedEdge<Vertex>> neighbors(Vertex v);
    double estimatedDistanceToGoal(Vertex s, Vertex goal);
}
