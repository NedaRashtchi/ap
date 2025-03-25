package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_1_3 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter k: ");
        int k = in.nextInt();
        char [][]board = new char[k+2][k+2];
        for(int i = 0; i < k+2; i++){
            for(int j = 0; j < k+2; j++){
                if(i==0 || j==0 || j==k+1 || i==k+1){
                    board[i][j] = '*';
                }
                else board[i][j] = ' ';
            }
        }
        int c;
        System.out.println("Enter c : ");
        do{
            c = in.nextInt();
            if(c > Math.pow(k,2)){
                System.out.println("Error");
            }
        }while(c > Math.pow(k , 2));
        Random rand = new Random();
        int x,y;
        while(c!=0){
           x=rand.nextInt(k+2);
           y=rand.nextInt(k+2);
           if(board[y][x] == ' '){
               board[y][x] = '.';
               c--;
           }
        }
        for(char[] b : board){
            System.out.print(b);
            System.out.println();
        }

    }

}
