import com.sun.org.apache.bcel.internal.generic.FALOAD;
import test.Point;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.*;

public class Main {

    private static Point buildPoint(String line)throws Exception{
        Point p = new Point();
        try{
            String[] split_lines = line.substring(line.indexOf("[")+1, line.indexOf("]")).split("#");
            p.setLabel(split_lines[0]);
            Double[] point = new Double[2];
            int i = 0;
            for (String s:split_lines[1].split(",")){
                point[i++] = Double.valueOf(s);
            }
            p.setPoint(point);
        }catch (Exception e){
            System.out.println("Please check data format!(Lable and Point are separated by '#')");
            throw e;
        }

        return p;
    }

    public static boolean isEqual(Point p1, Point p2){
        return (p1.getLabel().equals(p2.getLabel())) && (p1.getPoint()[0].equals(p2.getPoint()[0]))
                && (p1.getPoint()[1].equals(p2.getPoint()[1]));
    }



    public static void main(String[] args) {
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String filename = "src/data/test_points";

        String str;
        ArrayList<Point[]> allPointsPair = new ArrayList<>();
        List<Point> allPoints = new ArrayList<>();
        try{
            inputStream = new FileInputStream(filename);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((str = bufferedReader.readLine()) != null)
            {
                try{
                    String[] line = str.split("&");
                    Point p1 = buildPoint(line[0]);
                    p1.printPoint();
                    allPoints.add(p1);

                    Point p2 = buildPoint(line[1]);
                    p2.printPoint();
                    allPoints.add(p2);
                    allPointsPair.add(new Point[]{p1, p2});

                }catch (Exception e){
                    System.out.println("Please check data format!");
                    e.printStackTrace();
                }
            }
            System.out.println(allPointsPair.size());
            System.out.println(allPoints.size());
            // 去掉重复点
            List<Point> setPoints = new ArrayList<>();
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
            System.out.println(setPoints.size());
            Map<String, Integer> point_id = new HashMap<>();
            for (int i=0;i<setPoints.size();i++){
                point_id.put(setPoints.get(i).getLabel(), i);
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

    }
}
