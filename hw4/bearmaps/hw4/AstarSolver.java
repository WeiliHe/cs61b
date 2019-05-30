package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight = 0;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent;
    private int numStates = 0;
    private HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private HashSet<Vertex> marked = new HashSet<>();
    private DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
    private Vertex start;
    private Vertex end;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        this.start = start;
        this.end = end;
        distTo.put(start, 0.0);
        fringe.add(start, distTo.get(start));
        while (fringe.size() != 0 && sw.elapsedTime() < timeout) {
            Vertex v = fringe.removeSmallest();
            numStates += 1;
            if (v.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                solutionWeight = distTo.get(v);
                return;
            }
            marked.add(v);
//          relax all edges outgoing from v
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(v);
            for (WeightedEdge e: neighborEdges) {
                Vertex eTo = (Vertex) e.to();
                Vertex eFrom = (Vertex) e.from();
                if (marked.contains(eTo)) {
                    continue;
                }
                if (!distTo.containsKey(eTo)) {
                    double dist = distTo.get(eFrom) + e.weight();
                    distTo.put(eTo, dist);
                    edgeTo.put(eTo, eFrom);
                    fringe.add(eTo, dist + input.estimatedDistanceToGoal(eTo, end));
                } else {
                    double dist = distTo.get(eFrom) + e.weight();
                    if (distTo.get(eTo) > dist) {
                        distTo.put(eTo, dist);
                        edgeTo.put(eTo, eFrom);
                        fringe.changePriority(eTo, dist + input.estimatedDistanceToGoal(eTo, end));
                    }
                }
            }
        }
        if (sw.elapsedTime() >= timeout) {
            outcome = SolverOutcome.TIMEOUT;
            timeSpent = timeout;
            solution = getSolution();
        } else {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
        }
    }

    private List<Vertex> getSolution() {
        if (outcome != SolverOutcome.SOLVED) {
            return new ArrayList<>();
        }
        List<Vertex> path = new ArrayList<>();
        for (Vertex x = this.end; x != this.start; x = edgeTo.get(x)) {
            path.add(x);
        }
        path.add(this.start);
        Collections.reverse(path);
        return path;
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        solution = getSolution();
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStates;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
