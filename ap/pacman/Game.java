package ap.pacman;

import java.util.Scanner;

import static ap.pacman.Board.HEIGHT;
import static ap.pacman.Board.WIDTH;

public class Game {
    static class Type{
        int value;
        char type;
        public Type(int value, char type) {
            this.value = value;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        int score = 0;
        //Board board1 = new Board();
        Type[][] board = new Type[HEIGHT][WIDTH];
        Board.initialize(board);
        Board.printBoard(board);

        Scanner input = new Scanner(System.in);
        char moves;
        int result=0;
        loop:
        while(true){
            if(result == 1) {
                System.out.println("You lose!");
                break;
            }else if(result == 2) {
                System.out.println("You win!");
                break;
            }
            moves = input.next().charAt(0);
            switch(moves){

                case 'w' , 'W' : result = Play.move(0,-1 , board ,score); break;
                case 's' , 'S' : result = Play.move(0,1 , board ,score); break;
                case 'a' , 'A' : result = Play.move(-1,0 , board ,score); break;
                case 'd' , 'D' : result = Play.move(1,0 , board ,score); break;
                case 'q' : break loop;
                default : break;
            }
            Board.printBoard(board);
        }
    }
}
