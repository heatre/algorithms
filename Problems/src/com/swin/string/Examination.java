package com.swin.string;


import java.util.Arrays;

public class Examination {

    public Examination() {
    }

    public int getMaxSnest(int[][] envelopes) {

        //列排序
        for(int i=0;i<envelopes.length;++i)
            Arrays.sort(envelopes[i]);

        //行排序
        int temp1 = 0;
        int temp2 = 0;
        for(int i = envelopes.length-1;i >0;i--)
        {
            for(int j = 0;j<i;j++)
            {
                if(envelopes[j][0] > envelopes[j+1][0])  //交换两数位置
                {
                    temp1 = envelopes[j][0];
                    temp2 = envelopes[j][1];
                    envelopes[j][0] = envelopes[j+1][0];
                    envelopes[j][1] = envelopes[j+1][1];
                    envelopes[j+1][0] = temp1;
                    envelopes[j+1][1] =temp2;
                }
            }
        }


        int max = envelopes.length-1;
        int num = 0;
        for(int i = envelopes.length-1; i >0; --i) {
            if(envelopes[i][0]>envelopes[i-1][0] && envelopes[i][1]>envelopes[i-1][1]){
                num++;
                max = i-1;
            }
        }
        return num;
    }

    public static void main(String[] args) {
        Examination e = new Examination();
        int[][] testData1 = new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}, {3, 4}, {4, 5}, {5, 6}};
        System.out.println(e.getMaxSnest(testData1));
        int[][] testData2 = {{5, 4}, {6, 4}, {6, 7}, {2, 3}, {1, 5}};
        System.out.println(e.getMaxSnest(testData2));
        int[][] testData3 = new int[][]{{1, 3}, {2, 2}, {10, 30}, {20, 20}, {21, 21}, {22, 22}};
        System.out.println(e.getMaxSnest(testData3));
    }
}
