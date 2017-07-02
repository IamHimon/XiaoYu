package test;
import java.lang.*;
import java.util.Arrays;

/**
 * Created by himon on 17-7-2.
 * 动态规划算法
 */
public class demo {

    /*
    * 最大连续乘积子数组
    * */
    public static double MaxMinProdcctSubstring(double a[]){
        double maxEnd = a[0];
        double minEnd = a[0];

        double maxResult = a[0];
        double minResult = a[0];
        for (int i = 1; i < a.length; i++) {
            double end1 = maxEnd * a[i], end2 = minEnd * a[i];
            maxEnd = Math.max(Math.max(end1, end2), a[i]);
            minEnd = Math.min(Math.min(end1, end2), a[i]);
            maxResult = Math.max(maxResult, maxEnd);
            minResult = Math.min(minResult, minEnd);
        }
        return maxResult;
    }

    public static double MaxProdcctSubstring(double a[]){
        double maxEnd = a[0];
        double maxResult = a[0];
        for (int i = 1; i < a.length; i++){
            double end = maxEnd * a[i];
            maxEnd = Math.max(end, a[i]);
            maxResult = Math.max(maxResult, maxEnd);  // 状态方程
        }
        return maxResult;
    }

    /*
    * 字符串编辑距离
    * 动态规划
    *
    * 字符＝>字符：
    *
    *
    * */
    public static int EditDistance(String S, String T){
        int Slength = S.length();
        int Tlengh = T.length();
        int dp[][] = new int[Slength][Tlengh];
        // 初始化
        for(int i = 0; i < Slength;i ++ )
            dp[i][0] = Slength;

        for (int j = 0; j < Tlengh; j++)
            dp[0][j] = Tlengh;

        for (int i=1;i<Slength;i++){
            for (int j =1;j<Tlengh-1;j++){
                int temp = dp[i-1][j-1] + (S.charAt(i) == T.charAt(j) ? 0:1);
                dp[i][j] = Math.min(Math.min(dp[i-1][j]+1,dp[i][j-1] +1), temp);
            }
        }

        System.out.println(Arrays.deepToString(dp));

        return dp[Slength-1][Tlengh-1];
    }


    /*
    * 交替字符串
    *问题：三个字符串s1,s2,s3, 判断ｓ3是不是由s1和s2交错却不改变原有字符相对顺序组成．
    *分析：
    * */

    public static void main(String[] args) {
        System.out.println("hello world!");
//        double[] a = {-2.5, 4, 0, 3, 0.5, 8, -1};
//        double maxResult = MaxProdcctSubstring(a);
//        System.out.println(maxResult);
        EditDistance("asidfhal", "asdfa");

    }
}
