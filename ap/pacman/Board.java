package ap.pacman;

import java.util.Random;
import static ap.pacman.Game.Type;

public class Board {
    // controls the game loop and updates the game state
    //initialize the game board and characters
    //it has a move method
    static char PACMAN = 'C';
    static char FOOD = '.';
    static char WALL = '#';
    static char EMPTY = ' ';
    static char GOAST = 'G';
    static char TRAP = 'o';
    static int WIDTH = 20; //ofoghi
    static int HEIGHT = 10; //amoodi
    static int pacman_x = WIDTH / 2;
    static int pacman_y = HEIGHT / 2;
   // static char [][] board = new char[HEIGHT][WIDTH];

    static void initialize(Game.Type[][] board) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if(i==0 || i==HEIGHT-1 || j==0 || j==WIDTH-1){
                    board[i][j] = new Type(0 , WALL);
                }else board[i][j] = new Type(0, EMPTY);
            }
        }


        board[pacman_y][pacman_x].type = PACMAN;

        Random rand = new Random();
        int x , y ;
        int count = 30;
        while(count!=0){
            x = rand.nextInt(WIDTH) ;
            y = rand.nextInt(HEIGHT) ;
            if(board[y][x].type != WALL && board[y][x].type != PACMAN){
                board[y][x].type = WALL;
                count--;
            }
        }

        count = 10;
        while (count != 0) {
             y = rand.nextInt(HEIGHT);
             x = rand.nextInt(WIDTH);

            if (board[y][x].type != WALL && board[y][x].type != PACMAN) {

                board[y][x].type = GOAST;
                count--;
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i % 2 == 1 && j % 2 == 1 //food khone haye fard
                        && board[i][j].type != WALL
                        && board[i][j].type != GOAST
                        && board[i][j].type != PACMAN) {

                    board[i][j].type = FOOD;
                    board[i][j].value = 1;
                }
            }
        }
        count = 5;
        while (count != 0) {
            y = rand.nextInt(HEIGHT);
            x = rand.nextInt(WIDTH);

            if (board[y][x].type != WALL && board[y][x].type != PACMAN && board[y][x].type != GOAST) {

                board[y][x].type = TRAP;
                board[y][x].value = -1;
                count--;
            }
        }

    }
    static void printBoard(Game.Type[][] board) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j].type);
            }
            System.out.println();
        }

        System.out.println("Score: " + Play.returnScore());
    }

}
