package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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
        for(char []b : board){
            System.out.print(b);
            System.out.println();
        }
    }
}
