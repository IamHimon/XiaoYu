package test;

import javax.management.ObjectName;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by student on 2017/7/4.
 * 用邻接表来表示一个无向图，labels表示某个顶点名字，邻接表大小为：vertex.size *　vertex.size
 */
public class WeightedGraph {

    private Double[][] edges;  // adjacency matrix
    private Object[] labels;

    public WeightedGraph(int n) {
        edges = new Double[n][n];
        myFill(edges, 0.0);
        labels = new Object[n];
    }


    public int size() {
        return labels.length;
    }

    public void setLabel(int vertex, Object label) {
        labels[vertex] = label;
    }

    public Object getLabel(int vertex) {
        return labels[vertex];
    }

    public void addEdge(int source, int target, Double w) {
        edges[source][target] = w;
    }

    public boolean isEdge(int source, int target) {
        return edges[source][target] > 0;
    }

    public void removeEdge(int source, int target) {
        edges[source][target] = 0.0;
    }

    public Double getWeight(int source, int target) {
        return edges[source][target];
    }

    /*
    * 当定义int[n][m]初始值为0
    * edges类型定义为double[n][m]时初始值为null，所以要逐一初始化。
    * */
    public static void myFill(Object[][] a, Object val) {
        for (int i = 0, len = a.length; i < len; i++){
            for (int j = 0; j<a[i].length;j++){
                a[i][j] = val;
            }
        }
    }

    /*
    * 求某个顶点的邻居顶点,有向图
    * */
    public int[] neighbors_directed(int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) count++;
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) answer[count++] = i;
        }
        return answer;
    }
    /*
       * 求某个顶点的邻居顶点,无向图
       * */
    public int[] neighbors_undirected (int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if ((edges[vertex][i] > 0.0) || (edges[i][vertex] > 0.0)) count++;
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if ((edges[vertex][i] > 0.0) || (edges[i][vertex] > 0.0)) answer[count++] = i;
        }
        return answer;
    }

    public void print() {

        for (int j = 0; j < edges.length; j++) {
            System.out.print(labels[j] + ": ");
            for (int i = 0; i < edges[j].length; i++) {
                if (edges[j][i] > 0) System.out.print(labels[i] + ":" + edges[j][i] + " ");
            }
            System.out.println();
        }
    }

    public Double distanceBetweenTwoPoints(Point p1, Point p2){
        double _x = Math.abs(p1.getPoint()[0] - p2.getPoint()[0]);
        double _y = Math.abs(p1.getPoint()[1] - p2.getPoint()[1]);
        return Math.sqrt(_x*_x + _y*_y);
    }

    public WeightedGraph buildGraph(ArrayList<Point[]> allPointsPair, Map<String, Integer> point_id_map){
        WeightedGraph t = new WeightedGraph(point_id_map.size());

        for (Object obj:point_id_map.keySet()){
            t.setLabel(point_id_map.get(obj), obj);
        }

        for (int i = 0; i<allPointsPair.size();i++){
            int source = point_id_map.get(allPointsPair.get(i)[0].getLabel());
            int target = point_id_map.get(allPointsPair.get(i)[1].getLabel());
            Double weight = distanceBetweenTwoPoints(allPointsPair.get(i)[0], allPointsPair.get(i)[1]);

            t.addEdge(source, target, weight);

        }
        t.print();
        return t;
    }



    public static void main(String args[]) {
        final WeightedGraph t = new WeightedGraph(6);
        t.setLabel(0, "v0");
        t.setLabel(1, "v1");
        t.setLabel(2, "v2");
        t.setLabel(3, "v3");
        t.setLabel(4, "v4");
        t.setLabel(5, "v5");
        t.addEdge(0, 1, 2.0);
        t.addEdge(0, 5, 9.0);
        t.addEdge(1, 2, 8.0);
        t.addEdge(1, 3, 15.0);
        t.addEdge(1, 5, 6.0);
        t.addEdge(2, 3, 1.0);
        t.addEdge(4, 3, 3.0);
        t.addEdge(4, 2, 7.0);
        t.addEdge(5, 3, 9.0);
        t.addEdge(5, 4, 3.0);
//        for(Double[] obj:t.edges){
//            for (Double o:obj){
////                if (o != null)
////                    System.out.println(o);
//                System.out.println(o);
//            }
//        }
        t.print();
//
        final int[] pred = Dijkstra.dijkstra(t, 0);
        for (int n = 0; n < 6; n++) {
            Dijkstra.printPath(t, pred, 0, n);
        }
    }

}
