package ap.exercises.ex2;

import java.util.Scanner;

public class EX2_PM_1_1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        input.close();
        for(int i = 0; i < k+2; i++){
            for(int j = 0; j < k+2; j++){
                if(i==0 || j==0 || j==k+1 || i==k+1){
                    System.out.print("*");
                }else System.out.print(" ");
            }
            System.out.println();
        }
    }
}
