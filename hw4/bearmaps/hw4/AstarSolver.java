package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private HashMap<Vertex, Boolean> marked = new HashMap<>();
    private ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        distTo.put(start, 0.0);
        fringe.add(start, distTo.get(start));
        while (fringe.size() != 0) {
            Vertex v = fringe.removeSmallest();
            WeightedEdge<Vertex> e = input.neighbors(v);
        }
    }
    public SolverOutcome outcome() {

    }
    public List<Vertex> solution()
    public double solutionWeight()
    public int numStatesExplored()
    public double explorationTime()
}
