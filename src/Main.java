import org.javatuples.Quartet;
import test.Dijkstra;
import test.Floor;
import test.Point;
import test.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static test.Utils.*;

public class Main {
    public static ArrayList<String> path = new ArrayList<>();
    public static ArrayList<String> stair_path = new ArrayList<>();
    public static ArrayList<String> lift_path = new ArrayList<>();
    public static ArrayList<String> escalator_path = new ArrayList<>();

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

                climbing(floors, start_point_in_next_floor, end_point, false);
//            climbing(floors, nearest_stair.getValue1(), nearest_escalator);
//            climbing(floors, nearest_stair.getValue1(), nearest_escalator);

            }else {
                String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);
                floors.remove(0);
                climbing(floors, start_point_in_next_floor, end_point, false);
            }
        }
    }

    /*只通过stair的方式的最短路径，策略是：先从出发层找到最近stair，在层间判断是不是有同一个stair，如果有直接上楼
    * 如果没有，在找到最近stair，知道到目标层，然后在目标层从stair出发规划到目标点的路径.
    * 如果是普通点在前面加一个前缀：这一层的floor_name
    * */
    public static void stair_climbing(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor_result;
        if (hasSLE(floors.get(0), "S")){
            System.out.println("Can not arrived through stair,as "+ floors.get(0).getFloor_name() +" has no stair!");
            return;
        }
        if (floors.size() == 1){
            for (Object s:getResultWithinFloor(floors.get(0), start_point, end_point).getValue2()){
                if (s.toString().startsWith("C"))
                    stair_path.add(floors.get(0).getFloor_name() +"_"+ s.toString());
                else
                    stair_path.add(s.toString());
            }
        }else {
            if (start_point.startsWith("C"))
                stair_path.add(floors.get(0).getFloor_name() +"_"+ start_point);
            else
                stair_path.add(start_point);


            if (start_point.startsWith("C")&&!hasSameOne(floors.get(0), start_point, is_start_point)){
                floor_result = getResultWithinFloor(floors.get(0), start_point);
                // 在起始楼找到与起始点最近的点
                Quartet<String, String, ArrayList<Object>,Double> nearest_stair = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                for (Quartet<String, String, ArrayList<Object>,Double> q:floor_result){
                    // find the nearest Stair
                    if (q.getValue1().startsWith("S")){
                        if (q.getValue3() < nearest_stair.getValue3()){
                            nearest_stair = q;
                        }
                    }
                }

                //起始层将点加入到path
                if (is_start_point){
                    for (int i=1;i<nearest_stair.getSize()-1;i++){
                        if (nearest_stair.getValue2().get(i).toString().startsWith("C"))
                            stair_path.add(floors.get(0).getFloor_name() + "_" +nearest_stair.getValue2().get(i).toString());
                        else
                            stair_path.add(nearest_stair.getValue2().get(i).toString());
                    }
                }

                String start_point_in_next_floor = getSameOne(floors.get(1), nearest_stair.getValue1());
                floors.remove(0);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);

                stair_climbing(floors, start_point_in_next_floor,end_point,false);
            }else {
                String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);
                floors.remove(0);
                stair_climbing(floors, start_point_in_next_floor, end_point, false);
            }
        }
    }

    public static void lift_climbing(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor_result;
        if (hasSLE(floors.get(0), "L")){
            System.out.println("Can not arrived through lift,as "+ floors.get(0).getFloor_name() +" has no lift!");
            return;
        }
        if (floors.size() == 1){
            for (Object s:getResultWithinFloor(floors.get(0), start_point, end_point).getValue2()){
//                lift_path.add(s.toString());
                if (s.toString().startsWith("C"))
                    lift_path.add(floors.get(0).getFloor_name() +"_"+ s.toString());
                else
                    lift_path.add(s.toString());
            }
        }else {
            if (start_point.startsWith("C"))
                lift_path.add(floors.get(0).getFloor_name() +"_"+ start_point);
            else
                lift_path.add(start_point);

            if (start_point.startsWith("C")&&!hasSameOne(floors.get(0), start_point, is_start_point)){

                floor_result = getResultWithinFloor(floors.get(0), start_point);
                // 在起始楼找到与起始点最近的点
                Quartet<String, String, ArrayList<Object>,Double> nearest_lift = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                for (Quartet<String, String, ArrayList<Object>,Double> q:floor_result){
                    // find the nearest Stair
                    if(q.getValue1().startsWith("L")){
                        if (q.getValue3() < nearest_lift.getValue3()){
                            nearest_lift = q;
                        }
                    }
                }

                //起始层将点加入到path
                if (is_start_point){
                    for (int i=1;i<nearest_lift.getSize()-1;i++){
                        if (nearest_lift.getValue2().get(i).toString().startsWith("C"))
                            lift_path.add(floors.get(0).getFloor_name() + "_" +nearest_lift.getValue2().get(i).toString());
                        else
                            lift_path.add(nearest_lift.getValue2().get(i).toString());
                    }
                }

                String start_point_in_next_floor = getSameOne(floors.get(1), nearest_lift.getValue1());
                floors.remove(0);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);

                lift_climbing(floors, start_point_in_next_floor,end_point,false);

            }else {
                String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.printf("else: " + start_point_in_next_floor);
                floors.remove(0);
                lift_climbing(floors, start_point_in_next_floor, end_point, false);
            }
        }
    }

    public static void escalator_climbing(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point){

        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor_result;
        if (hasSLE(floors.get(0), "E")){
            System.out.println("Can not arrived through escalator, as "+ floors.get(0).getFloor_name() +" has no escalator!");
            return;
        }
        if (floors.size() == 1){
            for (Object s:getResultWithinFloor(floors.get(0), start_point, end_point).getValue2()){
//                escalator_path.add(s.toString());
                if (s.toString().startsWith("C"))
                    escalator_path.add(floors.get(0).getFloor_name() +"_"+ s.toString());
                else
                    escalator_path.add(s.toString());
            }
        }else {
//            escalator_path.add(start_point);
            if (start_point.startsWith("C"))
                escalator_path.add(floors.get(0).getFloor_name() +"_"+ start_point);
            else
                escalator_path.add(start_point);

            if (start_point.startsWith("C")&&!hasSameOne(floors.get(0), start_point, is_start_point)){

                floor_result = getResultWithinFloor(floors.get(0), start_point);
                // 在起始楼找到与起始点最近的点
                Quartet<String, String, ArrayList<Object>,Double> nearest_escalator = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);

                for (Quartet<String, String, ArrayList<Object>,Double> q:floor_result){
                    // find the nearest Stair
                    if(q.getValue1().startsWith("E")){
                        if (q.getValue3() < nearest_escalator.getValue3()){
                            nearest_escalator = q;
                        }
                    }
                }

                //起始层将点加入到path
                if (is_start_point){
                    for (int i=1;i<nearest_escalator.getSize()-1;i++){
//                        escalator_path.add(nearest_escalator.getValue2().get(i).toString());
                        if (nearest_escalator.getValue2().get(i).toString().startsWith("C"))
                            escalator_path.add(floors.get(0).getFloor_name() + "_" +nearest_escalator.getValue2().get(i).toString());
                        else
                            escalator_path.add(nearest_escalator.getValue2().get(i).toString());
                    }
                }

                String start_point_in_next_floor = getSameOne(floors.get(1), nearest_escalator.getValue1());
                floors.remove(0);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);

                escalator_climbing(floors, start_point_in_next_floor, end_point, false);

            }else {
                String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.println("else: " + start_point_in_next_floor);
                floors.remove(0);
                escalator_climbing(floors, start_point_in_next_floor, end_point, false);
            }
        }
    }


    public static void main(String[] args) throws Exception {
//        String filename1 = "src/data/test_points1";
//        String filename2 = "src/data/test_points2";
//        String filename3 = "src/data/test_points3";
        String filename1 = "src/data/3.1.txt";
        String filename2 = "src/data/4.1.txt";
        String filename3 = "src/data/5.1.txt";
//        Floor floor3l = buildFloorFromFile("floor_3l", filename3l);
//        Floor floor4l = buildFloorFromFile("floor_4l", filename4l);
//        Floor floor5l = buildFloorFromFile("floor_5l", filename5l);
//        floor3l.describeFloor();
//        floor4l.describeFloor();
//        floor5l.describeFloor();
//        String filename4 = "src/data/4楼";
//        String filename5 = "src/data/5楼";

        //按顺序添加floor

        Floor floor1 = buildFloorFromFile(1, "floor1", filename1);

        Floor floor2 = buildFloorFromFile(2, "floor2", filename2);

        Floor floor3 = buildFloorFromFile(3, "floor3", filename3);

        ArrayList<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);

        WeightedGraph t = buildBigGraph(floors);

        final int[] pred = Dijkstra.dijkstra(t, 0);

        Dijkstra.printPath(t, pred, 0, 187);

//
//        Double shortestEdges = getShortestEdges(floors);
//        System.out.println(shortestEdges);


//        stair_climbing((ArrayList<Floor>) floors.clone(), "C1", "C3", true);
//        lift_climbing((ArrayList<Floor>) floors.clone(), "C1", "C3", true);
//        escalator_climbing((ArrayList<Floor>) floors.clone(), "C1", "C3", true);

//        System.out.println("stair path:" + stair_path);
//        System.out.println("lift path:" + lift_path);
//        System.out.println("escalator path:" + escalator_path);



    }

}

