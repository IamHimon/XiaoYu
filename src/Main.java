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
        String filename3 = "src/data/3楼";
        String filename4 = "src/data/4楼";
        String filename5 = "src/data/5楼";

        Floor floor1 = buildFloorFromFile("floor_1",filename1);
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor1_result = getResultWithinFloor(floor1, "C1");
        System.out.println(floor1_result);

        // 找到最近的点
        Double temp = 0.0;
        for (Quartet<String, String, ArrayList<Object>,Double> q:floor1_result){
            if (temp > q.getValue3()){
                temp = q.getValue3();
            }
        }

//        Floor floor2 = buildFloorFromFile("floor_2",filename2);
//        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor2_result = getResultWithinFloor(floor2);
//        System.out.println(floor2_result);



        //用stair：Sa
//        for (Quartet<String, String, ArrayList<Object>,Double> q:floor1_result){
//            if(q.getValue1().startsWith("S") && q.getValue1().endsWith("a")){
////                System.out.println(q);
//                ArrayList<Quartet<String, String, ArrayList<Object>,Double>> _result = getResultWithinFloor(floor2, "S2a", "C5");
//                System.out.println(_result);
//
//            }
//        }

//        Floor floor3 = buildFloorFromFile("3楼", filename3);
//        floor3.describeFloor();
//        Floor floor4 = buildFloorFromFile("4楼", filename3);
//        floor4.describeFloor();
//        Floor floor5 = buildFloorFromFile("5楼", filename3);
//        floor5.describeFloor();




    }

}

