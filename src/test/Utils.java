package test;

import org.javatuples.Quartet;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by student on 2017/7/5.
 * 工具类
 */
public class Utils {


    public static Floor buildFloorFromFile(String floor_name, String filename)throws Exception{
        String regex = "^\\[[A-Z].+\\#[0-9]+(\\.[0-9]+)?\\,[0-9]+(\\.[0-9]+)?\\]\\*\\[[A-Z].+\\#[0-9]+(\\.[0-9]+)?\\,[0-9]+(\\.[0-9]+)?\\]$";

        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        String str;
        ArrayList<Point[]> allPointsPair = new ArrayList<>();
        List<Point> allPoints = new ArrayList<>();
        List<Point> setPoints = new ArrayList<>();
        HashMap<String, Integer> point_id_map = new HashMap<>();

        try{
            inputStream = new FileInputStream(filename);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((str = bufferedReader.readLine()) != null)
            {
                if (Pattern.matches(regex, str)){
                    String[] line = str.split("\\*");
                    Point p1 = buildPoint(line[0]);
//                    p1.printPoint();
                    allPoints.add(p1);

                    Point p2 = buildPoint(line[1]);
//                    p2.printPoint();
                    allPoints.add(p2);
                    allPointsPair.add(new Point[]{p1, p2});
                }else {
                    throw new Exception("data: '" + str + "' is not correct format," +" Please modify it!");
                }
            }
//            System.out.println(allPointsPair.size());
//            System.out.println(allPoints.size());
            // 去掉重复点
            boolean isin;
            for (Point point:allPoints){
                isin = true;
                for(Point p: setPoints){
                    if (isEqual(point, p)){
                        isin = false;
                    }
                }
                if(isin){
                    setPoints.add(point);
                }
            }
//            System.out.println(setPoints.size());

            //在这边检测是不是有错误数据
            if (hasErrorPoint(setPoints)){
                throw new Exception("There are two points inconsistent!");
            }

            for (int i=0;i<setPoints.size();i++){
                point_id_map.put(setPoints.get(i).getLabel(), i);
            }
            allPoints.clear();

        }catch (FileNotFoundException e){
            System.out.println("找不到指定文件");
        }catch (IOException e){
            System.out.println("读取文件失败");
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 读取完毕，构造WeightedGraph
//        print4J(point_id_map);

        //构造stairs，lifts，escalators
        ArrayList<String> common_points = new ArrayList<>();
        ArrayList<String> stairs = new ArrayList<>();
        ArrayList<String> lifts = new ArrayList<>();
        ArrayList<String> escalators = new ArrayList<>();
        for (String obj:point_id_map.keySet()){
            if(obj.startsWith("S"))
                stairs.add(obj);
            else if (obj.startsWith("L"))
                lifts.add(obj);
            else if (obj.startsWith("E"))
                escalators.add(obj);
            else if (obj.startsWith(""))
                common_points.add(obj);
        }

//        System.out.println(stairs.toString());
//        System.out.println(lifts.toString());
//        System.out.println(escalators.toString());


        WeightedGraph t = new WeightedGraph(point_id_map.size());
        for (Object obj:point_id_map.keySet()){
            t.setLabel(point_id_map.get(obj), obj);
        }

        for (int i = 0; i<allPointsPair.size();i++){
            int source = point_id_map.get(allPointsPair.get(i)[0].getLabel());
            int target = point_id_map.get(allPointsPair.get(i)[1].getLabel());
            Double weight = distanceBetweenTwoPoints(allPointsPair.get(i)[0], allPointsPair.get(i)[1]);

            t.addEdge(source, target, weight);
            // 无向图
            t.addEdge(target, source, weight);
        }

        return new Floor(floor_name, t,common_points, stairs,lifts,escalators, point_id_map);
    }

    /*
* 读文件，构建WeightedGraph。
* 文件格式：[C1#22.3,1.3]*[C2#12.3,45.6]， [],[]代表两个可以走通的点，放在一行来表示一条路线。
* 每个[]内，#之前部分表示点的类别（名称），#后面表示点的坐标。
* */
    public static WeightedGraph buildFromFile(String filename)throws Exception{
        String regex = "^\\[[A-Z].+\\#[0-9]+(\\.[0-9]+)?\\,[0-9]+(\\.[0-9]+)?\\]\\*\\[[A-Z].+\\#[0-9]+(\\.[0-9]+)?\\,[0-9]+(\\.[0-9]+)?\\]$";

        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        String str;
        ArrayList<Point[]> allPointsPair = new ArrayList<>();
        List<Point> allPoints = new ArrayList<>();
        List<Point> setPoints = new ArrayList<>();
        Map<String, Integer> point_id_map = new HashMap<>();

        try{
            inputStream = new FileInputStream(filename);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((str = bufferedReader.readLine()) != null)
            {
                if (Pattern.matches(regex, str)){
                    String[] line = str.split("\\*");
                    Point p1 = buildPoint(line[0]);
//                    p1.printPoint();
                    allPoints.add(p1);

                    Point p2 = buildPoint(line[1]);
//                    p2.printPoint();
                    allPoints.add(p2);
                    allPointsPair.add(new Point[]{p1, p2});
                }else {
                    throw new Exception("data: '" + str + "' is not correct format," +" Please modify it!");
                }
            }
//            System.out.println(allPointsPair.size());
//            System.out.println(allPoints.size());
            // 去掉重复点
            boolean isin;
            for (Point point:allPoints){
                isin = true;
                for(Point p: setPoints){
                    if (isEqual(point, p)){
                        isin = false;
                    }
                }
                if(isin){
                    setPoints.add(point);
                }
            }
//            System.out.println(setPoints.size());

            //在这边检测是不是有错误数据
            if (hasErrorPoint(setPoints)){
                throw new Exception("There are two points inconsistent!");
            }

            for (int i=0;i<setPoints.size();i++){
                point_id_map.put(setPoints.get(i).getLabel(), i);
            }
            allPoints.clear();

        }catch (FileNotFoundException e){
            System.out.println("找不到指定文件");
        }catch (IOException e){
            System.out.println("读取文件失败");
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 读取完毕，构造WeightedGraph
        print4J(point_id_map);

        //构造stairs，lifts，escalators
        List<String> stairs = new ArrayList<>();
        List<String> lifts = new ArrayList<>();
        List<String> escalators = new ArrayList<>();
        for (String obj:point_id_map.keySet()){
            if(obj.startsWith("S"))
                stairs.add(obj);
            else if (obj.startsWith("L"))
                lifts.add(obj);
            else if (obj.startsWith("E"))
                escalators.add(obj);
        }

        System.out.println(stairs.toString());
        System.out.println(lifts.toString());
        System.out.println(escalators.toString());


        WeightedGraph t = new WeightedGraph(point_id_map.size());
        for (Object obj:point_id_map.keySet()){
            t.setLabel(point_id_map.get(obj), obj);
        }

        for (int i = 0; i<allPointsPair.size();i++){
            int source = point_id_map.get(allPointsPair.get(i)[0].getLabel());
            int target = point_id_map.get(allPointsPair.get(i)[1].getLabel());
            Double weight = distanceBetweenTwoPoints(allPointsPair.get(i)[0], allPointsPair.get(i)[1]);

            t.addEdge(source, target, weight);
            // 无向图
            t.addEdge(target, source, weight);
        }
        return t;
    }


    private static Point buildPoint(String line){
        Point p = new Point();
        String[] split_lines = line.substring(line.indexOf("[")+1, line.indexOf("]")).split("#");
        p.setLabel(split_lines[0]);
        Double[] point = new Double[2];
        int i = 0;
        for (String s:split_lines[1].split(",")){
            point[i++] = Double.valueOf(s);
        }
        p.setPoint(point);

        return p;
    }


    private static boolean isEqual(Point p1, Point p2){
        return (p1.getLabel().equals(p2.getLabel())) && (p1.getPoint()[0].equals(p2.getPoint()[0]))
                && (p1.getPoint()[1].equals(p2.getPoint()[1]));
    }

    private static Double distanceBetweenTwoPoints(Point p1, Point p2){
        double _x = Math.abs(p1.getPoint()[0] - p2.getPoint()[0]);
        double _y = Math.abs(p1.getPoint()[1] - p2.getPoint()[1]);
        return Math.sqrt(_x*_x + _y*_y);
    }

    public static void print4J(Map map){
        Iterator iter = map.entrySet().iterator();
        System.out.print("{ ");
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.print(key + ":" + val + ", ");
        }
        System.out.println("}");
    }
//    private static void print4J(Object obj){
//        if (obj instanceof Map){
//            Iterator iter = obj.entrySet().iterator();
//            System.out.print("{ ");
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
//                Object val = entry.getValue();
//                System.out.print(key + ":" + val + ", ");
//            }
//            System.out.println("}");
//        }
//    }

    //检测是不是有错误数据，比如lable相同，但是坐标不同,或者坐标相同但是label不同
    private static boolean hasErrorPoint( List<Point> setPoints){
        boolean hasError = false;
        for (Point anchorPoint:setPoints){
            for (Point testPoint:setPoints){
                if (testPoint.getLabel().equals(anchorPoint.getLabel()) &&
                        ((!testPoint.getPoint()[0].equals(anchorPoint.getPoint()[0])) ||
                                (!testPoint.getPoint()[1].equals(anchorPoint.getPoint()[1])))){
                    System.out.println("Error:");
                    anchorPoint.printPoint();
                    testPoint.printPoint();
                    hasError = true;
                }
                if(((testPoint.getPoint()[0].equals(anchorPoint.getPoint()[0])) &&
                        (testPoint.getPoint()[1].equals(anchorPoint.getPoint()[1]))) &&
                        (!testPoint.getLabel().equals(anchorPoint.getLabel()))){
                    System.out.println("Error:");
                    anchorPoint.printPoint();
                    testPoint.printPoint();
                    hasError = true;
                }

            }
        }
        return hasError;
    }

    /*每层返回一个元祖(String start,String end,ArrayList path, Double distance)*/
    public static ArrayList<Quartet<String, String, ArrayList<Object>,Double>> getResultWithinFloor(Floor floor){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> result = new ArrayList<>();
        WeightedGraph graph = floor.getGraph();
        HashMap<String, Integer> point_id_map = floor.getPoint_id_map();
        for (String common_point:floor.getCommon_points()){
            Integer start_point_id = point_id_map.get(common_point);
            final int[] pred = Dijkstra.dijkstra(graph, start_point_id);
            //stairs
            for (String stair:floor.getStairs()){
                Quartet Q = Dijkstra.getResultTuple(graph, point_id_map, pred, common_point, stair);
                result.add(Q);
            }
            //lifts
            for (String lift:floor.getLifts()){
                Quartet Q = Dijkstra.getResultTuple(graph, point_id_map, pred, common_point, lift);
                result.add(Q);
            }
            //escalator
            for (String escalator:floor.getEscalators()){
                Quartet Q = Dijkstra.getResultTuple(graph, point_id_map,pred, common_point, escalator);
                result.add(Q);
            }
        }
        return result;
    }

    public static ArrayList<Quartet<String, String, ArrayList<Object>,Double>> getResultWithinFloor(Floor floor, String start_point_name){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> result = new ArrayList<>();
        WeightedGraph graph = floor.getGraph();
        HashMap<String, Integer> point_id_map = floor.getPoint_id_map();
        Integer start_point_id = point_id_map.get(start_point_name);
        System.out.println(start_point_id);
        final int[] pred = Dijkstra.dijkstra(graph, start_point_id);
        //stairs
        for (String stair:floor.getStairs()){
            Quartet Q = Dijkstra.getResultTuple(graph, point_id_map, pred, start_point_name, stair);
            result.add(Q);
        }
        //lifts
        for (String lift:floor.getLifts()){
            Quartet Q = Dijkstra.getResultTuple(graph, point_id_map, pred, start_point_name, lift);
            result.add(Q);
        }
        //escalator
        for (String escalator:floor.getEscalators()){
            Quartet Q = Dijkstra.getResultTuple(graph, point_id_map,pred, start_point_name, escalator);
            result.add(Q);
        }
        return result;
    }

    public static ArrayList<Quartet<String, String, ArrayList<Object>,Double>> getResultWithinFloor(Floor floor, String start_point, String end_point){
        ArrayList<Quartet<String, String, ArrayList<Object>,Double>> result = new ArrayList<>();
        WeightedGraph graph = floor.getGraph();
        HashMap<String, Integer> point_id_map = floor.getPoint_id_map();
        Integer start_point_id = point_id_map.get(start_point);
        final int[] pred = Dijkstra.dijkstra(graph, start_point_id);
        Quartet Q = Dijkstra.getResultTuple(graph, point_id_map, pred, start_point, end_point);
        result.add(Q);
        return result;
    }




    public static void main(String[] args) throws Exception {
        String filename = "src/data/test_points1";
        String filename2 = "C:\\Users\\student\\Desktop\\xiaoyu\\数据\\12.txt";
//        Map<String, Integer> point_id_map = readNodesFromFile(filename);

        WeightedGraph t = buildFromFile(filename);
//        t.print();
        if (t.isConnected()){
            final int[] pred = Dijkstra.dijkstra(t, 0);
            System.out.println(Arrays.toString(pred));
            for (int n = 0; n < t.vexs; n++) {
                Dijkstra.printPath(t, pred, 0, n);
            }
        }else {
            System.out.println(" The graph is not connected!");
        }

    }


}
