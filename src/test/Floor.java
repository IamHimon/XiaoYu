package test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static test.Utils.print4J;

/**
 * Created by student on 2017/7/4.
 * 代表楼层的类
 *所有可行点组成一个WeightedGraph。
 * 然后电梯，楼梯，扶梯分别构成数组。
 */
public class Floor {
    private String floor_name;
    private WeightedGraph graph;
    private ArrayList<String> common_points;
    private ArrayList<String> stairs;
    private ArrayList<String> lifts;
    private ArrayList<String> escalators;
    private HashMap<String, Integer> point_id_map;

    public Floor() {
    }


    public Floor(String floor_name,WeightedGraph graph,ArrayList<String> common_points, ArrayList<String> stairs, ArrayList<String> lifts, ArrayList<String> escalators, HashMap<String, Integer> point_id_map) {
        this.floor_name = floor_name;
        this.graph = graph;
        this.common_points = common_points;
        this.stairs = stairs;
        this.lifts = lifts;
        this.escalators = escalators;
        this.point_id_map = point_id_map;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public WeightedGraph getGraph() {
        return graph;
    }

    public void setGraph(WeightedGraph graph) {
        this.graph = graph;
    }

    public ArrayList<String> getCommon_points() {
        return common_points;
    }

    public void setCommon_points(ArrayList<String> common_points) {
        this.common_points = common_points;
    }

    public ArrayList<String> getStairs() {
        return stairs;
    }

    public void setStairs(ArrayList<String> stairs) {
        this.stairs = stairs;
    }

    public ArrayList<String> getLifts() {
        return lifts;
    }

    public void setLifts(ArrayList<String> lifts) {
        this.lifts = lifts;
    }

    public ArrayList<String> getEscalators() {
        return escalators;
    }

    public void setEscalators(ArrayList<String> escalators) {
        this.escalators = escalators;
    }

    public HashMap<String, Integer> getPoint_id_map() {
        return point_id_map;
    }

    public void setPoint_id_map(HashMap<String, Integer> point_id_map) {
        this.point_id_map = point_id_map;
    }

    public void describeFloor(){
        System.out.println("Floor: " + this.floor_name);
        System.out.println("points ID map:");
        print4J(this.point_id_map);
        System.out.println("WeightedGraph:");
        graph.print();
        System.out.println("common_points");
        System.out.println(common_points);
        System.out.println("stairs:");
        System.out.println(stairs.toString());
        System.out.println("lifts:");
        System.out.println(lifts.toString());
        System.out.println("escalators:");
        System.out.println(escalators.toString());
    }


}
