import org.javatuples.Quartet;
import test.Dijkstra;
import test.Floor;
import test.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;

import static test.Utils.*;

public class Main {

//    public static String climbing(ArrayList<Floor> floors, String start_point){
//
//    }

    public static void main(String[] args) throws Exception {
        String filename1 = "src/data/test_points1";
        String filename2 = "src/data/test_points2";
        String filename = "C:\\Users\\student\\Desktop\\xiaoyu\\数据\\12.txt";
        Floor floor1 = buildFloorFromFile("floor_1",filename1);
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor1_result = getResultWithinFloor(floor1, "C1");
        System.out.println(floor1_result);

        Floor floor2 = buildFloorFromFile("floor_2",filename2);
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor2_result = getResultWithinFloor(floor2);
        System.out.println(floor2_result);

        //用stair：Sa
        for (Quartet<String, String, ArrayList<Object>,Double> q:floor1_result){
            if(q.getValue1().startsWith("S") && q.getValue1().endsWith("a")){
                System.out.println(q);
                ArrayList<Quartet<String, String, ArrayList<Object>,Double>> _result = getResultWithinFloor(floor2, "S2a", "C5");
                System.out.println(_result);

            }
        }





//        WeightedGraph t = floor1.getGraph();
//        if (t.isConnected()){
//            final int[] pred = Dijkstra.dijkstra(t, 0);
//            System.out.println(Arrays.toString(pred));
//            for (int n = 0; n < t.vexs; n++) {
//                Dijkstra.printPath(t, pred, 0, n);
//            }
//        }else {
//            System.out.println(" The graph is not connected!");
//        }
//        Floor floor2 = buildFloorFromFile(filename2);
//        floor2.describeFloor();
//        ArrayList path = new ArrayList();
//        path.add("C1");
//        path.add("C2");
//        path.add("L1a");
//        Quartet<String, String, ArrayList<Object>,Double> q = Quartet.with("C1","L1", path,2.33);
//        System.out.println(q);
//        System.out.println(q.getValue0());
//        System.out.println(q.getValue1());
//        System.out.println(q.getValue2().toString());
//        System.out.println(q.getValue3());

    }

}
