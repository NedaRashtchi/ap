package ap.exercises.ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main_EX2_PM_2_3 {
    private int xX=1 , yX=1 , score=0;
    public static void main(String[] args) throws FileNotFoundException {
        Main_EX2_PM_2_3 mygame = new Main_EX2_PM_2_3();

        Scanner in = new Scanner(System.in);

        System.out.println("1. Start a new game");
        System.out.println("2. Continue previous game");
        int choice = in.nextInt();

        char[][] board;

        if (choice == 1) {
            System.out.print("Enter k : ");
            int k = in.nextInt();
            board = new char[k+2][k+2];
            mygame.board(board , k);
            mygame.printBoard(board);
            mygame.play(board);
        }else if (choice == 2) {
            File file = new File("mygame.txt");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            Scanner reader = new Scanner(file);
            int k = Integer.parseInt(reader.nextLine());
            board = new char[k+2][k+2];
            reader.close();
            try {
                mygame.readFromFile(board , k);
                mygame.getWinScore(board);
                mygame.printBoard(board);
                mygame.play(board);
            } catch (FileNotFoundException e) {
                System.out.println("No saved game found.\nStarting a new game...");
                System.out.print("Enter k: ");
                k = in.nextInt();
                board = new char[k + 2][k + 2];
                mygame.board(board, k);
                mygame.printBoard(board);
                mygame.play(board);
            }
        } else {
            System.out.println("Invalid choice.Exiting...");
        }
    }
    private void saveToFile(char[][] board) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("mygame.txt")) {
            int k = board.length - 2;
            writer.println(k);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    writer.print(board[i][j]);
                }
                writer.println();
            }
            writer.println(score);
            System.out.println("Game saved!");
        }
    }
    private void readFromFile(char[][] board , int k) throws FileNotFoundException {
        File file = new File("mygame.txt");
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        Scanner reader = new Scanner(file);
        reader.nextLine();
        for (int i = 0; i < k + 2; i++) {
            String line = reader.nextLine();
            for (int j = 0; j < k + 2; j++) {
                board[i][j] = line.charAt(j);
                if (board[i][j] == 'X') {
                    xX = j;
                    yX = i;
                }
            }
        }
        score = Integer.parseInt(reader.nextLine());
        reader.close();
        System.out.println("Game loaded successfully!");
    }
    private int getWinScore(char[][] board) {
        int count = 0;
        for (char[] row : board) {
            for (char c : row) {
                if (c == '.') {
                    count++;
                }
            }
        }
        return count;
    }
    private void board(char[][] board , int k) {
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
    }
    private void printBoard(char[][] board){
        for(char []b : board){
            System.out.print(b);
            System.out.println();
        }
        System.out.println("score : "+score);
    }
    private void play(char[][] board) throws FileNotFoundException {
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
                    saveToFile(board);
                    break loop;
                default:
                    System.out.println("Invalid move");
                    break;
            }
            if(getWinScore(board) == 0){
                System.out.println("You Won!\nFinal score : "+score);
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
