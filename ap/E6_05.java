package ap;

import java.util.Scanner;

public class E6_05 {
    static int count = 0;
    public static void main(String[] args) {
        float [] num = new float[50];
        //int count=0;
        add(num);
        System.out.println("The average value = "+getAverage(num));
        System.out.println("The smallest value = "+getSmallest(num));
        System.out.println("The largest value = "+getLargest(num));
        System.out.println("The range of values = "+getRange(num));
    }
    public static void add(float[]num){
        System.out.println("enter your numbers (enter -1 when finished) :");
        Scanner in = new Scanner(System.in);
        while(true){
            num[count] = in.nextFloat();
            if(num[count] == -1) break;
            count++;
        }
    }
    static float getAverage(float[]num){
        float sum=0;
        float ave=0;
        for(int i=0;i<count;i++){
            sum+=num[i];
        }
        ave=sum/count;
        return ave;
    }
    static float getSmallest(float[]num){
        float small = num[0];
        for(int i=1;i<count;i++){
            if(num[i]<small) small = num[i];
        }
        return small;
    }
    static float getLargest(float[]num){
        float large = num[0];
        for(int i=1;i<count;i++){
            if(num[i]> large) large = num[i];
        }
        return large;
    }
    static float getRange(float[]num){
        float up = getLargest(num);
        float down = getSmallest(num);
        return up - down;
    }
}
