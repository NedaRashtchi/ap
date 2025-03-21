package ap.pacman;

import static ap.pacman.Board.*;

public class Play {
    public static int countscore = 0;
    static int move(int moveX, int moveY, Game.Type [][] board , int score){
        countscore += score;
        int x = pacman_x + moveX;
        int y = pacman_y + moveY;

        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && board[y][x].type != WALL) {
            if (board[y][x].type == FOOD) {

                //score += board[y][x].value;
                countscore++;

                if ( score >= /*WIN_SCORE*/ 10 ) {
                    return 2 ;
                }
            }
            else if ( board[y][x].type == GOAST) {
                return 1 ;
            }
//            else if(board[y][x].type == BONUS){
//                powermove += 10;
//                score += board[y][x].value;
//            }
            else if(board[y][x].type == TRAP){
                countscore += board[y][x].value ;

            }

            board[pacman_y][pacman_x].type = EMPTY;
            board[pacman_y][pacman_x].value = 0;
            pacman_x = x;
            pacman_y = y;
            board[pacman_y][pacman_x].type = PACMAN;
            board[pacman_y][pacman_x].value = 0;


        }return 0;
    }
    public static int returnScore(){
        return countscore;
    }
}
