import java.io.*;
import java.lang.reflect.Array;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String filename = "src/data/test_points";

        String str;
        try{
            inputStream = new FileInputStream(filename);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((str = bufferedReader.readLine()) != null)
            {
                String[] line = str.split("*");
                System.out.println(line[0]);
                System.out.println(line[1]);
            }
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
