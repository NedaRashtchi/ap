package ap;

import java.util.Scanner;

public class E6_18 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("enter the size : ");
        int n = input.nextInt();

        printDimond(n);

    }
    static void printDimond(int row){
        int space=row-1 ;
        int star=row-space ;
        for(int i = 1 ; i <= row ; i++){
            for(int j=1 ; j <= space ;j++){
                System.out.print(" ");
            }
            for(int j=1 ; j <= star ; j++){
                System.out.print("*");
            }
            System.out.println();
            space--;
            star+=2;
        }
            space+=2;
            star-=4;
        for(int i = row-1 ; i >=1 ; i--){

            for(int j=space ; j >= 1 ;j--){
                System.out.print(" ");
            }
            for(int j=star ; j >= 1 ; j--){
                System.out.print("*");
            }

            System.out.println();
            space++;
            star-=2;
        }
    }
}
