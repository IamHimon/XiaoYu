import org.javatuples.Quartet;
import test.Dijkstra;
import test.Floor;
import test.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static test.Utils.*;

public class Main {
    public static ArrayList<String> stair_path = new ArrayList<>();

    public static void climbing(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point){

        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor_result;
        if (floors.size() == 1){
//            floor_result = getResultWithinFloor(floors.get(0), start_point, end_point);
//            System.out.println(getResultWithinFloor(floors.get(0), start_point, end_point).getValue2());
            for (Object s:getResultWithinFloor(floors.get(0), start_point, end_point).getValue2()){
                stair_path.add(s.toString());
            }
        }else {
            stair_path.add(start_point);

            if (start_point.startsWith("C")&&!hasSameOne(floors.get(0), start_point, is_start_point)){

                floor_result = getResultWithinFloor(floors.get(0), start_point);
                // 在起始楼找到与起始点最近的点
                Quartet<String, String, ArrayList<Object>,Double> nearest_stair = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                Quartet<String, String, ArrayList<Object>,Double> nearest_lift = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                Quartet<String, String, ArrayList<Object>,Double> nearest_escalator = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                for (Quartet<String, String, ArrayList<Object>,Double> q:floor_result){
                    // find the nearest Stair
                    if (q.getValue1().startsWith("S")){
                        if (q.getValue3() < nearest_stair.getValue3()){
                            nearest_stair = q;
                        }
                    }else if(q.getValue1().startsWith("L")){
                        if (q.getValue3() < nearest_lift.getValue3()){
                            nearest_lift = q;
                        }
                    }else if(q.getValue1().startsWith("E")){
                        if (q.getValue3() < nearest_escalator.getValue3()){
                            nearest_escalator = q;
                        }
                    }
                }
//                System.out.println(nearest_stair);
//                System.out.println(floors);
//            System.out.println(nearest_lift);
//            System.out.println(nearest_escalator);
                //起始层将点加入到path
                if (is_start_point){
                    for (int i=1;i<nearest_stair.getSize()-1;i++){
                        stair_path.add(nearest_stair.getValue2().get(i).toString());
                    }
                }

                String start_point_in_next_floor = getSameOne(floors.get(1), nearest_stair.getValue1());
                floors.remove(0);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);

                climbing(floors, start_point_in_next_floor,end_point,false);
//            climbing(floors, nearest_stair.getValue1(), nearest_escalator);
//            climbing(floors, nearest_stair.getValue1(), nearest_escalator);

            }else {
                String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);
                floors.remove(0);
                climbing(floors, start_point_in_next_floor,end_point,false);
            }
        }



    }

    public static void main(String[] args) throws Exception {
        String filename1 = "src/data/test_points1";
        String filename2 = "src/data/test_points2";
        String filename3 = "src/data/test_points3";
//        String filename3 = "src/data/3楼";
//        String filename4 = "src/data/4楼";
//        String filename5 = "src/data/5楼";

        /*一层*/
        Floor floor1 = buildFloorFromFile("floor_1", filename1);
//        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor1_result = getResultWithinFloor(floor1, "C1");
//        System.out.println(floor1_result);

        // 在起始楼找到与起始点最近的点
       /* Quartet<String, String, ArrayList<Object>,Double> nearest_stair = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
        Quartet<String, String, ArrayList<Object>,Double> nearest_lift = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
        Quartet<String, String, ArrayList<Object>,Double> nearest_escalator = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
        for (Quartet<String, String, ArrayList<Object>,Double> q:floor1_result){
            // find the nearest Stair
            if (q.getValue1().startsWith("S")){
                if (q.getValue3() < nearest_stair.getValue3()){
                    nearest_stair = q;
                }
            }else if(q.getValue1().startsWith("L")){
                if (q.getValue3() < nearest_lift.getValue3()){
                    nearest_lift = q;
                }
            }else if(q.getValue1().startsWith("E")){
                if (q.getValue3() < nearest_escalator.getValue3()){
                    nearest_escalator = q;
                }
            }

        }
        System.out.println(nearest_stair);
        System.out.println(nearest_lift);
        System.out.println(nearest_escalator);

*/
        /*二层*/
        Floor floor2 = buildFloorFromFile("floor_2", filename2);
//        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor2_result = getResultWithinFloor(floor2);
//        System.out.println(floor2_result);


        /*三层*/
        Floor floor3 = buildFloorFromFile("floor_3", filename3);
//        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor3_result = getResultWithinFloor(floor2);
//        System.out.println(floor3_result);

        ArrayList<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);

        climbing(floors, "C1", "C3", true);

        System.out.println(stair_path);

//        System.out.println(hasSameOne(floors.get(0), "C1", true));





    }

}

