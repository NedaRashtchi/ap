package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_2_1 {
    public static int xX=1 , yX=1;
    public static void main(String[] args) {


        System.out.print("Enter k : ");
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        char [][]board = new char[k+2][k+2];
        board(board , k);
        printBoard(board);
        play(board);
    }
    public static void board(char[][] board , int k){
        for(int i = 0; i < k+2; i++){
            for(int j = 0; j < k+2; j++){
                if(i==0 || j==0 || j==k+1 || i==k+1){
                    board[i][j] = '*';
                }
                else board[i][j] = ' ';
            }
        }
        board[1][1] = 'X';
    }
    static void printBoard(char[][] board){
        for(char []b : board){
            System.out.print(b);
            System.out.println();
        }
    }
    public static void play(char[][] board){
        Scanner in = new Scanner(System.in);
        char move ;
        loop:
        while(true){
            move = in.next().charAt(0);
            switch(move){
                case 'w' , 'W':
                    System.out.println("up");
                    moves(board,0,-1);
                    break;
                case 'd' , 'D':
                    System.out.println("right");
                    moves(board,1,0);
                    break;
                case 's', 'S':
                    System.out.println("down");
                    moves(board,0,1);
                    break;
                case 'a' , 'A':
                    System.out.println("left");
                    moves(board,-1,0);
                    break;
                case 'q' , 'Q' :
                    System.out.println("Exit");
                    break loop;
                default:
                    System.out.println("Invalid move");
                    break;
            }
        }
    }
    public static void moves(char[][] board , int x , int y){
        if(board[yX+y][xX+x] != '*'){
            board[yX+y][xX+x] = 'X';
            board[yX][xX] = ' ';
            xX+=x; yX+=y;
            printBoard(board);

        }else System.out.println("hitting the game wall");
    }
}
