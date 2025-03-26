package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_4 {
    public static void main(String[] args) {
        moves();
    }
    static void moves(){
        System.out.println("Enter w , a, s, d to move and q to exit.");
        Scanner sc = new Scanner(System.in);
        char move;
        loop:
        while(true){
            move = sc.next().charAt(0);
            switch(move){
                case 'w':
                    System.out.println("UP");
                    break;
                case 'a':
                    System.out.println("LEFT");
                    break;
                case 's':
                    System.out.println("DOWN");
                    break;
                case 'd':
                    System.out.println("RIGHT");
                    break;
                case 'q':
                    System.out.println("EXIT");
                    break loop;
                default:
                    System.out.println("WARNING");
                    break;
            }
        }
    }
}
