package ap.exercises.ex1;

import java.util.Scanner;

public class E6_13 {
    public static void main(String[] args) {
        System.out.print("Enter a number : ");
        Scanner number = new Scanner(System.in);
        int num = number.nextInt();
        printBinary(num);
    }
    static void printBinary(int num){
        while(num>0){
            System.out.println(num%2);
            num/=2;
        }
    }
}
