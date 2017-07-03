import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        String str;
        try{
            inputStream = new FileInputStream("C:\\Users\\student\\Desktop\\xiaoyu\\数据\\B1.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((str = bufferedReader.readLine()) != null)
            {
                System.out.println(str);
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
