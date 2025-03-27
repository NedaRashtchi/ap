package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_1_5 {
    public static int xX=1 , yX=1;
    public static void main(String[] args) {


        System.out.print("Enter k : ");
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        char [][]board = new char[k+2][k+2];
        board(board , k);
        printBoard(board);
        randoms(board);
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
    public static void randoms(char[][] board){
        Random rand = new Random();
        int move ;
        while(true){
          move = rand.nextInt(4);
          switch(move){
              case 0:
                  System.out.println("up");
                  moves(board,0,-1);
                  break;
              case 1:
                  System.out.println("right");
                  moves(board,1,0);
                  break;
              case 2:
                  System.out.println("down");
                  moves(board,0,1);
                  break;
              case 3:
                  System.out.println("left");
                  moves(board,-1,0);
                  break;
              default:
                  System.out.println("Invalid move");
                  break;
          }
          try{
              Thread.sleep(1000);
          }catch (Exception e){}
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
