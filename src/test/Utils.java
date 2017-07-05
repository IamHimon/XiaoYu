package test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by student on 2017/7/5.
 * 工具类
 */
public class Utils {

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
                    p1.printPoint();
                    allPoints.add(p1);

                    Point p2 = buildPoint(line[1]);
                    p2.printPoint();
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


    public static void main(String[] args) throws Exception {
        String filename = "src/data/test_points";
        WeightedGraph t = buildFromFile(filename);
        t.print();
    }
}
