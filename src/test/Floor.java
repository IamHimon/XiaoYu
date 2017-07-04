package test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by student on 2017/7/4.
 */
public class Floor {
    private WeightedGraph graph;
    private ArrayList<String> stairs;
    private ArrayList<String> lift;
    private ArrayList<String> escalator;

    public Floor(WeightedGraph graph, ArrayList<String> stairs, ArrayList<String> lift, ArrayList<String> escalator) {
        this.graph = graph;
        this.stairs = stairs;
        this.lift = lift;
        this.escalator = escalator;
    }

    public WeightedGraph getGraph() {
        return graph;
    }

    public void setGraph(WeightedGraph graph) {
        this.graph = graph;
    }

    public ArrayList<String> getStairs() {
        return stairs;
    }

    public void setStairs(ArrayList<String> stairs) {
        this.stairs = stairs;
    }

    public ArrayList<String> getLift() {
        return lift;
    }

    public void setLift(ArrayList<String> lift) {
        this.lift = lift;
    }

    public ArrayList<String> getEscalator() {
        return escalator;
    }

    public void setEscalator(ArrayList<String> escalator) {
        this.escalator = escalator;
    }

    public WeightedGraph buildFromFile(String filename){
        System.out.println("hehe");
        final WeightedGraph t = new WeightedGraph(6);

        return t;
    }

}
