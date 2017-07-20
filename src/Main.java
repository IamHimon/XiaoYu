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

    public static void stair_climbing2(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> floor_result;
        if (hasSLE(floors.get(0), "S")){
            System.out.println("Can not arrived through stair,as "+ floors.get(0).getFloor_name() +" has no stair!");
            return;
        }
        if (floors.size() == 1){
            for (Object s:getResultWithinFloor(floors.get(0), start_point, end_point).getValue2()){
                stair_path.add(s.toString());
            }
        }else {
            stair_path.add(start_point);

            if (start_point.contains("C")||hasSameOne(floors.get(0), start_point, is_start_point)){
                floor_result = getResultWithinFloor(floors.get(0), start_point);
                System.out.println(floor_result);
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
                System.out.println(nearest_stair);

                //起始层将点加入到path
                if (is_start_point){
                    for (int i=1;i<nearest_stair.getValue2().size();i++){
                        stair_path.add(nearest_stair.getValue2().get(i).toString());
                    }
                }
                System.out.println(nearest_stair.getValue1());
                String start_point_in_next_floor = getSameOne(floors.get(1), nearest_stair.getValue1());
                floors.remove(0);
                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);
                for (Floor f:floors)
                    System.out.println(f.getFloor_name());

                stair_climbing2(floors, start_point_in_next_floor, end_point, false);
            }else {
                String start_point_in_next_floor = getSameOne(floors.get(0), start_point);
                System.out.println("else:" + start_point_in_next_floor);
//                System.out.println(floors);
                for (Floor f:floors)
                    System.out.println(f.getFloor_name());
                floors.remove(0);
                stair_climbing2(floors, start_point_in_next_floor, end_point, false);
            }
        }
    }

    public static void lift_climbing(ArrayList<Floor> floors, String start_point, String end_point, boolean is_start_point) {
        ArrayList<Quartet<String, String, ArrayList<Object>, Double>> floor_result;
        if (hasSLE(floors.get(0), "L")) {
            System.out.println("Can not arrived through lift,as " + floors.get(0).getFloor_name() + " has no lift!");
            return;
        }
        if (floors.size() == 1) {
            for (Object s : getResultWithinFloor(floors.get(0), start_point, end_point).getValue2())
                lift_path.add(s.toString());
        } else {
            lift_path.add(start_point);

            if (start_point.contains("C") && !hasSameOne(floors.get(0), start_point, is_start_point)) {

                floor_result = getResultWithinFloor(floors.get(0), start_point);
                // 在起始楼找到与起始点最近的点
                Quartet<String, String, ArrayList<Object>, Double> nearest_lift = Quartet.with("a", "b", new ArrayList<>('a'), Double.MAX_VALUE);
                for (Quartet<String, String, ArrayList<Object>, Double> q : floor_result) {
                    // find the nearest Stair
                    if (q.getValue1().contains("L")) {
                        if (q.getValue3() < nearest_lift.getValue3()) {
                            nearest_lift = q;
                        }
                    }
                }

                //起始层将点加入到path
                if (is_start_point) {
                    for (int i = 1; i < nearest_lift.getValue2().size(); i++) {
                        lift_path.add(nearest_lift.getValue2().get(i).toString());
                    }

                    String start_point_in_next_floor = getSameOne(floors.get(1), nearest_lift.getValue1());
                    floors.remove(0);
//                System.out.println(start_point_in_next_floor);
//                System.out.println(floors);

                    lift_climbing(floors, start_point_in_next_floor, end_point, false);

                } else {
                    String start_point_in_next_floor = getSameOne(floors.get(1), start_point);
//                System.out.printf("else: " + start_point_in_next_floor);
                    floors.remove(0);
                    lift_climbing(floors, start_point_in_next_floor, end_point, false);
                }
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
                    if (q.getValue1().startsWith("E")){
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

    public static void showPath( ArrayList<Floor> floors, Floor start, String startNode, Floor end, String endNode, String way) throws Exception {
        int start_id;
        int end_id;

        switch (way) {
            case "S": {
                buildLinkedPointsPair_SLE(floors, "S");
                WeightedGraph t = buildBigGraph(floors, LinkedPointsPair_S);


                try {
                    start_id = allFloor_point_id_map.get(start.getFloor_name() + "_" + startNode);
                    end_id = allFloor_point_id_map.get(end.getFloor_name() + "_" + endNode);
                    final int[] pred = Dijkstra.dijkstra(t, start_id);
                    System.out.println("Stair path:");
                    Dijkstra.printPath(t, pred, start_id, end_id);
                } catch (Exception e) {
                    throw new Exception("The node is not exit!");
                }
                break;
            }
            case "L": {
                buildLinkedPointsPair_SLE(floors, "L");
                WeightedGraph t = buildBigGraph(floors, LinkedPointsPair_L);
                try {
                    start_id = allFloor_point_id_map.get(start.getFloor_name() + "_" + startNode);
                    end_id = allFloor_point_id_map.get(end.getFloor_name() + "_" + endNode);
                    final int[] pred = Dijkstra.dijkstra(t, start_id);
                    System.out.println("Lift path:");
                    Dijkstra.printPath(t, pred, start_id, end_id);
                } catch (Exception e) {
                    throw new Exception("The node is not exit!");
                }
                break;
            }
            case "E": {
                buildLinkedPointsPair_SLE(floors, "E");

                WeightedGraph t = buildBigGraph(floors, LinkedPointsPair_E);
                System.out.println(t.isConnected());
                print4J(allFloor_point_id_map);

                try {
                    start_id = allFloor_point_id_map.get(start.getFloor_name() + "_" + startNode);
                    end_id = allFloor_point_id_map.get(end.getFloor_name() + "_" + endNode);
                    System.out.println(start_id);
                    System.out.println(end_id);
                    final int[] pred = Dijkstra.dijkstra(t, start_id);
                    System.out.println("Escalator path:");
                    Dijkstra.printPath(t, pred, start_id, end_id);
                } catch (Exception e) {
                    throw new Exception("The node is not exit!");
                }
                break;
            }
            case "A":{
                buildLinkedPointsPair(floors);
                WeightedGraph t = buildBigGraph(floors, LinkedPointsPair_SLE);
                try {
                    start_id = allFloor_point_id_map.get(start.getFloor_name() + "_" + startNode);
                    end_id = allFloor_point_id_map.get(end.getFloor_name() + "_" + endNode);
                    final int[] pred = Dijkstra.dijkstra(t, start_id);
                    System.out.println("Anyway path:");
                    Dijkstra.printPath(t, pred, start_id, end_id);
                } catch (Exception e) {
                    throw new Exception("The node is not exit!");
                }
                break;
            }
            default:
                System.out.println("please input the correct mode:(S,L,E,A)");
        }

    }


    public static void main(String[] args) throws Exception {
//        String filename1 = "src/data/test_points1";
//        String filename2 = "src/data/test_points2";
//        String filename3 = "src/data/test_points3";
        String filename1 = "src/data/3.1.txt";
        String filename2 = "src/data/4.1.txt";
        String filename3 = "src/data/5.1.txt";

        //按顺序添加floor

        Floor floor1 = buildFloorFromFile(3, "floor3", filename1);
//        System.out.println(floor1.getStairs());
//        System.out.println(floor1.getLifts());



        Floor floor2 = buildFloorFromFile(4, "floor4", filename2);
//        System.out.println(floor2.getStairs());
//        System.out.println(floor2.getLifts());

        Floor floor3 = buildFloorFromFile(5, "floor5", filename3);
//        System.out.println(floor3.getStairs());
//        System.out.println(floor3.getLifts());
//        System.out.println(floor3.getAll_points());

        ArrayList<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);

        showPath(floors, floor1, "C2", floor3, "C19", "A");
        showPath(floors, floor1, "C1", floor3, "C27", "L");
        showPath(floors, floor1, "C2", floor3, "C20", "S");
        showPath(floors, floor1, "C5", floor3, "C10", "E");

//        buildLinkedPointsPair_SLE(floors, "S");
//        System.out.println(LinkedPointsPair_S);
//
//        buildLinkedPointsPair_SLE(floors, "L");
//        System.out.println(LinkedPointsPair_L);
////        buildLinkedPointsPair_SLE(floors, "E");
//        WeightedGraph st = buildBigGraph(floors,LinkedPointsPair_S);
//        WeightedGraph lt = buildBigGraph(floors,LinkedPointsPair_L);
//        WeightedGraph et = buildBigGraph(floors,LinkedPointsPair_E);
//        print4J(allFloor_point_id_map);
//
//        final int[] pred = Dijkstra.dijkstra(t, 60);
//
//        Dijkstra.printPath(t, pred, 60, 192);


//        print4J(floor1.getPoint_id_map());
//        print4J(floor2.getPoint_id_map());
//        print4J(floor3.getPoint_id_map());

//        stair_climbing2((ArrayList<Floor>) floors.clone(), "floor3_C8", "floor5_C23", true);
//        lift_climbing((ArrayList<Floor>) floors.clone(), "floor3_C4", "floor5_C27", true);
//        escalator_climbing((ArrayList<Floor>) floors.clone(), "floor1_C8", "floor3_C23", true);

//        System.out.println("stair path:" + stair_path);
//        System.out.println("lift path:" + lift_path);
//        System.out.println("escalator path:" + escalator_path);

    }

}

