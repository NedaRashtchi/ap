package ap;

import java.util.Scanner;

public class E6_17 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("enter the size : ");
        int n = input.nextInt();

        printSquare(n);
    }
    static void printSquare(int n){
        for(int i=0;i<n;i++){
            for(int j=0;j<2*n;j++){

                if(i==0 || i==n-1){

                    System.out.print("*");
                    if(j==n-1) System.out.print("  ");
                }
                if(i>0 && i<n-1){
                    if(j<n || j==2*n-1){

                        System.out.print("*");
                        if(j==n-1) System.out.print("  *");
                    }
                    if(j>=n && j<2*n-2){

                        System.out.print(" ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
}
