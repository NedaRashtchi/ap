package ap.exercises.ex2;

import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_2_2 {
    private int xX=1 , yX=1 , score=0;
    public static void main(String[] args) {
        Main_EX2_PM_2_2 mygame = new Main_EX2_PM_2_2();
        System.out.print("Enter k : ");
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        char [][]board = new char[k+2][k+2];
       int winScore = mygame.board(board , k);
        mygame.printBoard(board);
        mygame.play(board,winScore);
    }
    private int board(char[][] board , int k) {
        for(int i = 0; i < k+2; i++){
            for(int j = 0; j < k+2; j++){
                if(i==0 || j==0 || j==k+1 || i==k+1){
                    board[i][j] = '*';
                }
                else board[i][j] = ' ';
            }
        }
        board[1][1] = 'X';
        Scanner in = new Scanner(System.in);
        int c , winScore;
        System.out.print("Enter c : ");
        do{
            c = in.nextInt();
            winScore = c;
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
        return winScore;
    }
    private void printBoard(char[][] board){
        for(char []b : board){
            System.out.print(b);
            System.out.println();
        }
        System.out.println("score : "+score);
    }
    private void play(char[][] board , int winScore){
        long start = System.currentTimeMillis();
        long finish , timeElapsed;
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
                    System.out.println("Exiting...");
                    System.out.println("score : "+score);
                    finish = System.currentTimeMillis();
                    timeElapsed = finish - start;
                    System.out.println("time elapsed : "+timeElapsed);
                    break loop;
                default:
                    System.out.println("Invalid move");
                    break;
            }
            if(score == winScore){
                System.out.println("Final score : "+score);
                finish = System.currentTimeMillis();
                timeElapsed = finish - start;
                System.out.println("Time elapsed : "+timeElapsed);
                break loop;
            }
        }
    }
    private void moves(char[][] board , int x , int y){
        if(board[yX+y][xX+x] != '*'){
            if(board[yX+y][xX+x] == '.'){
                score++;
            }
            board[yX+y][xX+x] = 'X';
            board[yX][xX] = ' ';
            xX+=x; yX+=y;
            printBoard(board);

        }else System.out.println("hitting the game wall");
    }
}
